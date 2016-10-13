/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import org.junit.Assert;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;

/**
 * 
 */
public class SwComboBoxProxy extends ASwElementProxy implements ISwComboBox<Long> {

  @Override
  public void click() {
    executeCommand(Command.click.toString());
  }

  @Override
  public String getSelectedItem() {
    TafProperties props = executeCommand(Command.getselecteditem.toString());
    return props.getString(paramText);
  }

  @Override
  public String getType() {
    return ISwComboBox.type;
  }

  @Override
  public void selectIndex(Long index) {
    TafProperties props = new TafProperties();
    props.putObject(paramIndex, index);
    executeCommand(Command.selectindex.toString(), props);
  }

  @Override
  public void selectItem(String item) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, item);
    executeCommand(Command.selectitem.toString(), props);
  }

  @Override
  public void selectItemByFillingInput(String item) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, item);
    executeCommand(Command.selectitembyfillinginput.toString(), props);
  }

  @Override
  public void selectItemByMatchingDescription(String item) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, item);
    executeCommand(Command.selectitembymatchingdescription.toString(), props);
  }

  @Override
  public void selectIndexByMatchingDescription(String item) {
    TafProperties props = new TafProperties();
    props.putObject(paramText, item);
    executeCommand(Command.selectindexbymatchingdescription.toString(), props);
  }

}
