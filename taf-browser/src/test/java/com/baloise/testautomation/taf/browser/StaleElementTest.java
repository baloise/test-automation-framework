package com.baloise.testautomation.taf.browser;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.elements.BrButton;
import org.awaitility.Awaitility;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class StaleElementTest extends TafBrowserTest {

  @IAnnotations.ById("ReplaceButton")
  private BrButton replaceButton;

  @IAnnotations.ById("ButtonBeingReplaced")
  private MockBrButton buttonBeingReplaced;

  private WebElement originalButtonElement;

  @Before
  public void setUp() {
    originalButtonElement = buttonBeingReplaced.find();
  }

  @Test
  public void safeClickTest() {
    buttonBeingReplaced.safeClick(this);
    assertEquals(
        "Expecting the first invocation to fail, the safe invocation to retry, then the second invocation to be successful",
        2,
        buttonBeingReplaced.getFindInvocationCount());
  }

  @Test(expected = StaleElementReferenceException.class)
  public void unsafeClickTest() {
    buttonBeingReplaced.unsafeClick(this);
  }

  /**
   * Replace the button in the DOM, making previously acquired WebElement references stale.
   */
  void makeButtonWebElementStale() {
    replaceButton.click();
    Awaitility.await()
              .atMost(10L, TimeUnit.SECONDS)
              .until(() -> !buttonBeingReplaced.find().equals(originalButtonElement));
  }

}
