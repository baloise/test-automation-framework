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
public class BrLink extends AElement {

  @Override
  public void click() {
    find().click();
  }

//  @Override
  public WebElement find() {
    WebElement link = (WebElement)brFind();
    assertTrue("", "a".equalsIgnoreCase(link.getTagName()));
    return link;
  }

}
