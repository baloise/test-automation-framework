/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.elements;

import com.baloise.testautomation.taf.base._base.AElement;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTable;

/**
 * 
 */
public class SwTable extends AElement {

  @Override
  public void click() {}

  public ISwTable<?> find() {
    return (ISwTable<?>)swFind(ISwTable.type);
  }

  public void rightClickCell(int row, int col) {
    find().rightClickCell(row, col);
  }

  public void rightClickCell(String text) {
    find().rightClickCell(text);
  }

}
