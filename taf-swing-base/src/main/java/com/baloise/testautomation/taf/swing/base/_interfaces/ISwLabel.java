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

  public enum Command {
    gettext, rightclick, click
  }

  public final String paramText = "text";

  public final String type = "label";

  public void click();

  public String getText();

  public void rightClick();

}
