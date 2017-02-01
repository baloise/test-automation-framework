/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.browser.elements;

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

  public String getText() {
    return find().getText();
  }
  
  public WebElement find() {
    WebElement div = (WebElement)brFind();
    return div;
  }

}
