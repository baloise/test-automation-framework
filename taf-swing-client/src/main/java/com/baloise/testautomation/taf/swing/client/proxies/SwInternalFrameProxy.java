package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwInternalFrame;

public class SwInternalFrameProxy extends ASwElementProxy implements ISwInternalFrame<Long> {

  @Override
  public String getType() {
    return ISwInternalFrame.type;
  }
  
  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

}
