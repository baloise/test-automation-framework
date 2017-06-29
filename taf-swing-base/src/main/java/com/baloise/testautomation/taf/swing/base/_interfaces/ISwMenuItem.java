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
public interface ISwMenuItem<R> extends ISwElement<R> {

  public enum Command {
    click, isenabled
  }

  public final String type = "menuitem";

  public final String paramIsEnabled = "isenabled";

  public void click();
  
  public boolean isEnabled();

}
