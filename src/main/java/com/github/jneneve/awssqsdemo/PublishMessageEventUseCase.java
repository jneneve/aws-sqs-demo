package com.github.jneneve.awssqsdemo;

@FunctionalInterface
public interface PublishMessageEventUseCase {

  void execute(String message);
}
