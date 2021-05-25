package com.baloise.testautomation.taf.base.testing.steps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestMethodOrder(StepAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StepsTest {

  @Order(10)
  @RegisterExtension
  protected StepInvocationInterceptor sii = new StepInvocationInterceptor();

  private String data = "first";

  @Step(10)
  public void first() {
    Assertions.assertEquals("first", data);
    data = "second comes next";
  }

  @Step(20)
  public void second() {
    Assertions.assertEquals("second comes next", data);
  }

}
