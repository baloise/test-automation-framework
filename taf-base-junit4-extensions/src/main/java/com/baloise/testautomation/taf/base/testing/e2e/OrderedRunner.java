package com.baloise.testautomation.taf.base.testing.e2e;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.List;

import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Sorter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.baloise.testautomation.taf.base._base.AnnotationHelper;

public class OrderedRunner extends BlockJUnit4ClassRunner {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface IgnoreAfterFailed {
  }

  private class IgnoreAfterStoppedRule extends TestWatcher {

    @Override
    protected void failed(Throwable e, Description description) {
      stopped = true;
    }

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface Order {
    public int value();
  }

  protected boolean stopped = false;

  public OrderedRunner(Class<?> klass) throws InitializationError {
    super(klass);
    stopped = false;
  }

  @Override
  public Description getDescription() {
    sort();
    return super.getDescription();
  }

  @Override
  protected List<TestRule> getTestRules(Object target) {
    List<TestRule> rules = super.getTestRules(target);
    if (isAnnotatedWithIgnoreAfterFail()) {
      rules.add(new IgnoreAfterStoppedRule());
    }
    return rules;
  }

  private boolean isAnnotatedWithIgnoreAfterFail() {
    return AnnotationHelper.hasAnnotationInHierarchy(getTestClass().getJavaClass(), IgnoreAfterFailed.class);
    // Annotation[] annotations = getRunnerAnnotations();
    // boolean isAnnotedWithIgnoreAfterFail = false;
    // for (Annotation annotation : annotations) {
    // if (annotation instanceof IgnoreAfterFailed) {
    // isAnnotedWithIgnoreAfterFail = true;
    // break;
    // }
    // }
    // return isAnnotedWithIgnoreAfterFail;
  }

  @Override
  protected boolean isIgnored(FrameworkMethod child) {
    if (stopped) {
      return true;
    }
    return super.isIgnored(child);
  }

  public void sort() {
    Comparator<Description> comparator = new Comparator<Description>() {

      @Override
      public int compare(Description d1, Description d2) {
        Order o1 = d1.getAnnotation(Order.class);
        Order o2 = d2.getAnnotation(Order.class);

        if (o1 == null || o2 == null) return -1;

        return o1.value() - o2.value();
      }
    };
    sort(new Sorter(comparator));
  }
}
