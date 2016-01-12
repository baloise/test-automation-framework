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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;
import com.baloise.testautomation.taf.swing.base.db.SwError;

/**
 * 
 */
public class SwTabbedPane extends AElement {

  @Override
  public void click() {
    throw new SwError("click is not supported on tabbed pane");
  }

  public void clickTab(String title) {
    find().selectByTitle(title);
  }

  public void clickTab(Long index) {
    find().selectByIndex(index);
  }

  public ISwTabbedPane<?> find() {
    return (ISwTabbedPane<?>)swFind(ISwTabbedPane.type);
  }

}
