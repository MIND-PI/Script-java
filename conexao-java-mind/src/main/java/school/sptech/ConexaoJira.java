package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConexaoJira {
    private static final Logger log = LoggerFactory.getLogger(ConexaoJira.class);

    public void abrirChamado(Violacao v) {
        // Titulo do chamado
        String titulo = "CLP " + v.getIdClp() + ": " + v.getApelido() + " acima do limite";

        // Descrição do chamado
        String descricao = "Maior valor: " + v.getMaiorValor() + " " + v.getUnidadeMedida()
                + " (limite " + v.getParametro() + ")\n"
                + "Ocorrências: " + v.getOcorrencias() + "\n"
                + "Primeira ocorrência: " + v.getPrimeiraOcorrencia() + "\n"
                + "Processos no pico: " + v.getProcessosNoPico();

        log.info("Abrindo chamado no Jira: {}", titulo);
        log.debug("Descrição do chamado:\n{}", descricao);

        // Chamada da API do Jira
        log.info("Chamado aberto com sucesso (simulado)");
    }
}