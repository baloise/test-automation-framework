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
        selectIndex(Integer.parseInt(props.getString(paramText)));
        props.clear();
        break;
      case getselecteditem:
        props.clear();
        props.putObject(paramText, getSelectedItem());
        break;
      default:
        throw new NotSupportedException("command not implemented: " + c);
    }
    return props;
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
  public String getType() {
    return ISwComboBox.type;
  }

  @Override
  public void selectIndex(int index) {
    getFixture().selectItem(index);
  }

  @Override
  public void selectItem(String item) {
    try {
      getFixture().enterText(item);
    }
    catch (Exception e) {
      getFixture().selectItem(item);
    }
  }

}
