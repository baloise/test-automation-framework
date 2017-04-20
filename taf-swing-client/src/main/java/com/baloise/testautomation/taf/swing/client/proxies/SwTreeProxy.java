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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTree;

/**
 * 
 */
public class SwTreeProxy extends ASwElementProxy implements ISwTree<Long> {

  @Override
  public void clickPath(String path) {
    TafProperties props = new TafProperties();
    props.putObject(paramPath, path);
    executeCommand(Command.clickpath.toString(), props);
  }

  @Override
  public void doubleClickEachElement(String path) {
    TafProperties props = new TafProperties();
    props.putObject(paramPath, path);
    executeCommand(Command.doubleclickeachelement.toString(), props);
  }

  @Override
  public void doubleClickPath(String path) {
    TafProperties props = new TafProperties();
    props.putObject(paramPath, path);
    executeCommand(Command.doubleclickpath.toString(), props);
  }

  @Override
  public String getType() {
    return ISwTree.type;
  }

  @Override
  public void rightClickPath(String path) {
    TafProperties props = new TafProperties();
    props.putObject(paramPath, path);
    executeCommand(Command.rightclickpath.toString(), props);
  }

}
