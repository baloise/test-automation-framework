package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwDialog;

public class SwDialogProxy extends ASwElementProxy implements ISwDialog<Long> {

  @Override
  public String getType() {
    return ISwDialog.type;
  }
  
  @Override
  public String getTitle() {
    TafProperties props = executeCommand(Command.gettitle.toString());
    return props.getString(paramTitle);
  }
  
  @Override
  public void resizeTo(Long width, Long height) {
    TafProperties props = new TafProperties();
    props.putObject(paramWidth, width);
    props.putObject(paramHeight, height);
    executeCommand(Command.resizeto.toString(), props);
  }

}
