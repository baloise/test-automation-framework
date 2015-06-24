/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._base.AElement;

/**
 * 
 */
public class BrElement extends AElement {

  @Override
  public void click() {
    find().click();
  }

//  @Override
  public WebElement find() {
    WebElement div = (WebElement)brFind();
    assertTrue("BrElement is expected to be a DIV but was: " + div.getTagName(),
        "div".equalsIgnoreCase(div.getTagName()));
    return div;
  }

}
