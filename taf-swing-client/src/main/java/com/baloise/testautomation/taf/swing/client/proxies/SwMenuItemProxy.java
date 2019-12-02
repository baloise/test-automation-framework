package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwMenuItem;

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

  public String[] getSubElements() {
    TafProperties props = executeCommand(Command.getsubelements.toString());
    String elementsAsString = props.getString(paramGetSubElements);
    String[] elements = getSplitElements(elementsAsString);
    return elements;
  }
  
  protected String[] getSplitElements(String elementsAsString) {
    return elementsAsString.split("\\" + separator, -1);
  }
}
