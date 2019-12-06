package com.baloise.testautomation.taf.browser.elements;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrLabelTest extends TafBrowserTest {

  @IAnnotations.ById("Label")
  private BrLabel label;

  @Test
  public void test() {
    assertEquals("Label Text", label.get().asString());
  }

}