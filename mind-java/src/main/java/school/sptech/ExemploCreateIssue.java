package school.sptech;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.github.cdimascio.dotenv.Dotenv;
import school.sptech.config.Jira;

public class ExemploCreateIssue {

    public static void main(String[] args) {
// Caminho da pasta onde estão os arquivos (pode ser relativo ou absoluto)
        File pasta = new File("./csv");
        String divisor = ",";

        // Filtra para pegar apenas arquivos que terminam com .csv
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".csv"));

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                System.out.println("--- Processando: " + arquivo.getName() + " ---");

                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    br.readLine();

                    while ((linha = br.readLine()) != null) {
                        String[] campos = lineSplit(linha, divisor);
                        // Processa os dados aqui

                        LocalDateTime dataHora = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        String dataHoraFormatada = dataHora.format(formatter);

                        System.out.println(dataHoraFormatada + " CLP: " + campos[0] + " | Uso da CPU (%): " + campos[2] + " | Uso de RAM (%): " + campos[3] + " | ScanTime: " + campos[4]);

                        if (Double.parseDouble(campos[2]) > 90.0) {
                            criarChamado("Alto nível da CPU");
                            System.out.println(dataHoraFormatada + " [WARNING] " + campos[0] + " Apresentou alto uso de CPU - " + campos[2] + "%");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Abrindo chamado no Jira + Slack - " + campos[0] + " Alto uso de CPU. Verificação do CLP necessária!");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Chamado aberto com sucesso!");
                            Thread.sleep(1000);
                            System.out.println(dataHoraFormatada + " [INFO] Continuando leitura dos demais aparelhos...");
                            break;
                        }

                        if (Double.parseDouble(campos[3]) > 95.0) {
                            criarChamado("Alto nível da RAM");
                            System.out.println(dataHoraFormatada + " [WARNING] " + campos[0] + " Apresentou alto uso de RAM - " + campos[3] + "%");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Abrindo chamado no Jira + Slack - " + campos[0] + " Alto uso de RAM. Verificação do CLP necessária!");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Chamado aberto com sucesso!");
                            Thread.sleep(1000);
                            System.out.println(dataHoraFormatada + " [INFO] Continuando leitura dos demais aparelhos...");
                            break;
                        }

                        if (Double.parseDouble(campos[4]) > 5.0) {
                            criarChamado("Alto nível de SCAN TIME");
                            System.out.println(dataHoraFormatada + " [WARNING] " + campos[0] + " Apresentou ScanTime elevado - " + campos[4] + "ms");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Abrindo chamado no Jira + Slack - " + campos[0] + " ScanTime acima dos padrões definidos. Verificação do CLP necessária!");
                            Thread.sleep(1000);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.print(".");
                            Thread.sleep(500);
                            System.out.println(".");
                            Thread.sleep(500);
                            System.out.println(dataHoraFormatada + " [INFO] Chamado aberto com sucesso!");
                            Thread.sleep(1000);
                            System.out.println(dataHoraFormatada + " [INFO] Continuando leitura dos demais aparelhos...");
                            break;
                        }

                        Thread.sleep(1000);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Pasta não encontrada ou vazia.");
        }
    }

    public static void criarChamado(String nomeDaIssue){
        Dotenv dotenv = Dotenv.load();
        // Dados de acesso ao Jira Cloud
        // ATENÇÃO: USE VARIÁVEIS DE AMBIENTE!
        String baseUrl = dotenv.get("BASEURL"); // ex: https://seu-dominio.atlassian.net
        String email = dotenv.get("EMAIL_JIRA");
        String apiToken = dotenv.get("API_TOKEN");

        Jira jira = new Jira(baseUrl, email, apiToken);

        try {
            // Cria uma issue no backlog do projeto informado
            String response = jira.createIssue(
                    "TES", // Key do projeto, presente na URL do seu site
                    nomeDaIssue, // Nome da issue
                    "Tarefa" // Tipo da issue: "Task", "Bug", "Story", etc.
            );

            // Exibe o JSON de resposta com os dados da issue criada
            System.out.println(response);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String[] lineSplit(String linha, String divisor) {
        return linha.split(divisor);
    }
}
