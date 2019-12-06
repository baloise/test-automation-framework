package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Test;

public class BrButtonTest extends TafBrowserTest {

  @IAnnotations.ById("Button")
  private BrButton button;

  @Test
  public void test() {
    button.click();
    assertBrowserLogContainsMessage("Button clicked");
  }

}