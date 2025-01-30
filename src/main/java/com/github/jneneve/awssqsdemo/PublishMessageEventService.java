package com.github.jneneve.awssqsdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishMessageEventService implements PublishMessageEventUseCase {

  private final MessageQueueProvider messageQueueProvider;

  @Override
  public void execute(String message) {
    log.info("Publishing message: {}", message);
    messageQueueProvider.sendMessage(message);
  }
}
