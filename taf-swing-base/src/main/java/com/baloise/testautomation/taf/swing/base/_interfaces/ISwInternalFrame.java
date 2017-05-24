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
    click, resizeto;
  }

  public final String type = "internalframe";
  
  public final String paramWidth = "width";
  public final String paramHeight = "height";

  public void click();
  
  public void resizeTo(Long width, Long height);

}
