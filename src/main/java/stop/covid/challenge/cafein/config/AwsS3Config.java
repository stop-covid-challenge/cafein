package stop.covid.challenge.cafein.config;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretKey;

    @Value("{cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3Client amazonS3Client() {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        InstanceProfileCredentialsProvider provider = new InstanceProfileCredentialsProvider(true);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(provider)
//            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }

}
