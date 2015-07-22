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
public interface ISwTable<R> extends ISwElement<R> {

  public final String paramText = "text";
  public final String paramRow = "row";
  public final String paramCol = "col";
      
  public enum Command {
    clickcell
  }

  public final String type = "table";
  
  public void clickCell(int row, int col);
  
  public void clickCell(String text);

}
