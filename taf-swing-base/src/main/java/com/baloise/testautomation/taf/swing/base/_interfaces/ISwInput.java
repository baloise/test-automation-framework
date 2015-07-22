/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.base._interfaces;

import com.baloise.testautomation.taf.common.interfaces.ISwElement;

/**
 * 
 */
public interface ISwInput<R> extends ISwElement<R> {

  public final String paramText = "text";
  
  public enum Command {
    click, clear, entertext, gettext
  }

  public final String type = "input";
  
  public void click();

  public void clear();

  public void enterText(String text);

  public String getText();

}
