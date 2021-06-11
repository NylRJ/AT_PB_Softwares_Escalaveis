package com.i9developement.transactionsvc.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.i9developement.transactionsvc.repository.DynamoRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@EnableDynamoDBRepositories(basePackageClasses = DynamoRepository.class)
public class DynamoDBConfig {


    public static final String TRANSACAO = "transacao";
    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;


    @Bean
    @Primary
    public DynamoDBMapperConfig dynamoDBMapperConfig(){return DynamoDBMapperConfig.DEFAULT;}



    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB,
                                         DynamoDBMapperConfig config){
        return new DynamoDBMapper(amazonDynamoDB, config);
    }

    @Bean
    @Primary
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(amazonAWSCredentials())
                .withRegion(Regions.US_WEST_2)
                .build();


        return amazonDynamoDB;

    }

    @Bean
    public DynamoDB getDatabase(AmazonDynamoDB amazonDynamoDB) {

        return new DynamoDB(amazonDynamoDB);
    }

    @Bean
    public AWSCredentialsProvider amazonAWSCredentials() {
//        var key = new String(Base64.getDecoder().decode(amazonAWSAccessKey));
//        var secret = new String(Base64.getDecoder().decode(amazonAWSSecretKey));

        return
                new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));


    }
}
