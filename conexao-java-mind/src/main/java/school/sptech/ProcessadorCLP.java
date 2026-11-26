package school.sptech;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.SdkException;

import java.nio.file.Path;
import java.util.List;

public class ProcessadorCLP {

    private static final Logger log = LoggerFactory.getLogger(ProcessadorCLP.class);

    private final Dotenv dotenv;

    public ProcessadorCLP(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public void executar() {

        log.info("Iniciando processamento dos CLPs");

        String nomeBucket = dotenv.get("NOME_BUCKET");
        String prefixo = dotenv.get("PREFIXO");
        String nomePasta = "silver";

        try {
            System.out.println("Buscando e instalando objetos na S3");

            S3Objects obj = new S3Objects();
            obj.getS3Objects(nomeBucket, prefixo);

            System.out.println("Lendo CSVs");

            List<Clp> clps = new Arquivo().lerCsvs(nomePasta);

            System.out.println("Deletando CSVs");

            obj.deleteCsvs(prefixo);

            // Conexão com o banco de dados

            ConexaoBd conexao = new ConexaoBd();
            JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

            // Busca dos limites

            LimiteDao limiteDao = new LimiteDao(template);

            VerificadorLimites verificador = new VerificadorLimites();

            // Conexão com o Jira

            CreateIssue createIssue = new CreateIssue();

            for (Clp clpAtual : clps) {

                try {

                    // Insere o endereço MAC do CLP
                    clpAtual.setMac_adress(
                            limiteDao.buscarMacAdress(clpAtual.getId_clp())
                    );

                    System.out.println("CLP atual: " + clpAtual);

                    // Insere o nome do modelo
                    clpAtual.setNome_modelo(
                            limiteDao.buscarNomeModelo(clpAtual.getId_clp())
                    );

                    // Insere o nome do CLP (apelido)
                    clpAtual.setApelido(
                            limiteDao.buscarNomeClp(clpAtual.getId_clp())
                    );

                    // Busca os limites no banco
                    List<LimiteMedicao> limites =
                            limiteDao.buscarLimites(clpAtual.getId_clp());

                    // Compara os limites com as leituras
                    List<Violacao> violacoes =
                            verificador.verificar(clpAtual, limites);

                    // Cria o chamado no Jira
                    if (!violacoes.isEmpty()) {
                        createIssue.criarChamado(clpAtual, violacoes);
                    }

                } catch (Exception e) {
                    log.error(
                            "Falha ao processar o CLP {}",
                            clpAtual.getId_clp(),
                            e
                    );
                }
            }

        } catch (SdkException e) {
            log.error("Erro ao acessar a AWS (S3)", e);

        } catch (DataAccessException e) {
            log.error("Erro ao acessar o banco de dados", e);

        } catch (Exception e) {
            log.error("Falha inesperada durante o processamento", e);
        }

        // Garante que não sobrem CSVs antigos na pasta
        new S3Objects().deleteCsvs(prefixo);

        // Envio dos logs ao bucket
        try {

            String caminho = LogArquivo.getCaminho();

            if (caminho != null) {

                Path arquivo = Path.of(caminho);

                new S3Objects().enviarLog(
                        nomeBucket,
                        "bronze/logs/" + arquivo.getFileName(),
                        arquivo
                );
            }

        } catch (Exception e) {
            log.error("Não foi possível enviar o log ao bucket", e);
        }
        log.info("Processamento dos CLPs finalizado");
    }
}
