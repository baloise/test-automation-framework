package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrStringInputTest extends TafBrowserTest {

  @IAnnotations.ById("StringInput")
  private BrStringInput stringInput;

  @Test
  public void test() {
    assertEquals("", stringInput.get().asString());
    String expected = "Text Input";
    stringInput.setFill(expected);
    stringInput.fill();
    assertEquals(expected, stringInput.get().asString());
  }

}