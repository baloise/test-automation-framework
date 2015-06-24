/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._base;

import com.baloise.testautomation.taf.base._interfaces.ISwingFinder;

/**
 * 
 */
public class SwingElement {

  private Long tid;
  private ISwingFinder finder;

  public SwingElement(ISwingFinder finder, Long tid) {
    this.tid = tid;
    this.finder = finder;
  }

  public void clear() {
    finder.clear(tid);
  }

  public void click() {
    finder.click(tid);
  }

  public String getText() {
    return finder.getText(tid);
  }

  public void setText(String text) {
    finder.setText(tid, text);
  }

}
