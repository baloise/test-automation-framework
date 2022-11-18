package com.baloise.testautomation.taf.browser.elements.actions;

import org.awaitility.Awaitility;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.concurrent.TimeUnit;

public class ResilientRun {

  private final long timeoutInMsecs;

  public ResilientRun(long timeoutInMsecs) {
    this.timeoutInMsecs = timeoutInMsecs;
  }

  public void invoke(Runnable runnable) {
    Awaitility.await().atMost(timeoutInMsecs, TimeUnit.MILLISECONDS).pollDelay(0L, TimeUnit.MILLISECONDS)
        .pollInterval(10L, TimeUnit.MILLISECONDS).until(() -> safeRun(runnable));
  }

  private boolean safeRun(Runnable runnable) {
    try {
      runnable.run();
      return true;
    }
    catch (StaleElementReferenceException e1) {
      return false;
    }
    catch (ElementNotInteractableException e2) {
      return false;
    }
    catch (InvalidElementStateException e3) {
      return false;
    }

  }

}
