package school.sptech;

import java.time.LocalDateTime;
import java.util.Locale;

public class Leitura {
    private LocalDateTime timestamp;
    private Double porcentagem_uso_cpu;
    private Double ram_total_gb;
    private Double ram_disponivel_gb;
    private Double ram_usada_gb;
    private Double porcentagem_uso_ram;
    private Double porcentagem_uso_swap;
    private Integer uptime_segundos;
    private Double scan_time_ms;
    private Double latencia_ms;
    private Integer perda_pacotes;
    private Double vel_upload;
    private Double vel_download;
    private String top1_processo;
    private Double top1_cpu;
    private Double top1_ram;
    private String top2_processo;
    private Double top2_cpu;
    private Double top2_ram;
    private String top3_processo;
    private Double top3_cpu;
    private Double top3_ram;

    public Leitura() {
    }

    public Leitura(LocalDateTime timestamp, Double porcentagem_uso_cpu, Double ram_total_gb,
                   Double ram_disponivel_gb, Double ram_usada_gb, Double porcentagem_uso_ram,
                   Double porcentagem_uso_swap, Integer uptime_segundos, Double scan_time_ms,
                   Double latencia_ms, Integer perda_pacotes, Double vel_upload, Double vel_download,
                   String top1_processo, Double top1_cpu, Double top1_ram, String top2_processo,
                   Double top2_cpu, Double top2_ram, String top3_processo, Double top3_cpu,
                   Double top3_ram) {
        this.timestamp = timestamp;
        this.porcentagem_uso_cpu = porcentagem_uso_cpu;
        this.ram_total_gb = ram_total_gb;
        this.ram_disponivel_gb = ram_disponivel_gb;
        this.ram_usada_gb = ram_usada_gb;
        this.porcentagem_uso_ram = porcentagem_uso_ram;
        this.porcentagem_uso_swap = porcentagem_uso_swap;
        this.uptime_segundos = uptime_segundos;
        this.scan_time_ms = scan_time_ms;
        this.latencia_ms = latencia_ms;
        this.perda_pacotes = perda_pacotes;
        this.vel_upload = vel_upload;
        this.vel_download = vel_download;
        this.top1_processo = top1_processo;
        this.top1_cpu = top1_cpu;
        this.top1_ram = top1_ram;
        this.top2_processo = top2_processo;
        this.top2_cpu = top2_cpu;
        this.top2_ram = top2_ram;
        this.top3_processo = top3_processo;
        this.top3_cpu = top3_cpu;
        this.top3_ram = top3_ram;
    }

    public Double getValorPorApelido(String apelido){
        apelido = apelido.trim().toUpperCase();

        if(apelido.equals("CPU")){
            return porcentagem_uso_cpu;
        }
        if(apelido.equals("RAM")){
            return porcentagem_uso_ram;
        }
        if(apelido.equals("SWAP")){
            return porcentagem_uso_swap;
        }
        if(apelido.equals("SCAN")){
            return scan_time_ms;
        }
        if(apelido.equals("PING")){
            return latencia_ms;
        }
        if(apelido.equals("LOSS")){
            return  perda_pacotes == null ? null : perda_pacotes.doubleValue();
        }
        if(apelido.equals("UPLOAD")){
            return vel_upload;
        }
        if(apelido.equals("DOWNLOAD")){
            return vel_download;
        }

        return null;
    }

    public String getResumoProcessos(){
        return top1_processo + " (CPU " + top1_cpu + "%, RAM " + top1_ram + "%), "
                + top2_processo + " (CPU " + top2_cpu + "%, RAM " + top2_ram + "%), "
                + top3_processo + " (CPU " + top3_cpu + "%, RAM " + top3_ram + "%)";
    }



    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getPorcentagem_uso_cpu() {
        return porcentagem_uso_cpu;
    }

    public void setPorcentagem_uso_cpu(Double porcentagem_uso_cpu) {
        this.porcentagem_uso_cpu = porcentagem_uso_cpu;
    }

    public Double getRam_total_gb() {
        return ram_total_gb;
    }

    public void setRam_total_gb(Double ram_total_gb) {
        this.ram_total_gb = ram_total_gb;
    }

    public Double getRam_disponivel_gb() {
        return ram_disponivel_gb;
    }

    public void setRam_disponivel_gb(Double ram_disponivel_gb) {
        this.ram_disponivel_gb = ram_disponivel_gb;
    }

    public Double getRam_usada_gb() {
        return ram_usada_gb;
    }

    public void setRam_usada_gb(Double ram_usada_gb) {
        this.ram_usada_gb = ram_usada_gb;
    }

    public Double getPorcentagem_uso_ram() {
        return porcentagem_uso_ram;
    }

    public void setPorcentagem_uso_ram(Double porcentagem_uso_ram) {
        this.porcentagem_uso_ram = porcentagem_uso_ram;
    }

    public Double getPorcentagem_uso_swap() {
        return porcentagem_uso_swap;
    }

    public void setPorcentagem_uso_swap(Double porcentagem_uso_swap) {
        this.porcentagem_uso_swap = porcentagem_uso_swap;
    }

    public Double getScan_time_ms() {
        return scan_time_ms;
    }

    public void setScan_time_ms(Double scan_time_ms) {
        this.scan_time_ms = scan_time_ms;
    }

    public Double getLatencia_ms() {
        return latencia_ms;
    }

    public void setLatencia_ms(Double latencia_ms) {
        this.latencia_ms = latencia_ms;
    }

    public Integer getPerda_pacotes() {
        return perda_pacotes;
    }

    public void setPerda_pacotes(Integer perda_pacotes) {
        this.perda_pacotes = perda_pacotes;
    }

    public Double getVel_upload() {
        return vel_upload;
    }

    public void setVel_upload(Double vel_upload) {
        this.vel_upload = vel_upload;
    }

    public Double getVel_download() {
        return vel_download;
    }

    public void setVel_download(Double vel_download) {
        this.vel_download = vel_download;
    }

    public String getTop1_processo() {
        return top1_processo;
    }

    public void setTop1_processo(String top1_processo) {
        this.top1_processo = top1_processo;
    }

    public String getTop2_processo() {
        return top2_processo;
    }

    public void setTop2_processo(String top2_processo) {
        this.top2_processo = top2_processo;
    }

    public String getTop3_processo() {
        return top3_processo;
    }

    public void setTop3_processo(String top3_processo) {
        this.top3_processo = top3_processo;
    }

    public Integer getUptime_segundos() {
        return uptime_segundos;
    }

    public void setUptime_segundos(Integer uptime_segundos) {
        this.uptime_segundos = uptime_segundos;
    }

    public Double getTop1_cpu() {
        return top1_cpu;
    }

    public void setTop1_cpu(Double top1_cpu) {
        this.top1_cpu = top1_cpu;
    }

    public Double getTop1_ram() {
        return top1_ram;
    }

    public void setTop1_ram(Double top1_ram) {
        this.top1_ram = top1_ram;
    }

    public Double getTop2_cpu() {
        return top2_cpu;
    }

    public void setTop2_cpu(Double top2_cpu) {
        this.top2_cpu = top2_cpu;
    }

    public Double getTop2_ram() {
        return top2_ram;
    }

    public void setTop2_ram(Double top2_ram) {
        this.top2_ram = top2_ram;
    }

    public Double getTop3_cpu() {
        return top3_cpu;
    }

    public void setTop3_cpu(Double top3_cpu) {
        this.top3_cpu = top3_cpu;
    }

    public Double getTop3_ram() {
        return top3_ram;
    }

    public void setTop3_ram(Double top3_ram) {
        this.top3_ram = top3_ram;
    }

    @Override
    public String toString() {
        return "Leitura{" +
                "timestamp=" + timestamp +
                ", porcentagem_uso_cpu=" + porcentagem_uso_cpu +
                ", ram_total_gb=" + ram_total_gb +
                ", ram_disponivel_gb=" + ram_disponivel_gb +
                ", ram_usada_gb=" + ram_usada_gb +
                ", porcentagem_uso_ram=" + porcentagem_uso_ram +
                ", porcentagem_uso_swap=" + porcentagem_uso_swap +
                ", uptime_segundos=" + uptime_segundos +
                ", scan_time_ms=" + scan_time_ms +
                ", latencia_ms=" + latencia_ms +
                ", perda_pacotes=" + perda_pacotes +
                ", vel_upload=" + vel_upload +
                ", vel_download=" + vel_download +
                ", top1_processo='" + top1_processo + '\'' +
                ", top1_cpu=" + top1_cpu +
                ", top1_ram=" + top1_ram +
                ", top2_processo='" + top2_processo + '\'' +
                ", top2_cpu=" + top2_cpu +
                ", top2_ram=" + top2_ram +
                ", top3_processo='" + top3_processo + '\'' +
                ", top3_cpu=" + top3_cpu +
                ", top3_ram=" + top3_ram +
                '}';
    }
}
