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
public interface ISwList<R> extends ISwElement<R> {

  public enum Command {
    gettextat
  }

  public final String type = "list";

  public final String paramIndex = "index";

  public final String paramText = "text";

  public String getTextAt(Long index);

}
