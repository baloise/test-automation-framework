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
public interface ISwComboBox<R> extends ISwElement<R> {

  public enum Command {
    click, selectitem, selectindex, getselecteditem
  }

  public static String paramText = "text";

  public final String type = "combobox";

  public void click();

  public String getSelectedItem();

  public void selectIndex(int index);

  public void selectItem(String item);

}
