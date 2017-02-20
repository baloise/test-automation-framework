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
public interface ISwInternalFrame<R> extends ISwElement<R> {

  public enum Command {
    click;
  }

  public final String type = "internalframe";

  public void click();

}
