package com.github.jneneve.awssqsdemo;

import java.util.List;
import software.amazon.awssdk.services.sqs.model.Message;

public interface MessageQueueProvider {

  void sendMessage(String message);

  List<Message> receiveMessages();

  void purgeQueue();
}
