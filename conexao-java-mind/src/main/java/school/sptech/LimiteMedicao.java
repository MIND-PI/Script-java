package school.sptech;

// Guarda os limites que puxamos do banco
public class LimiteMedicao {
    private String apelido;
    private String unidadeMedida;
    private Integer parametro;

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getParametro() {
        return parametro;
    }

    public void setParametro(Integer parametro) {
        this.parametro = parametro;
    }
}
