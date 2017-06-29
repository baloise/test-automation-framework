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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

/**
 * 
 */
public class SwMenuItemProxy extends ASwElementProxy implements ISwMenuItem<Long> {

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

  @Override
  public String getType() {
    return ISwMenuItem.type;
  }

  @Override
  public boolean isEnabled() {
    TafProperties props = executeCommand(Command.isenabled.toString());
    return props.getBoolean(paramIsEnabled);
  }

}
