package school.sptech;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.exception.SdkException;

import java.nio.file.Path;
import java.util.List;

public class Main {
    // Logs próprios da Main
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        // TIPOS DE LOG
        // INFO: Resumo de cada CLP
        // WARN: Leitura acima do limite
        // DEBUG: Valor ausente (dado incompleto)
        // TRACE: Cada comparação que deu certo
        // ERROR: Falhas (banco ou bucket), na Main

        // Defina o nome do bucket e o prefixo da busca
        String nomeBucket = dotenv.get("NOME_BUCKET");
        String prefixo = dotenv.get("PREFIXO");
        String nomePasta = "silver";

        try {
            System.out.println("Buscando e instalando objetos na S3");
            S3Objects obj = new S3Objects();
            obj.getS3Objects(nomeBucket, prefixo);

            System.out.println("Lendo CSVs");
            List<Clp> clps = new Arquivo().lerCsvs(nomePasta);

            Clp clp = new Arquivo().buscarCpl(clps, 20, 4, "1");
            if (clp == null) {
                System.out.println("\nCLP não encontrado");
            } else {
                System.out.println("\n" + clp + " | leituras: " + clp.getLeituras().size());

                System.out.println("\n\n\n\n");

                for (Leitura leitura : clp.getLeituras()) {
                    System.out.println(leitura);
                }

                System.out.println("Deletando CSVs");
            }

            obj.deleteCsvs(prefixo);


            // ====================================================================
            // CONEXÃO COM O BANCO DE DADOS E BUSCA DOS LIMITES

            ConexaoBd conexao = new ConexaoBd();
            JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

            // ABERTURA DE CHAMADO NO JIRA
            LimiteDao limiteDao = new LimiteDao(template);
            VerificadorLimites verificador = new VerificadorLimites();
//            ConexaoJira jira = new ConexaoJira();
//
            for (Clp clpAtual : clps) {
                try {
                    List<LimiteMedicao> limites = limiteDao.buscarLimites(clpAtual.getId_clp());
                    List<Violacao> violacoes = verificador.verificar(clpAtual, limites);

//                    for (Violacao violacao : violacoes) {
//                        jira.abrirChamado(violacao);
//                    }
                } catch (Exception e) {
                    log.error("Falha ao processar o CLP {}", clpAtual.getId_clp(), e);
                }
            }
        } catch (SdkException e) {
            log.error("Erro ao acessar a AWS (S3)", e);
        } catch (DataAccessException e) {
            log.error("Erro ao acessar o banco de dados", e);
        } catch (Exception e) {
            log.error("Falha inesperada durante o processamento", e);
        }


        new S3Objects().deleteCsvs(prefixo); // garante que não sobram CSVs velhos na pasta

        // Envio dos logs ao bucket
        try {
            String caminho = LogArquivo.getCaminho();
            if (caminho != null) {
                Path arquivo = Path.of(caminho);
                new S3Objects().enviarLog(nomeBucket, "bronze/logs/" + arquivo.getFileName(), arquivo);
            }
        } catch (Exception e) {
            System.out.println("Não foi possível enviar o log ao bucket: " + e);
        }
    }
}

