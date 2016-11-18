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
import com.baloise.testautomation.taf.swing.base._interfaces.ISwLabel;

/**
 * 
 */
public class SwLabelProxy extends ASwElementProxy implements ISwLabel<Long> {

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

  @Override
  public String getText() {
    TafProperties props = executeCommand(Command.gettext.toString());
    return props.getString(paramText);
  }

  @Override
  public String getType() {
    return ISwLabel.type;
  }

  @Override
  public void rightClick() {
    executeCommand(Command.rightclick.toString());
  }

  @Override
  public int getX() {
    TafProperties props = executeCommand(Command.getx.toString());
    return props.getLong(paramText).intValue();
  }

  @Override
  public int getY() {
    TafProperties props = executeCommand(Command.gety.toString());
    return props.getLong(paramText).intValue();
  }

}
