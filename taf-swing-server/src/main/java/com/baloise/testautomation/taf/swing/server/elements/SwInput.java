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
import java.awt.Window;

import javax.swing.JTextField;

import org.assertj.swing.fixture.JTextComponentFixture;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;
import com.baloise.testautomation.taf.swing.server.utils.LabelFinder;
import com.baloise.testautomation.taf.swing.server.utils.SwRobotFactory;

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
  public JTextField getComponent() {
    // waitUntilFocused();
    return (JTextField)component;
  }

  private Window getSelectedWindow(Window[] windows) {
    Window result = null;
    for (int i = 0; i < windows.length; i++) {
      Window window = windows[i];
      if (window.isActive()) {
        result = window;
      }
      else {
        Window[] ownedWindows = window.getOwnedWindows();
        if (ownedWindows != null) {
          result = getSelectedWindow(ownedWindows);
        }
      }
    }
    return result;
  }

//  private void waitUntilReady() {
//    System.out.println("WindowMonitor event queues: " + WindowMonitor.instance().allEventQueues().size());
//    for (EventQueue eq : WindowMonitor.instance().allEventQueues()) {
//      System.out.println("Check if queue is empty: " + eq);
//      while (eq.peekEvent() != null) {
//        System.out.println("Event queue not empty: " + eq);
//      }
//    }
//    // long time = System.currentTimeMillis();
//    // boolean timedOut = false;
//    // while (!timedOut) {
//    // if (ComponentShowingQuery.isShowing(component)) {
//    // return;
//    // }
//    // System.out.println("component is not yet showing: " + component);
//    // try {
//    // Thread.sleep(1000);
//    // }
//    // catch (Exception e) {}
//    // if (System.currentTimeMillis() > time + 30000) {
//    // System.out.println("timed out");
//    // timedOut = true;
//    // }
//    // }
//  }

  @Override
  public JTextComponentFixture getFixture() {
//    waitUntilReady();
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
