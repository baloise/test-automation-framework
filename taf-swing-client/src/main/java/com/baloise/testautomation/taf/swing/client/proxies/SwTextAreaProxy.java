package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextArea;

public class SwTextAreaProxy extends ASwTextComponentProxy implements ISwTextArea<Long> {

  @Override
  public String getType() {
    return ISwTextArea.type;
  }

}
