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

        if(nomeBucket != null && prefixo != null && nomePasta != null){
            System.out.println("Definindo nome do bucket e prefixo da busca: \n");
            System.out.println("Nome Bucket: " + nomeBucket);
            System.out.println("Prefixo: " + prefixo);
            System.out.println("Pasta: " + nomePasta + "\n");
        }

        try {
            System.out.println("Buscando e instalando objetos na S3");
            S3Objects obj = new S3Objects();
            obj.getS3Objects(nomeBucket, prefixo);

            System.out.println("Lendo CSVs");
            List<Clp> clps = new Arquivo().lerCsvs(nomePasta);

            System.out.println("Deletando CSVs");
            obj.deleteCsvs(prefixo);


            // ====================================================================
            // CONEXÃO COM O BANCO DE DADOS E BUSCA DOS LIMITES

            ConexaoBd conexao = new ConexaoBd();
            JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

            // ABERTURA DE CHAMADO NO JIRA

            // Busca os limites no banco
            LimiteDao limiteDao = new LimiteDao(template);
            System.out.println("\n Chamando objeto de consulta do banco..");

            // Compara os limites do banco com as leituras do bucket
            VerificadorLimites verificador = new VerificadorLimites();
            System.out.println("Chamando VerificarLimites...");

            // Conexão com o Jira
            CreateIssue createIssue = new CreateIssue();
            System.out.println("Conexão com o Jira criada! \n");
//
            for (Clp clpAtual : clps) {
                try {
                    // Insere o mac_adress em clp
                    clpAtual.setMac_adress(limiteDao.buscarMacAdress(clpAtual.getId_clp()));
                    System.out.println("CLP atual: "+clpAtual+ "\n\n");
                    // Insere o nome do modelo em clp
                    clpAtual.setNome_modelo(limiteDao.buscarNomeModelo(clpAtual.getId_clp()));

                    // Insere o nome do CLP (apelido)
                    clpAtual.setApelido(limiteDao.buscarNomeClp(clpAtual.getId_clp()));

                    // Busca os limites no banco
                    List<LimiteMedicao> limites = limiteDao.buscarLimites(clpAtual.getId_clp());;

                    // Compara os limites do banco com as leituras do bucket
                    List<Violacao> violacoes = verificador.verificar(clpAtual, limites);

                    // Cria o chamado no Jira
                    if(!violacoes.isEmpty()){
                        createIssue.criarChamado(clpAtual, violacoes);
                    }

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
                System.out.println("Logs enviados ao Bucket " + nomeBucket + " em bronze/logs/");
            }
        } catch (Exception e) {
            System.out.println("Não foi possível enviar o log ao bucket: " + e);
        }
    }
}

