package school.sptech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

// Consulta os limites no banco
public class LimiteDao {
    String mac_adress = null;
    String nome_modelo = null;

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
                "WHERE cm.clpId = ? AND cm.status = 1";

        List<LimiteMedicao> limites = template.query(
                sql, new BeanPropertyRowMapper<>(LimiteMedicao.class), idClp);

        System.out.println("Buscando limites no banco de dados...");
        log.debug("CLP {}: {} limites encontrados no banco", idClp, limites.size());
        return limites;
    }

    public String buscarMacAdress(Integer idClp){
        String selectMac = "SELECT mac_address FROM clp WHERE idClp = ?";

        try {
            mac_adress = template.queryForObject(selectMac, String.class, idClp);
        } catch (EmptyResultDataAccessException e) {
            log.warn("CLP {} não encontrado na tabela clp", idClp);
            mac_adress = null;
        }
        return mac_adress;
    }

    public String buscarNomeModelo(Integer idClp){
        String selectNomeModelo = "SELECT m.nome\n" +
                "FROM clp c\n" +
                "JOIN modelo m ON c.modeloId = m.idmodelo\n" +
                "WHERE c.idClp = ?;";

        try{
            nome_modelo = template.queryForObject(selectNomeModelo, String.class, idClp);
        }
        catch (EmptyResultDataAccessException e){
            log.warn("CLP {} não encontrado na tabela clp", idClp);
            mac_adress = null;
        }
        return nome_modelo;
    }

    public String buscarNomeClp(Integer idClp){
        String selectNomeClp = "SELECT nome FROM clp WHERE idClp = ?";

        try{
            nome_modelo = template.queryForObject(selectNomeClp, String.class, idClp);
        }
        catch (EmptyResultDataAccessException e){
            log.warn("CLP {} não encontrado na tabela clp", idClp);
            mac_adress = null;
        }
        return nome_modelo;
    }


}
