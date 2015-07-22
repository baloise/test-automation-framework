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
public interface ISwLabel<R> extends ISwElement<R> {

  public final String paramText = "text";
  
  public enum Command {
    gettext
  }

  public final String type = "label";
  
  public String getText();

}
