package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

// Consulta os limites no banco
public class LimiteDao {
    // Logs próprios da classe
    private static final Logger log = LoggerFactory.getLogger(LimiteDao.class);

    // Conexão do banco
    private final JdbcTemplate template;

    public LimiteDao(JdbcTemplate template) {
        this.template = template;
    }

    public List<LimiteMedicao> buscarLimites(Integer idClp){
        String sql = "SELECT tm.apelido, tm.unidade_medida, cm.parametro " +
                "FROM clp_medicao cm " +
                "JOIN tipo_medicao tm ON " +
                "tm.idTipoMedicao = cm.tipoMedicaoId " +
                "WHERE cm.clpId = ? AND cm.ativo = 1";

        List<LimiteMedicao> limites = template.query(
                sql, new BeanPropertyRowMapper<>(LimiteMedicao.class), idClp);

        log.debug("CLP {}: {} limites encontrados no banco", idClp, limites.size());
        return limites;
    }
}
