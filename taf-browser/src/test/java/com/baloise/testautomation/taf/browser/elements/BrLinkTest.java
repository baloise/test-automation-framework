package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.jupiter.api.Test;

public class BrLinkTest extends TafBrowserTest {

  @IAnnotations.ById("Link")
  private BrLink link;

  @Test
  public void test() {
    link.click();
    assertBrowserLogContainsMessage("Link HREF invoked");
  }

}