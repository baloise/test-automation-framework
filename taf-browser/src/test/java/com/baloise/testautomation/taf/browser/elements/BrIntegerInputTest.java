package com.baloise.testautomation.taf.browser.elements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.baloise.testautomation.taf.base._base.TafError;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.browser.TafBrowserTest;

public class BrIntegerInputTest extends TafBrowserTest {

  @IAnnotations.ById("IntegerInput")
  private BrIntegerInput integerInput;

  @Test
  public void test() {
    assertNull(integerInput.get().asInteger());
    Integer expected = 12345;
    integerInput.setFill(new TafInteger(expected).asString());
    integerInput.fill();
    assertEquals(expected, integerInput.get().asInteger());
  }

  @Test
  public void testIllegalNumberFormat() {
    try {
      integerInput.setFill("bubu");
      integerInput.fill();
    } catch (TafError e) {
      assertEquals("illegal number format", e.getMessage());
    }
  }

}