package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextComponent;

public abstract class ASwTextComponentProxy extends ASwElementProxy implements ISwTextComponent<Long> {

  @Override
  public void clear() {
    executeCommand(Command.clear.toString());
  }

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

  @Override
  public void enterText(String text) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, text);
    executeCommand(Command.entertext.toString(), props);
  }

  @Override
  public String getText() {
    TafProperties props = executeCommand(Command.gettext.toString());
    return props.getString(paramText);
  }

  @Override
  public boolean isEnabled() {
    TafProperties props = executeCommand(Command.isenabled.toString());
    return props.getBoolean(paramIsEnabled);
  }

}
