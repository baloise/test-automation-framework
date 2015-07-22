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

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.assertj.swing.fixture.JTextComponentFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;

/**
 * 
 */
public class SwInput extends ASwElement implements ISwInput<Component> {

  public static int yTolerance = 5;

  public static int xTolerance = 5;

  public static int maxYTop = 25;

  public static int maxXRight = 20;

  public SwInput(long tid, JTextField c) {
    super(tid, c);
  }

  private void addLeftLabelProperty() {
    // System.out.println("Starting left label search: " + getComponent().toString());
    for (Component c : getComponent().getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getY() - yTolerance, getY() + yTolerance, c.getY())) {
          if (c.getX() < getX()) {
            // System.out.println("Adding leftlabel for component: " + c);
            String text = ((JLabel)c).getText().trim();
            if (!text.isEmpty()) {
              addProperty("leftlabel", text);
              break;
            }
          }
        }
      }
    }
  }

  private void addRightLabelProperty() {
    // System.out.println("Starting right label search: " + getComponent().toString());
    for (Component c : getComponent().getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getY() - yTolerance, getY() + yTolerance, c.getY())) {
          if (inRange(getX(), getRightX(), c.getX())) {
            // System.out.println("Adding rightlabel for component: " + c);
            String text = ((JLabel)c).getText().trim();
            if (!text.isEmpty()) {
              addProperty("rightlabel", text);
              break;
            }
          }
        }
      }
    }
  }

  private void addTopLabelProperty() {
    // System.out.println("Starting top label search: " + getComponent().toString());
    for (Component c : getComponent().getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getX() - xTolerance, getX() + xTolerance, c.getX())) {
          if (inRange(getY() - maxYTop, getY(), c.getY())) {
            // System.out.println("Adding toplabel for component: " + c);
            addProperty("toplabel", ((JLabel)c).getText());
            break;
          }
        }
      }
    }
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
      default:
        throw new IllegalArgumentException("command valid but not implemented yet: " + c);
    }
    return props;
  }

  public void clear() {
    getFixture().deleteText();
  }

  public void click() {
    System.out.println("Click!");
    System.out.println("Current text: " + getFixture().text());
    getFixture().click();
  }

  public void enterText(String text) {
    getFixture().enterText(text);
  }

  @Override
  public void fillProperties() {
    addLeftLabelProperty();
    addTopLabelProperty();
    addRightLabelProperty();
  }

  @Override
  public JTextField getComponent() {
    return (JTextField)component;
  }

  @Override
  public JTextComponentFixture getFixture() {
    return new JTextComponentFixture(getRobot(), getComponent());
  }

  private int getRightX() {
    return getComponent().getX() + getComponent().getWidth();
  }

  public String getText() {
    return getComponent().getText();
  }

  @Override
  public String getType() {
    return ISwInput.type;
  }

  private int getX() {
    return getComponent().getX();
  }

  private int getY() {
    return getComponent().getY();
  }

  private boolean inRange(int min, int max, int value) {
    return (min <= value && value <= max);
  }

}
