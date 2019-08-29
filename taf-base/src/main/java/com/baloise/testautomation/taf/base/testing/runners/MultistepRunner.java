package com.baloise.testautomation.taf.base.testing.runners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class MultistepRunner extends BlockJUnit4ClassRunner {
  protected Object testObject = null;
  protected boolean failed = false;

  public MultistepRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  /*
   * Separate annotation as execution logic is completely different,
   * essentially entire class is always one Test.
   * Test annotation should still be used in addition for timeouts and expected exception handling.
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface Step {
    public int value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface StepMin {
    public int value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface StepMax {
    public int value();
  }

  public class StepWrapper extends Statement {
    private final Statement next;

    public StepWrapper(Statement next) {
      this.next = next;
    }

    @Override
    public void evaluate() throws Throwable {
      try {
        next.evaluate();
      }
      catch (AssumptionViolatedException e) {
        throw e;
      }
      catch (Throwable e) {
        failed = true;
        throw e;
      }
    }
  }

  protected List<FrameworkMethod> computeTestMethods() {
    List<FrameworkMethod> steps = new ArrayList<FrameworkMethod>();

    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;

    StepMin stepMin = getTestClass().getAnnotation(StepMin.class);
    if (stepMin != null) {
      min = stepMin.value();
    }
    StepMax stepMax = getTestClass().getAnnotation(StepMax.class);
    if (stepMax != null) {
      max = stepMax.value();
    }
    for (FrameworkMethod m : getTestClass().getAnnotatedMethods(Step.class)) {
      int step = m.getAnnotation(Step.class).value();
      if (step >= min && step <= max) {
        steps.add(m);
      }
    }

    Collections.sort(steps, new Comparator<FrameworkMethod>() {

      @Override
      public int compare(FrameworkMethod s1, FrameworkMethod s2) {
        return s1.getAnnotation(Step.class).value() - s2.getAnnotation(Step.class).value();
      }
    });

    return Collections.unmodifiableList(steps);
  }

  protected Object createTest() throws Exception {
    if (testObject == null) {
      testObject = getTestClass().getOnlyConstructor().newInstance();
    }
    return testObject;
  }

  @Override
  protected Statement methodBlock(FrameworkMethod method) {
    return new StepWrapper(super.methodBlock(method));
  }

  @Override
  protected boolean isIgnored(FrameworkMethod child) {
    return failed || super.isIgnored(child);
  }
}
