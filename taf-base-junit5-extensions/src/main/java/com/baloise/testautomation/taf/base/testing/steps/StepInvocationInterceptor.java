package com.baloise.testautomation.taf.base.testing.steps;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.baloise.testautomation.taf.base.testing.steps.Step.StepMax;
import com.baloise.testautomation.taf.base.testing.steps.Step.StepMin;
import com.baloise.testautomation.taf.base.testing.steps.Step.SkipAfterFailed;

public class StepInvocationInterceptor implements InvocationInterceptor, BeforeAllCallback, TestWatcher {

  private boolean canContinue = true;

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    Optional<Class<?>> testClass = context.getTestClass();
    if (testClass.isPresent()) {
      SkipAfterFailed skipAfterFailedAnnotation = testClass.get().getAnnotation(SkipAfterFailed.class);
      if (skipAfterFailedAnnotation != null) {
        if (!skipAfterFailedAnnotation.value()) {
          return;
        }
      }
    }
    canContinue = false;
  }

  private int stepMin = Integer.MIN_VALUE;
  private int stepMax = Integer.MAX_VALUE;

  @Override
  public void interceptTestMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext,
      ExtensionContext extensionContext) throws Throwable {
    Step step = invocationContext.getExecutable().getAnnotation(Step.class);
    if (step == null) {
      invocation.proceed();
      return;
    }
    Assumptions.assumeTrue(canContinue, "Previously failed -> skip: " + invocationContext.getExecutable().getName());
    if (step.value() >= stepMin && step.value() <= stepMax) {
      invocation.proceed();
    }
    else {
      Assumptions.assumeFalse(step.value() > stepMax,
          "Current step index is greater than @StepMax -> skip: " + invocationContext.getExecutable().getName());
      Assumptions.assumeFalse(step.value() < stepMin,
          "Current step index is smaller than @StepMin -> skip: " + invocationContext.getExecutable().getName());
    }
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Optional<Class<?>> testClass = context.getTestClass();
    if (testClass.isPresent()) {
      StepMin stepMinAnnotation = testClass.get().getAnnotation(StepMin.class);
      StepMax stepMaxAnnotation = testClass.get().getAnnotation(StepMax.class);
      if (stepMinAnnotation != null) {
        stepMin = stepMinAnnotation.value();
      }
      if (stepMaxAnnotation != null) {
        stepMax = stepMaxAnnotation.value();
      }
    }
  }

}
