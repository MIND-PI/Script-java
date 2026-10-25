package school.sptech;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 	Lê os CSVs e monta a lista de Clp com Leitura
public class Arquivo {

    private static final Logger log = LoggerFactory.getLogger(Arquivo.class);

    public List<Clp> lerCsvs(String prefixo) {

        List<Clp> clps = new ArrayList<>();

        // definindo o nome da pasta em que acontecerá a busca
        String nome = prefixo;
        File pastaPref = new File(String.valueOf(nome));

        // Se a pasta existe, lista os arquivos dentro da pasta
        if (pastaPref.exists()) {
            File[] arquivos = pastaPref.listFiles();

            for (File arquivo : arquivos) {

                // Define o formato do cvs lido
                CSVFormat formato = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build();

                System.out.println("Iniciando leitura dos CSVs");
                try (
                        CSVParser parser = new CSVParser(new FileReader(arquivo, StandardCharsets.UTF_8), formato)
                ) {
                    for (CSVRecord registro : parser) {
                        // Inserindo as informações do arquivo em variáveis
                        try {
                            String modelo_clp = registro.get("modelo_clp");
                            Integer id_clp = Integer.parseInt(registro.get("id_clp"));
                            Integer id_empresa = Integer.parseInt(registro.get("id_empresa"));

                            Clp clp = buscarCpl(clps, id_clp, id_empresa, modelo_clp);

                            if (clp == null) {
                                clp = new Clp(id_clp, id_empresa, modelo_clp);
                                System.out.printf("\n   Criando novo objeto CLP: id_clp - %d, id_empresa - %d, modelo - %s", id_clp, id_empresa, modelo_clp);
                                clps.add(clp);
                                System.out.println("\n   Adicionando o CLP criado a lista de objetos");
                            }

                            LocalDateTime timestamp = LocalDateTime.parse(registro.get("timestamp").trim().replace(" ", "T"));
                            Double porcentagem_uso_cpu = lerDouble(registro, "porcentagem_uso_cpu");
                            Double ram_total_gb = lerDouble(registro,"ram_total_gb");
                            Double ram_disponivel_gb = lerDouble(registro, "ram_disponivel_gb");
                            Double ram_usada_gb = lerDouble(registro,"ram_usada_gb");
                            Double porcentagem_uso_ram = lerDouble(registro,"porcentagem_uso_ram");
                            Double porcentagem_uso_swap = lerDouble(registro, "porcentagem_uso_swap");
                            Integer uptime_segundos = lerInteger(registro, "uptime_segundos");
                            Double scan_time_ms = lerDouble(registro,"scan_time_ms");
                            Double latencia_ms = lerDouble(registro, "latencia_ms");
                            Integer perda_pacotes = lerInteger(registro, "perda_pacotes");
                            Double vel_upload = lerDouble(registro, "vel_upload");
                            Double vel_download = lerDouble(registro, "vel_download");
                            String top1_processo = lerString(registro, "top1_processo");
                            Double top1_cpu = lerDouble(registro, "top1_cpu");
                            Double top1_ram = lerDouble(registro, "top1_ram");
                            String top2_processo = lerString(registro,"top2_processo");
                            Double top2_cpu = lerDouble(registro, "top2_cpu");
                            Double top2_ram = lerDouble(registro, "top2_ram");
                            String top3_processo = lerString(registro, "top3_processo");
                            Double top3_cpu = lerDouble(registro, "top3_cpu");
                            Double top3_ram = lerDouble(registro, "top3_ram");

                            //System.out.println("\nInserindo informações no objeto " + clp);

                            // Adicionando a letura a lista
                            clp.getLeituras().add(new Leitura (timestamp, porcentagem_uso_cpu, ram_total_gb, ram_disponivel_gb, ram_usada_gb, porcentagem_uso_ram, porcentagem_uso_swap, uptime_segundos, scan_time_ms, latencia_ms, perda_pacotes, vel_upload, vel_download, top1_processo, top1_cpu, top1_ram, top2_processo, top2_cpu, top2_ram, top3_processo, top3_cpu, top3_ram));

                        } catch (NumberFormatException | DateTimeParseException e) {
                            System.out.println("Linha " + registro.getRecordNumber() + " ignorada: " + e.getMessage());
                        } catch (IllegalArgumentException illegalArgumentException) {
                            System.out.println("Coluna faltante ou errado " + illegalArgumentException.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Erro ao ler o CSV: " + e.getMessage());
                }
            }
        }
        // Retorno que permite a visualização dos CLPs
        return clps;
    }

    public Clp buscarCpl(List<Clp> clps, Integer id_clp, Integer id_empresa, String modelo) {
        System.out.printf("\nBuscando CLP id_clp - %d, id_empresa - %d, modelo - %s", id_clp, id_empresa, modelo);
        for (Clp clp : clps) {
            if (clp.getId_empresa().equals(id_empresa) && clp.getId_clp().equals(id_clp) && clp.getId_modelo().equals(modelo)){
                return clp;
            }
        }
        return null;
    }

    private static Integer lerInteger(CSVRecord r, String coluna) {
        String v = r.get(coluna);
        return (v == null || v.isBlank()) ? null : Integer.parseInt(v.trim());
    }

    private static Double lerDouble(CSVRecord r, String coluna) {
        String v = r.get(coluna);
        return (v == null || v.isBlank()) ? null : Double.parseDouble(v.trim());
    }

    private static String lerString(CSVRecord r, String coluna) {
        String v = r.get(coluna);
        return (v == null || v.isBlank()) ? null : v.trim();
    }
}
