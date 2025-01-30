package com.github.jneneve.awssqsdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublishMessageEventController {

  private final PublishMessageEventUseCase publishMessageEventUseCase;

  @PostMapping("${v1.aws-sqs-demo.publish-message}")
  public ResponseEntity<Void> publishMessageEvent(@RequestBody String message) {
    publishMessageEventUseCase.execute(message);
    return ResponseEntity.ok().build();
  }
}
