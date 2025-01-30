package com.github.jneneve.awssqsdemo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AwsSqsAdapterTest extends LocalStackContainerTestBase {

  @Autowired private AwsSqsAdapter awsSqsAdapter;

  @BeforeEach
  void cleanQueue() {
    awsSqsAdapter.purgeQueue();
  }

  @Test
  void givenMessage_whenSendMessageExecuted_thenMessageIsPublishedAndRetrievable() {
    final var testMessage = "Test message";

    awsSqsAdapter.sendMessage(testMessage);

    final var messages = awsSqsAdapter.receiveMessages();
    final var messageFound =
        messages.stream().anyMatch(message -> message.body().equals(testMessage));
    assertTrue(messageFound, "The message was not found in the SQS queue");
  }
}
