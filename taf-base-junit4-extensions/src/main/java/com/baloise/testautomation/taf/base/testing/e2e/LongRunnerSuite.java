package com.baloise.testautomation.taf.base.testing.e2e;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;

import org.junit.Assume;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import com.baloise.testautomation.taf.base.testing.e2e.LongRunner.LongRunnerInfo;

import static org.junit.Assert.*;

public class LongRunnerSuite extends Suite {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface LongRunnerInit {
  }

  private static Vector<LongRunnerInfo> longRunnerInfo = new Vector<>();
  private static int currentIndex = 0;

  public static void addLongRunnerInfosFor(Class<?>... testClasses) {
    for (Class<?> testClass : testClasses) {
      assertNotNull(testClass);
      longRunnerInfo.addAll(LongRunner.getFor(testClass));
    }
    Collections.sort(longRunnerInfo);
  }

  public static LongRunnerInfo getCurrentLongRunnerInfo() {
    return longRunnerInfo.get(currentIndex);
  }

  private static Class<?>[] getLongRunnerClasses(Class<?> testClass) {
    Method[] methods = testClass.getMethods();
    for (Method method : methods) {
      if (method.isAnnotationPresent(LongRunnerInit.class)) {
        try {
          method.invoke(testClass);
        }
        catch (Exception e) {
          fail("Mit 'LongRunnerInit' annotierte Methode konnte nicht ausgeführt werden. Muss 'static' sein.");
        }
      }
    }

    Class<?>[] classes = new Class[longRunnerInfo.size()];
    for (int i = 0; i < longRunnerInfo.size(); i++) {
      classes[i] = longRunnerInfo.get(i).testClass;
    }
    return classes;
  }

  public LongRunnerSuite(final Class<?> testClass) throws InitializationError {
    super(testClass, getLongRunnerClasses(testClass));
  }

  @Override
  protected Description describeChild(Runner child) {
    assertTrue("Runner must be an instance of (sub)class(es) 'LongRunner'", child instanceof LongRunner);
    ((LongRunner)child).setId(longRunnerInfo.get(currentIndex));
    currentIndex++;
    return child.getDescription();
  }

  @Override
  public Description getDescription() {
    currentIndex = 0;
    Description result = super.getDescription();
    currentIndex = 0;
    return result;
  }

  @Override
  public void run(final RunNotifier notifier) {
    currentIndex = 0;
    Assume.assumeTrue("Keine Tests auszuführen", getChildren().size() > 0);
    super.run(notifier);
  }

  @Override
  protected void runChild(Runner runner, final RunNotifier notifier) {
    assertTrue("Runner must be an instance of (sub)class(es) 'LongRunner'", runner instanceof LongRunner);
    ((LongRunner)runner).initWith(longRunnerInfo.get(currentIndex));
    currentIndex++;
    super.runChild(runner, notifier);
  }

}
