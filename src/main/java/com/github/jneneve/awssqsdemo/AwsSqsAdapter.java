package com.github.jneneve.awssqsdemo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsSqsAdapter implements MessageQueueProvider {

  @Value("${aws.sqs.queue.name}")
  private String queueName;

  private final SqsAsyncClient sqsAsyncClient;

  @Override
  public void sendMessage(String message) {
    try {
      final var queueUrl = getQueueUrl();
      final var sendMessageRequest =
          SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build();
      sqsAsyncClient.sendMessage(sendMessageRequest);
      log.info("Message sent to SQS: {}", message);
    } catch (Exception e) {
      log.error("Error while sending message to SQS", e);
      throw new RuntimeException("Error while sending message to SQS", e);
    }
  }

  @Override
  public List<Message> receiveMessages() {
    try {
      final var queueUrl = getQueueUrl();
      final var receiveMessageRequest =
          ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(10).build();
      return sqsAsyncClient.receiveMessage(receiveMessageRequest).get().messages();
    } catch (Exception e) {
      log.error("Error while receiving messages from SQS", e);
      throw new RuntimeException("Error while receiving messages from SQS", e);
    }
  }

  @Override
  public void purgeQueue() {
    try {
      final var queueUrl = getQueueUrl();
      final var purgeQueueRequest = PurgeQueueRequest.builder().queueUrl(queueUrl).build();
      sqsAsyncClient.purgeQueue(purgeQueueRequest);
      log.info("Queue purged: {}", queueName);
    } catch (Exception e) {
      log.error("Error while purging the SQS queue", e);
      throw new RuntimeException("Error while purging the SQS queue", e);
    }
  }

  private String getQueueUrl() throws Exception {
    final var getQueueUrlRequest = GetQueueUrlRequest.builder().queueName(queueName).build();
    final var getQueueUrlResponse = sqsAsyncClient.getQueueUrl(getQueueUrlRequest).get();
    log.debug("Queue URL: {}", getQueueUrlResponse.queueUrl());
    return getQueueUrlResponse.queueUrl();
  }
}
