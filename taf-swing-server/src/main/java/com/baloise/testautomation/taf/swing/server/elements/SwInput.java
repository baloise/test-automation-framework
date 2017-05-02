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

import javax.swing.JTextField;

import org.assertj.swing.fixture.JTextComponentFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
import com.baloise.testautomation.taf.swing.server.utils.LabelFinder;

/**
 * 
 */
public class SwInput extends ASwElement implements ISwInput<Component> {

  public SwInput(long tid, JTextField c) {
    super(tid, c);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    System.out.println("SwInput --> basicExecCommand");
    Command c = getCommand(Command.class, props.getString(paramCommand));
    System.out.println(c);
    switch (c) {
      case click:
        props.clear();
        click();
        break;
      case clear:
        props.clear();
        clear();
        break;
      case entertext:
        enterText(props.getString(paramText));
        props.clear();
        break;
      case gettext:
        props.clear();
        props.putObject(paramText, getText());
        break;
      case isenabled:
        props.clear();
        props.putObject(paramIsEnabled, isEnabled());
        break;
      default:
        throw new IllegalArgumentException("command valid but not implemented yet: " + c);
    }
    return props;
  }

  public void clear() {
    getFixture().deleteText();
  }

  public void click() {
    getFixture().click();
  }

  public void enterText(String text) {
    getFixture().enterText(text);
  }

  @Override
  public void fillProperties() {
    LabelFinder lf = new LabelFinder(getComponent());
    addProperty("leftlabel", lf.findLeftLabelProperty());
    addProperty("toplabel", lf.findTopLabelProperty());
    addProperty("rightlabel", lf.findRightLabelProperty());
  }

  @Override
  public JTextField getComponent() {
    return (JTextField)component;
  }

  @Override
  public JTextComponentFixture getFixture() {
    return new JTextComponentFixture(getRobot(), getComponent());
  }

  public String getText() {
    return getComponent().getText();
  }

  @Override
  public String getType() {
    return ISwInput.type;
  }

}
