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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBox;

/**
 * 
 */
public class SwCheckBoxProxy extends ASwElementProxy implements ISwCheckBox<Long> {

  @Override
  public void check() {
    executeCommand(Command.check.toString());
  }

  @Override
  public String getType() {
    return ISwCheckBox.type;
  }

  @Override
  public boolean isChecked() {
    TafProperties props = executeCommand(Command.ischecked.toString());
    return props.getBoolean(paramState);
  }

  @Override
  public void uncheck() {
    executeCommand(Command.uncheck.toString());
  }

  @Override
  public boolean isEnabled() {
    TafProperties props = executeCommand(Command.isenabled.toString());
    return props.getBoolean(paramIsEnabled);
  }


}
