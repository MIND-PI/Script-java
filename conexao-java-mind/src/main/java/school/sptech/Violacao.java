package school.sptech;

import java.time.LocalDateTime;

public class Violacao {
    private Integer idClp;
    private String apelido;
    private String unidadeMedida;
    private Integer parametro;
    private Double maiorValor;
    private int ocorrencias;
    private LocalDateTime primeiraOcorrencia;
    private String processosNoPico;

    public Violacao(Integer idClp, LimiteMedicao limite, Double valor, Leitura leitura) {
        this.idClp = idClp;
        this.apelido = limite.getApelido();
        this.unidadeMedida = limite.getUnidadeMedida();
        this.parametro = limite.getParametro();
        this.maiorValor = valor;
        this.ocorrencias = 1;
        this.primeiraOcorrencia = leitura.getTimestamp();
        this.processosNoPico = leitura.getResumoProcessos();
    }

    public void registrar(Double valor, Leitura leitura){
        this.ocorrencias++;
        if(valor > this.maiorValor){
            this.maiorValor = valor;
            this.processosNoPico = leitura.getResumoProcessos();
        }
    }

    public Integer getIdClp() {
        return idClp;
    }

    public void setIdClp(Integer idClp) {
        this.idClp = idClp;
    }

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

    public Double getMaiorValor() {
        return maiorValor;
    }

    public void setMaiorValor(Double maiorValor) {
        this.maiorValor = maiorValor;
    }

    public int getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(int ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public LocalDateTime getPrimeiraOcorrencia() {
        return primeiraOcorrencia;
    }

    public void setPrimeiraOcorrencia(LocalDateTime primeiraOcorrencia) {
        this.primeiraOcorrencia = primeiraOcorrencia;
    }

    public String getProcessosNoPico() {
        return processosNoPico;
    }

    public void setProcessosNoPico(String processosNoPico) {
        this.processosNoPico = processosNoPico;
    }
}
