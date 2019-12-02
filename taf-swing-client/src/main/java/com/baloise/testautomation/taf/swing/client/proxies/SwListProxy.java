package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwList;

public class SwListProxy extends ASwElementProxy implements ISwList<Long> {

  @Override
  public Long getSize() {
    TafProperties props = executeCommand(Command.getsize.toString());
    return props.getLong(paramSize);
  }

  @Override
  public String getTextAt(Long index) {
    TafProperties props = new TafProperties();
    props.putObject(paramIndex, index);
    props = executeCommand(Command.gettextat.toString(), props);
    return props.getString(paramText);
  }

  @Override
  public String getType() {
    return ISwList.type;
  }

  @Override
  public void clickItem(Long index) {
    TafProperties props = new TafProperties();
    props.putObject(paramIndex, index);
    props = executeCommand(Command.clickitem.toString(), props);
  }

  @Override
  public void clickItem(String value) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, value);
    props = executeCommand(Command.clickitembytext.toString(), props);
  }

}
