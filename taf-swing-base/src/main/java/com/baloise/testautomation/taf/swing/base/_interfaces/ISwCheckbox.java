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
public interface ISwCheckbox<R> extends ISwElement<R> {

  public enum Command {
    check, uncheck
  }

  public final String type = "checkbox";
  
  public void check();
  
  public void uncheck();


}
