package com.github.jneneve.awssqsdemo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AwsSqsConfigTest extends LocalStackContainerTestBase {

  @Autowired private AwsSqsConfig awsSqsConfig;

  @Test
  void givenSqsAsyncClient_whenInitialized_thenPropertiesAreNotNull() {
    final var sqsAsyncClient = awsSqsConfig.sqsAsyncClient();

    assertNotNull(sqsAsyncClient, "SqsAsyncClient should not be null");
    assertNotNull(sqsAsyncClient.serviceName(), "Service name should not be null");
    assertNotNull(
        sqsAsyncClient.serviceClientConfiguration().region(), "Region should not be null");
    assertNotNull(
        sqsAsyncClient.serviceClientConfiguration().endpointOverride(),
        "Endpoint override should not be null");
    assertNotNull(
        sqsAsyncClient.serviceClientConfiguration().credentialsProvider(),
        "Credentials provider should not be null");
  }
}
