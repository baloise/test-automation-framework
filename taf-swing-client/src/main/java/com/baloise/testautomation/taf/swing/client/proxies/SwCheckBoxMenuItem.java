package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwCheckBoxMenuItem;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

public class SwCheckBoxMenuItem extends ASwElementProxy implements ISwCheckBoxMenuItem<Long> {
  
  @Override
  public String getType() {
    return ISwMenuItem.type;
  }
  
  @Override
  public void click() {
  	executeCommand(Command.click.toString());
  }
  
  @Override
  public boolean isEnabled() {
    TafProperties props = executeCommand(Command.isenabled.toString());
    return props.getBoolean(paramIsEnabled);
  }
  
  @Override
  public boolean getState() {
    TafProperties props = executeCommand(Command.getstate.toString());
    return props.getBoolean(paramState);
  }
}
