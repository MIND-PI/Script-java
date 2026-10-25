package school.sptech;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Clp {
    private Integer id_clp;
    private Integer id_empresa;
    private String apelido;
    private String id_modelo;
    private List<Leitura> leituras = new ArrayList<>();
    private String mac_adress;
    private String nome_modelo;


    public Clp(Integer id_clp, Integer id_empresa, String modelo_clp) {
        this.id_clp = id_clp;
        this.id_empresa = id_empresa;
        this.id_modelo = modelo_clp;
    }

    public Clp(Integer id_clp, Integer id_empresa, String modelo_clp, List<Leitura> leituras) {
        this.id_clp = id_clp;
        this.id_empresa = id_empresa;
        this.id_modelo = modelo_clp;
        this.leituras = leituras;
    }

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
    }

    public Integer getId_clp() {
        return id_clp;
    }

    public void setId_clp(Integer id_clp) {
        this.id_clp = id_clp;
    }

    public Integer getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public List<Leitura> getLeituras() {
        return leituras;
    }

    public String getMac_adress() {
        return mac_adress;
    }

    public void setMac_adress(String mac_adress) {
        this.mac_adress = mac_adress;
    }

    public void setLeituras(List<Leitura> leituras) {
        this.leituras = leituras;
    }

    public String getNome_modelo() { return nome_modelo; }

    public void setNome_modelo(String nome_modelo) { this.nome_modelo = nome_modelo;}

    public String getApelido() { return apelido;}

    public void setApelido(String apelido) { this.apelido = apelido; }



    @Override
    public String toString() {
        return "Clp{" +
                "id_clp=" + id_clp +
                ", id_empresa=" + id_empresa +
                ", modelo_clp='" + id_modelo + '\'' +
                ", leituras=" + leituras +
                '}';
    }

    public String getClpWhereId(List<Clp> clps, Integer id_clp, Integer id_empresa) {
        for (Clp clp: clps) {
            if (clp.getId_clp().equals(id_clp) && clp.getId_empresa().equals(id_empresa)){
                return "Clp{" +
                        "modelo_clp='" + clp.getId_modelo() + '\'' +
                        ", id_clp=" + id_clp +
                        ", id_empresa=" + id_empresa +
                        ", Leituras=" + clp.getLeituras();
            }
        }
        return "Clp não encontrardo";
    }
}
