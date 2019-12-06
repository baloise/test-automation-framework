package com.baloise.testautomation.taf.browser;

import com.baloise.testautomation.taf.browser.elements.BrButton;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class MockBrButton extends BrButton {

  private int findInvocationCount = 0;

  int getFindInvocationCount() {
    return findInvocationCount;
  }

  /**
   * Performs a click operation on the element, without wrapping it in a safeInvoke call.
   * <p>
   * This method reproduces a race condition where the WebElement becomes stale between the find() and click() calls. It
   * should always throw a StaleElementReferenceException.
   *
   * @param testInstance The instance of the running test. Required to make the WebElement stale at the appropriate time
   *                     to reproduce the race condition.
   * @throws StaleElementReferenceException
   */
  public void unsafeClick(StaleElementTest testInstance) {
    find(testInstance).click();
  }

  /**
   * Performs a click operation on the element, wrapping it in a safeInvoke call.
   * <p>
   * This method reproduces a race condition where the element becomes stale between the the find() and click() calls.
   * The safeInvoke() wrapping the operation should retry and successfully perform the click the second attempt.
   *
   * @param testInstance The instance of the running test. Required to make the WebElement stale at the appropriate time
   *                     to reproduce the race condition.
   */
  public void safeClick(StaleElementTest testInstance) {
    getFinder().safeInvoke(() -> find(testInstance).click());
  }

  /**
   * Performs a find() operation, the first time returing stale WebElement, the subsequent times returning a non-stale
   * WebElement.
   *
   * @param testInstance The instance of the running test. Required to make the WebElement stale at the appropriate time
   *                     to reproduce the race condition.
   * @return A stale WebElement for the first invocation, a non-stale WebElement in subsequent invocations.
   */
  public WebElement find(StaleElementTest testInstance) {
    findInvocationCount++;
    WebElement webElement = (WebElement) brFind();
    if (findInvocationCount == 1) {
      testInstance.makeButtonWebElementStale();
    }
    return webElement;
  }

}
