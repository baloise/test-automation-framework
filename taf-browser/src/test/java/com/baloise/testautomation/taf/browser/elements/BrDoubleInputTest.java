package com.baloise.testautomation.taf.browser.elements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.baloise.testautomation.taf.base._base.TafError;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.browser.TafBrowserTest;

public class BrDoubleInputTest extends TafBrowserTest {

  @IAnnotations.ById("DoubleInput")
  private BrDoubleInput doubleInput;

  @Test
  public void test() {
    assertNull(doubleInput.get().asDouble());
    Double expected = 0.123D;
    doubleInput.setFill(new TafDouble(expected).asString());
    doubleInput.fill();
    assertEquals(expected, doubleInput.get().asDouble());
  }

  @Test
  public void testIllegalNumberFormat() {
    try {
      doubleInput.setFill("bubu");
      doubleInput.fill();
    } catch (TafError e) {
      assertEquals("illegal number format", e.getMessage());
    }
  }

}