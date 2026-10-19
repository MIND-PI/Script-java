package school.sptech;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

// Cria o cliente do S3 com as credenciais da AWS
public class S3Provider {
    //Representa o provedor de credenciais usado pelo SDK.
    private final AwsCredentialsProvider credentials;


    public S3Provider() {
        this.credentials = DefaultCredentialsProvider.create();
        //aplica automaticamente a cadeia de credenciais da AWS.
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(credentials)
                .build();
    }
}
