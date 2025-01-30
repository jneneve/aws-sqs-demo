package com.github.jneneve.awssqsdemo;

import java.net.URI;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSqsConfig {

  @Value("${aws.region}")
  private String region;

  @Value("${aws.sqs.endpoint}")
  private String sqsEndpoint;

  @Bean
  SqsAsyncClient sqsAsyncClient() {
    try {
      return SqsAsyncClient.builder()
          .endpointOverride(URI.create(sqsEndpoint))
          .region(Region.of(region))
          .build();
    } catch (Exception e) {
      throw new BeanCreationException("Error while creating SQS client", e);
    }
  }
}
