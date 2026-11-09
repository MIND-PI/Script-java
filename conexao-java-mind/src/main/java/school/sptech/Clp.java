package school.sptech;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Clp {
    private Integer id_clp;
    private Integer id_empresa;
    private String apelido;
    private Integer id_modelo;
    private List<Leitura> leituras = new ArrayList<>();
    private String mac_adress;
    private String nome_modelo;

    private static List<Clp> CLPS;


    public Clp(Integer id_clp, Integer id_empresa, String apelido, Integer id_modelo, String mac_adress, String nome_modelo) {
        this.id_clp = id_clp;
        this.id_empresa = id_empresa;
        this.apelido = apelido;
        this.id_modelo = id_modelo;
        this.mac_adress = mac_adress;
        this.nome_modelo = nome_modelo;
        leituras = new ArrayList<>();
    }

    public static Clp getClpWhereId(Integer id_clp, Integer id_empresa) {
        for (Clp clp: CLPS) {
            if (clp.getId_clp().equals(id_clp) && clp.getId_empresa().equals(id_empresa)){
                return clp;
            }
        }
        return null;
    }

    public static Clp getClpWhereId(Integer id_clp, Integer id_empresa, Integer id_modelo) {
        for (Clp clp: CLPS) {
            if (clp.getId_clp().equals(id_clp) && clp.getId_empresa().equals(id_empresa) && clp.getId_modelo().equals(id_modelo)){
                return clp;
            }
        }
        return null;
    }

    public static void adicionarClpALista(Clp clp) {
        if (getClpWhereId(clp.getId_clp(), clp.getId_empresa(), clp.getId_modelo()) == null){
            CLPS.add(clp);
        }
    }

    public static List<Clp> getCLPS() {
        return CLPS;
    }

    public Integer getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(Integer id_modelo) {
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
                ", apelido='" + apelido + '\'' +
                ", id_modelo=" + id_modelo +
                ", leituras=" + leituras +
                ", mac_adress='" + mac_adress + '\'' +
                ", nome_modelo='" + nome_modelo + '\'' +
                '}';
    }
}
