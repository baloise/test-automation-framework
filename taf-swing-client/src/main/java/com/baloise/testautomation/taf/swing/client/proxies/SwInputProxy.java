/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;

/**
 * 
 */
public class SwInputProxy extends ASwElementProxy implements ISwInput<Long> {

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
  public String getType() {
    return ISwInput.type;
  }

}
