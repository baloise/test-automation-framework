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
public interface ISwTabbedPane<R> extends ISwElement<R> {

  public enum Command {
    selectbytitle, selectbyindex
  }

  public final String type = "tabbedpane";

  public final String paramTitle = "title";
  public final String paramIndex = "index";

  public void selectByIndex(Long index);

  public void selectByTitle(String title);

}
