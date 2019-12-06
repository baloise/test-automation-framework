package com.baloise.testautomation.taf.browser;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.elements.BrButton;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StaleElementTest extends TafBrowserTest {

  @IAnnotations.ById("ReplaceButton")
  private BrButton replaceButton;

  @IAnnotations.ById("ButtonBeingReplaced")
  private MockBrButton buttonBeingReplaced;

  private WebElement originalButtonElement;

  @BeforeEach
  public void setUp() {
    originalButtonElement = buttonBeingReplaced.find();
  }

  @Test
  public void safeClickTest() {
    buttonBeingReplaced.safeClick(this);
    assertEquals(2,
                 buttonBeingReplaced.getFindInvocationCount(),
                 "Expecting the first invocation to fail, the safe invocation to retry, then the second invocation " +
                 "to be successful");
  }

  @Test
  public void unsafeClickTest() {
    assertThrows(StaleElementReferenceException.class, () -> buttonBeingReplaced.unsafeClick(this));
  }

  /**
   * Replace the button in the DOM, making previously acquired WebElement references stale.
   */
  void makeButtonWebElementStale() {
    replaceButton.click();
    Awaitility.await()
              .atMost(10L, TimeUnit.SECONDS)
              .until(() -> !buttonBeingReplaced.find()
                                               .equals(originalButtonElement));
  }

}
