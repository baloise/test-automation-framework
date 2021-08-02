package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._base.TafError;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Assert;
import org.junit.Test;

public class BrButtonTest extends TafBrowserTest {

  @IAnnotations.ById("Button")
  private BrButton button;
  @IAnnotations.ById("ButtonWrong")
  private BrButton buttonWrong;
  @Test
  public void test() {
    button.click();
    assertBrowserLogContainsMessage("Button clicked");
  }
  @Test
  public void testErrorWithByAnnotationInfo() {
    try {
      buttonWrong.click();
    }catch (TafError te){
      Assert.assertEquals("buttonWrong -> error when clicking is caused byAnnotation:ById -> ButtonWrong",te.getMessage());
    }

  }

}
