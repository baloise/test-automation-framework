package com.baloise.testautomation.taf.base.testing.steps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
public @interface Step {

  int DEFAULT = Integer.MAX_VALUE / 2;

  int value();

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Test
  public @interface StepMin {

    int value() default 0;

  }

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Test
  public @interface StepMax {

    int value() default Integer.MAX_VALUE / 2;

  }

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Test
  public @interface SkipAfterFailed {

    boolean value() default true;

  }

}