package school.sptech;

import java.io.IOException;

import io.github.cdimascio.dotenv.Dotenv;
import school.sptech.config.Jira;

public class CreateIssue {

    public void criarChamado(String nomeDaIssue) {
        Dotenv dotenv = Dotenv.load();

        String baseUrl = dotenv.get("BASEURL"); // ex: https://seu-dominio.atlassian.net
        String email = dotenv.get("EMAIL_JIRA");
        String apiToken = dotenv.get("API_TOKEN");

        Jira jira = new Jira(baseUrl, email, apiToken);

        try {
            // Cria uma issue no backlog do projeto informado
            String response = jira.createIssue(
                    "TI", // Key do projeto, presente na URL do seu site
                    nomeDaIssue, // Nome da issue
                    "Tarefa" // Tipo da issue: "Task", "Bug", "Story", etc.
            );

            // Exibe o JSON de resposta com os dados da issue criada
            System.out.println(response);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
