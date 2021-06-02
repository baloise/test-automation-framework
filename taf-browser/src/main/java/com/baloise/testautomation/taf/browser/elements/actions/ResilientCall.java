package com.baloise.testautomation.taf.browser.elements.actions;

import org.awaitility.Awaitility;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ResilientCall<T> {

  private final long timeoutInMsecs;

  private T callResult;

  public ResilientCall(long timeoutInMsecs) {
    this.timeoutInMsecs = timeoutInMsecs;
  }

  public T invoke(Callable<T> callable) {
    Awaitility.await().atMost(timeoutInMsecs, TimeUnit.MILLISECONDS).pollDelay(0L, TimeUnit.MILLISECONDS)
        .pollInterval(10L, TimeUnit.MILLISECONDS).until(() -> safeCall(callable));
    return callResult;
  }

  private boolean safeCall(Callable<T> callable) throws Exception {
    try {
      callResult = callable.call();
      return true;
    }
    catch (StaleElementReferenceException e) {
      return false;
    }
    catch (ElementNotInteractableException e2) {
      return false;
    }
  }

}
