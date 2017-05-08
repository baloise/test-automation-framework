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
import java.awt.Container;

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
    waitUntilFocused();
    return (JTextField)component;
  }

  private void waitUntilFocused() {
    long time = System.currentTimeMillis();
    boolean timedOut = false;
    boolean isFocusOwner = false;
    while (!(timedOut | isFocusOwner)) {
      boolean hasParent = true;
      Container parent = component.getParent();
      while (hasParent) {
        if (parent != null) {
          isFocusOwner = parent.isFocusOwner();
          System.out.println("parent: " + parent + ", is focus owner: " + isFocusOwner);
          parent = parent.getParent();
        }
        if (parent == null) {
          System.out.println("no more parent");
          hasParent = false;
        }
      }
      if (!isFocusOwner) {
        try {
          Thread.sleep(1000);
        }
        catch (Exception e) {
        }
      }
      if (System.currentTimeMillis() > time + 30000) {
        System.out.println("timed out");
        timedOut = true;
      }
    }
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
