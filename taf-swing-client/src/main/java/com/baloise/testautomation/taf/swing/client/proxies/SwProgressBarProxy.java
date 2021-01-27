package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwProgressBar;

public class SwProgressBarProxy extends ASwElementProxy implements ISwProgressBar<Long> {

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

  @Override
  public void rightClick() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public String getText() {
    TafProperties props = executeCommand(Command.gettext.toString());
    return props.getString(paramText);
  }

  @Override
  public String getType() {
    return ISwProgressBar.type;
  }

  @Override
  public int getValue() {
    TafProperties props = executeCommand(Command.getvalue.toString());
    return Integer.parseInt(props.getString(paramValue));
  }

  @Override
  public int getMaximum() {
    TafProperties props = executeCommand(Command.getmaximum.toString());
    return Integer.parseInt(props.getString(paramMaximum));
  }

  @Override
  public int getMinimum() {
    TafProperties props = executeCommand(Command.getminimum.toString());
    return Integer.parseInt(props.getString(paramMinimum));
  }

  @Override
  public boolean isIndeterminate() {
    TafProperties props = executeCommand(Command.isindeterminate.toString());
    return Boolean.parseBoolean(props.getString(paramIsIndeterminate));
  }

}
