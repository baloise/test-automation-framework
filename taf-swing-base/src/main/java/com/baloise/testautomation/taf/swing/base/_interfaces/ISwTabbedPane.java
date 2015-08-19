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
import com.baloise.testautomation.taf.common.utils.TafProperties;

/**
 * 
 */
public interface ISwTabbedPane<R> extends ISwElement<R> {

  public enum Command {
    selectbytitle
  }

  public final String type = "tabbedpane";

  public final String paramTitle = "title";
  
  public void selectByTitle(String title);
  
}
