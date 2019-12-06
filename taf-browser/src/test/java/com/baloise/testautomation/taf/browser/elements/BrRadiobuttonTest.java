package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BrRadiobuttonTest extends TafBrowserTest {

  @IAnnotations.ById("RadioA")
  private BrRadiobutton radioA;

  @IAnnotations.ById("RadioB")
  private BrRadiobutton radioB;

  @Test
  public void test() {
    assertFalse(radioA.get().asBoolean());
    assertFalse(radioB.get().asBoolean());
    radioA.setFill(new TafBoolean(true));
    radioA.fill();
    assertTrue(radioA.get().asBoolean());
    assertFalse(radioB.get().asBoolean());
    radioB.setFill(new TafBoolean(true));
    radioB.fill();
    assertFalse(radioA.get().asBoolean());
    assertTrue(radioB.get().asBoolean());
  }

}