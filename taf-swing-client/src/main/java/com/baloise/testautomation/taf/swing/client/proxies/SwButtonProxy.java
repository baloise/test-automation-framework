/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwButton;

/**
 * 
 */
public class SwButtonProxy extends ASwElementProxy implements ISwButton<Long> {

  @Override
  public String getType() {
    return ISwButton.type;
  }

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

}
