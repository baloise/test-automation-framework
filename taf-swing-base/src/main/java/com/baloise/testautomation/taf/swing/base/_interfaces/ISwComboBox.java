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
    click,
    selectitem,
    selectindex,
    getselecteditem,
    selectitembyfillinginput,
    selectitembymatchingdescription,
    selectindexbymatchingdescription
  }

  public static String paramText = "text";
  
  public static String paramIndex = "index";

  public final String type = "combobox";

  public void click();

  public String getSelectedItem();

  public void selectIndex(Long index);

  public void selectItem(String item);

  public void selectItemByFillingInput(String item);

  public void selectItemByMatchingDescription(String item);

  public void selectIndexByMatchingDescription(String item);

}
