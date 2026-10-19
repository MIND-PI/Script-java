package school.sptech;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class S3Objects {
    private static final Logger log = LoggerFactory.getLogger(S3Objects.class);

    public void getS3Objects(String nomeBucket, String prefixo) {
        // Instanciando o cliente S3 através do S3Provider criado anteriormente
        S3Provider s3Provider = new S3Provider();
        S3Client s3Client = s3Provider.getS3Client();

        // formato do nome dos arquivos: ano-mes-dia_hora-minuto_idEmpresa-idClp.csv

        List<String> tempos = new ArrayList<>();
        LocalDateTime dtaHora = LocalDateTime.now();

        List<S3Object> objetos = new ArrayList<>();

        for (int i = 15; i > 0; i--) {
            LocalDateTime dtaHoraMinus = dtaHora.minusMinutes(i);
            DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-M-d_H-m");
            String tempoMinus =  dtaHoraMinus.format(formatoDataHora);
            tempos.add(tempoMinus);
        }
        try {
            for (String tempo : tempos) {
                ListObjectsRequest requisicao = ListObjectsRequest.builder()
                        .bucket(nomeBucket)
                        .prefix(prefixo + tempo)
                        .build();

                // Listando as informações dos objetos
                List<S3Object> objects = s3Client.listObjects(requisicao).contents();
                if (objects != null && !objects.isEmpty()) {
                    objetos.addAll(objects);
                }
            }
        } catch (NoSuchKeyException keyException) {
            System.out.println("Prefixo incorreto");
        }

        try {
            // Faz a requisição para a S3      ↓ pega o conteudo returnado
            for (S3Object object : objetos) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(nomeBucket)
                        .key(object.key())
                        .build();

                // Caminho da pasta de destino
                Path destino = Path.of(prefixo);
                //Se não existir cria a pasta
                if (!Files.exists(destino)) {
                    Files.createDirectories(destino);
                    System.out.println("A pasta de destino foi criada");
                }
                String caminho = object.key();
                // Faz a requisição para a S3      ↓ pega o conteudo returnado e tranforma em algo legivel para o Java
                try(InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());) {
                    Files.copy(inputStream, Path.of(caminho));
                    System.out.println("Arquivo baixado: " + object.key());
                } catch (IOException | S3Exception e) {
                    System.out.println("Erro ao baixar " + object.key() + ": " + e.getMessage());
                }
            }
        } catch (S3Exception e) {
            System.out.println("Erro ao buscar arquivos " + e.getMessage());
        } catch (IOException IO) {
            System.err.println("Erro ao fazer download dos arquivos: " + IO.getMessage());
        }
    }

    public void deleteCsvs(String pasta) {
        // Nome da pasta a ser excluído
        File pastaPref = new File(String.valueOf(pasta));

        // Se a pasta existe, listo os arquivos da pasta e excluído cada um
        if(pastaPref.exists()) {
            File[] arquivos = pastaPref.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    arquivo.delete();
                }
                System.out.println("Arquivos apagados");
            }

            if (pastaPref.delete()){
                System.out.println("Pasta " + pastaPref +" deletada com sucesso");
            }
        }
    }

    // Envio dos logs
    public void enviarLog(String nomeBucket, String chave, Path arquivo){
        // Busca qual é o bucket
        S3Client s3Client = new S3Provider().getS3Client();

        // Diz para onde vai o arquivo
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(nomeBucket)
                .key(chave)
                .contentType("text/plain")
                .build();

        // RequestBody.fromFile(arquivo): diz o que vai enviar (conteúdo)
        s3Client.putObject(request, RequestBody.fromFile(arquivo));
    }

}










