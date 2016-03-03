/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTabbedPane;

/**
 * 
 */
public class SwTabbedPaneProxy extends ASwElementProxy implements ISwTabbedPane<Long> {

  @Override
  public String getType() {
    return ISwTabbedPane.type;
  }

  @Override
  public void selectByIndex(Long index) {
    TafProperties props = new TafProperties();
    props.putObject(paramIndex, index);
    executeCommand(Command.selectbyindex.toString(), props);
  }

  @Override
  public void selectByTitle(String title) {
    TafProperties props = new TafProperties();
    props.putObject(paramTitle, title);
    executeCommand(Command.selectbytitle.toString(), props);
  }
}
