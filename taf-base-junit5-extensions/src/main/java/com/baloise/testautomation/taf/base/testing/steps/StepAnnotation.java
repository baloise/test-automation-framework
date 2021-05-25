package com.baloise.testautomation.taf.base.testing.steps;

import java.util.Comparator;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

public class StepAnnotation implements MethodOrderer {

  @Override
  public void orderMethods(MethodOrdererContext context) {
    context.getMethodDescriptors().sort(Comparator.comparingInt(StepAnnotation::getOrder));
  }

  private static int getOrder(MethodDescriptor descriptor) {
    return descriptor.findAnnotation(Step.class).map(Step::value).orElse(Step.DEFAULT);
  }
}
