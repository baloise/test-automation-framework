package com.baloise.testautomation.taf.browser.fill;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations;
import com.baloise.testautomation.taf.browser.TafBrowserTest;
import com.baloise.testautomation.taf.browser.elements.BrStringInput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IAnnotations.DataProvider(IAnnotations.DataProviderType.EXCEL)
public class FillTest extends TafBrowserTest {

  @IAnnotations.Fill
  @IAnnotations.ById("StringInput")
  private BrStringInput input;

  @Test
  void test() {
    setFill("testId");
    fill();
    assertEquals("Fill Text", input.get().asString());
  }

}
