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
public interface ISwButton<R> extends ISwElement<R> {

  public enum Command {
    click, isEnabled
  }

  public static String type = "button";

  public static String paramIsEnabled = "isenabled";
  
  public void click();
  
  public boolean isEnabled();

}
