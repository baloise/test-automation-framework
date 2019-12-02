package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.text.JTextComponent;

import org.assertj.swing.fixture.JTextComponentFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextComponent;
import com.baloise.testautomation.taf.swing.server.utils.LabelFinder;
import com.baloise.testautomation.taf.swing.server.utils.SwRobotFactory;

public abstract class ASwTextComponent extends ASwElement implements ISwTextComponent<Component> {

  public ASwTextComponent(long tid, JTextComponent c) {
    super(tid, c);
  }

  @Override
  public TafProperties basicExecCommand(TafProperties props) {
    System.out.println("SwTextComponent --> basicExecCommand");
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
//    getFixture().enterText(text);
    for (char c : text.toCharArray()) {
      getFixture().enterText(String.valueOf(c));
      try {
        Thread.sleep(SwRobotFactory.delayBetweenKeystrokes);
      }
      catch (Exception e) {}
    }
  }

  @Override
  public void fillProperties() {
    LabelFinder lf = new LabelFinder(getComponent());
    addProperty("leftlabel", lf.findLeftLabelProperty());
    addProperty("toplabel", lf.findTopLabelProperty());
    addProperty("rightlabel", lf.findRightLabelProperty());
  }

  @Override
  public JTextComponent getComponent() {
    return (JTextComponent)component;
  }

  @Override
  public JTextComponentFixture getFixture() {
    return new JTextComponentFixture(getRobot(), getComponent());
  }

  public String getText() {
    return getFixture().text();
  }

}
