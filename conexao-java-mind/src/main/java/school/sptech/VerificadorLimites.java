package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class VerificadorLimites {

    // Logs próprios para cada classe
    private static final Logger log = LoggerFactory.getLogger(VerificadorLimites.class);

    // Puxa o último reinício do CLP com base no uptime
    private void detectarReinicio(Clp clp) {
        clp.getLeituras().sort(Comparator.comparing(Leitura::getTimestamp));

        Integer uptimeAnterior = null;
        for (Leitura leitura : clp.getLeituras()) {
            Integer atual = leitura.getUptime_segundos();
            if (atual == null) continue;

            if (uptimeAnterior != null && atual < uptimeAnterior) {
                log.warn("CLP {} reiniciou: uptime caiu de {}s para {}s em {}",
                        clp.getId_clp(), uptimeAnterior, atual, leitura.getTimestamp());
            }
            uptimeAnterior = atual;
        }
    }

    // Compara os valores do bucket com os do banco
    public List<Violacao> verificar(Clp clp, List<LimiteMedicao> limites) {
        log.info("Verificando CLP {} | {} leituras | {} limites",
                clp.getId_clp(), clp.getLeituras().size(), limites.size());

        detectarReinicio(clp);

        // Armazena em pares de chave e valor e mantém na ordem de inserção
        Map<String, Violacao> violacoes = new LinkedHashMap<>();

        for (Leitura leitura : clp.getLeituras()) {
            for (LimiteMedicao limite : limites) {

                Double valor = leitura.getValorPorApelido(limite.getApelido());

                if (valor == null) {
                    log.debug("CLP {}: sem valor para '{}' em {}",
                            clp.getId_clp(), limite.getApelido(), leitura.getTimestamp());
                    continue;
                }

                //abrindo chamado no jira
                if (valor > limite.getParametro()) {
                    // algo virá aqui



                    log.warn("CLP {} | {} = {} {} acima do limite {} | {}",
                            clp.getId_clp(), limite.getApelido(), valor,
                            limite.getUnidadeMedida(), limite.getParametro(),
                            leitura.getTimestamp());

                    Violacao existente = violacoes.get(limite.getApelido());
                    if (existente == null) {
                        violacoes.put(limite.getApelido(),
                                new Violacao(clp.getId_clp(), clp.getModelo_clp(), limite, valor, leitura));
                    } else {
                        existente.registrar(valor, leitura);
                    }
                } else {
                    log.trace("CLP {} | {} = {} dentro do limite {}",
                            clp.getId_clp(), limite.getApelido(), valor, limite.getParametro());
                }
            }
        }

        log.info("CLP {}: {} medidas fora do limite", clp.getId_clp(), violacoes.size());
        return new ArrayList<>(violacoes.values());
    }
}
