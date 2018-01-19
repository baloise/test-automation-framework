/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import org.assertj.swing.fixture.JComboBoxFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwComboBox;
import com.baloise.testautomation.taf.swing.server.utils.LabelFinder;

/**
 * 
 */
public class SwComboBox extends ASwElement implements ISwComboBox<Component> {

  public SwComboBox(long tid, JComboBox component) {
    super(tid, component);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    Command c = getCommand(Command.class, props.getString(paramCommand));
    switch (c) {
      case click:
        props.clear();
        click();
        break;
      case selectitem:
        selectItem(props.getString(paramText));
        props.clear();
        break;
      case selectindex:
        selectIndex(props.getLong(paramText));
        props.clear();
        break;
      case selectitembyfillinginput:
        selectItemByFillingInput(props.getString(paramText));
        props.clear();
        break;
      case selectitembymatchingdescription:
        selectItemByMatchingDescription(props.getString(paramText));
        props.clear();
        break;
      case selectindexbymatchingdescription:
        selectIndexByMatchingDescription(props.getString(paramText));
        props.clear();
        break;
      case getselecteditem:
        props.clear();
        props.putObject(paramText, getSelectedItem());
        break;
      case getallitems:
        props.clear();
        List<String> allItems = getAllItems();
        String items = serializeItems(allItems);
        props.putObject(paramItems, items);
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
  }

  protected String serializeItems(List<String> allItems) {
    String items = "";
    int i;
    for (i = 0; i < allItems.size(); i++) {
      String item = allItems.get(i);
      items = items + item;
      if (i < allItems.size() - 1) {
        items = items + separator;
      }
    }
    return items;
  }

  @Override
  public void click() {
    getFixture().click();
  }

  @Override
  public void fillProperties() {
    LabelFinder lf = new LabelFinder(getComponent());
    addProperty("leftlabel", lf.findLeftLabelProperty());
    addProperty("toplabel", lf.findTopLabelProperty());
    addProperty("rightlabel", lf.findRightLabelProperty());
  }

  @Override
  public JComboBox getComponent() {
    return (JComboBox)component;
  }

  @Override
  public JComboBoxFixture getFixture() {
    return new JComboBoxFixture(getRobot(), getComponent());
  }

  @Override
  public String getSelectedItem() {
    return getFixture().selectedItem();
  }

  @Override
  public List<String> getAllItems() {
    return Arrays.asList(getFixture().contents());
  }
  
  @Override
  public String getType() {
    return ISwComboBox.type;
  }

  @Override
  public void selectIndex(Long index) {
    getFixture().selectItem(index.intValue());
  }

  @Override
  public void selectItem(String item) {
    try {
      selectItemByFillingInput(item);
    }
    catch (Exception e) {
      selectItemByMatchingDescription(item);
    }
  }

  @Override
  public void selectItemByFillingInput(String item) {
    getFixture().enterText(item);
  }
  
  @Override
  public void selectItemByMatchingDescription(String item) {
    getFixture().selectItem(item);
  }

  @Override
  public void selectIndexByMatchingDescription(String item) {
    long index = Arrays.asList(getFixture().contents()).indexOf(item);
    selectIndex(index);
  }

}
