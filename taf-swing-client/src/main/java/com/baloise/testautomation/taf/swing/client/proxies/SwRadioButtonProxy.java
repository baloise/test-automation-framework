package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwRadioButton;

public class SwRadioButtonProxy extends ASwElementProxy implements ISwRadioButton<Long> {

  @Override
  public void check() {
    executeCommand(Command.check.toString());
  }

  @Override
  public String getType() {
    return ISwRadioButton.type;
  }

  @Override
  public boolean isChecked() {
    TafProperties props = executeCommand(Command.ischecked.toString());
    return props.getBoolean(paramState);
  }

  @Override
  public boolean isEnabled() {
    TafProperties props = executeCommand(Command.isenabled.toString());
    return props.getBoolean(paramIsEnabled);
  }

  @Override
  public void uncheck() {
    executeCommand(Command.uncheck.toString());
  }

}
