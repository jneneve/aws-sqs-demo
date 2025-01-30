package com.github.jneneve.awssqsdemo;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

public abstract class LocalStackContainerTestBase {

  static LocalStackContainer localStack =
      new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
          .withServices(LocalStackContainer.Service.SQS)
          .withCopyFileToContainer(
              MountableFile.forHostPath("./init-aws.sh"),
              "/etc/localstack/init/ready.d/init-aws.sh")
          .withStartupTimeout(Duration.of(2, ChronoUnit.MINUTES));

  static {
    localStack.start();
  }

  @BeforeAll
  static void setup() {
    System.setProperty("aws.accessKeyId", localStack.getAccessKey());
    System.setProperty("aws.secretAccessKey", localStack.getSecretKey());
    System.setProperty("aws.region", "us-east-1");
    System.setProperty(
        "aws.sqs.endpoint",
        localStack.getEndpointOverride(LocalStackContainer.Service.SQS).toString());
  }
}
