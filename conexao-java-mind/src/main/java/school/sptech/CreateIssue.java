package school.sptech;

import java.io.IOException;
import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import school.sptech.config.Jira;

public class CreateIssue {

    public void criarChamado(Clp clp, List<Violacao> violacoes){
        Dotenv dotenv = Dotenv.load();
        Jira jira = new Jira(dotenv.get("BASEURL"), dotenv.get("EMAIL_JIRA"), dotenv.get("API_TOKEN"));

        // Cria a descrição para o chamado de cada leitura fora do limite
        for (Violacao violacao : violacoes) {

            String titulo = "CLP " + clp.getMac_adress() + " : " + violacoes.size() + " medida(s) fora do limite";

            StringBuilder descricao = new StringBuilder();
            descricao.append("Execução em ").append(java.time.LocalDateTime.now()).append("\n\n");


            descricao.append(violacao.getApelido())
                    .append(" (limite ").append(violacao.getParametro())
                    .append(" ").append(violacao.getUnidadeMedida())
                    .append(")\n");
            descricao.append("Id CLP: ").append(clp.getId_clp()).append("\n");
            descricao.append(" Maior valor: ").append(violacao.getMaiorValor()).append("\n");
            descricao.append(" Ocorrências: ").append(violacao.getOcorrencias()).append("\n");
            descricao.append(" Primeira ocorrência: ").append(violacao.getPrimeiraOcorrencia()).append("\n");
            descricao.append(" Processos no pico: ").append(violacao.getProcessosNoPico()).append("\n");
            descricao.append(" Leituras fora do limite:\n");

            // Mostra todos os horários e valores registrados em cada vez
            // que a medida esteve fora do limite
            for (String linha : violacao.getDetalhesChamado()) {
                descricao.append("    - ").append(linha).append("\n");
            }
            descricao.append("\n");


            try{
                String response = jira.createIssue(
                        dotenv.get("KEY_WORKSPACE"), // Key do projeto, presente na URL do seu site
                        titulo, // Nome da issue
                        "Tarefa", // Tipo da issue: "Task", "Bug", "Story", etc.
                        descricao.toString()
                );

                // Exibe o JSON de resposta com os dados da issue criada
                System.out.println(response);
            }
            catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
