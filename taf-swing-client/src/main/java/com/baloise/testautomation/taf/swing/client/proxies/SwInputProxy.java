package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;

public class SwInputProxy extends ASwTextComponentProxy implements ISwInput<Long> {

  @Override
  public String getType() {
    return ISwInput.type;
  }

}
