package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import org.assertj.swing.fixture.AbstractComponentFixture;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;

public abstract class ASwElement implements ISwElement<Component> {

  public Component component;
  public TafProperties properties;
  //public String tagName;
  public long tid;

  public ASwElement(long tid, Component component) {
    //this.tagName = tagName;
    this.tid = tid;
    this.component = component;
    this.properties = new TafProperties();
    Rectangle r = component.getBounds();
    addProperty("name", component.getName());
    addProperty("tid", tid);
    addProperty("x", r.x);
    addProperty("y", r.y);
    addProperty("width", r.width);
    addProperty("height", r.height);
    fillProperties();
  }

  public AbstractComponentFixture getFixture() {
    return null;
  };
  
  protected String asValidAttribute(String s) {
    if (s == null) {
      return s;
    }
    s = s.replace("&", "&amp;");
    s = s.replace("<", "&lt;");
    s = s.replace(">", "&gt;");
    s = s.replace("\"", "&quot;");
    s = s.replace("'", "&apos;");
    return s;
  }


  public org.assertj.swing.core.Robot getRobot() {
    return SwRobotFactory.getRobot();
  }
  
  protected void addProperty(String key, String value) {
    if ((key != null) && (value != null)) {
      properties.putObject(key, value);
    }
  }

  protected void addProperty(String key, int value) {
    addProperty(key, new Integer(value).toString());
  }

  protected void addProperty(String key, long value) {
    addProperty(key, new Long(value).toString());
  }

  public abstract void fillProperties();

  public abstract Component getComponent();

//  public String getTagName() {
//    if (tagName == null) {
//      return "null-tag";
//    }
//    if (tagName.isEmpty()) {
//      return "empty-tag-" + component.getClass().getSimpleName();
//    }
//    return tagName;
//  }

  public long getTID() {
    return tid;
  }

  protected void click(Point p, int mask) {
    while (Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent() != null) {};
    try {
      System.out.println("Click auf " + p);
      Robot robot = new Robot();
      robot.mouseMove(p.x, p.y);
      robot.mousePress(mask);
      robot.delay(100);
      robot.mouseRelease(mask);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void waitUntilReady() {
    while (Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent() != null) {};
    long time = System.currentTimeMillis();
    boolean isReady = false;
    while (!isReady) {
      try {
        isReady = getComponent().isShowing() && getComponent().isEnabled() && getComponent().isFocusable();
        if (!isReady) {
          System.out.println("Wait --> element not yet enabled");
        }
      } catch (Exception e) {
      }
      if (System.currentTimeMillis() > time + 1000 * 10) {
        // TODO Throw an exception --> Component is NOT ready for user input
        break;
      }
    }
  }

  protected void click(Component c, int mask) {
    if (c != null) {
      if (c.getBounds().isEmpty()) {
        System.out.println("Can't click --> has no bounds!");
        return;
      }
      waitUntilReady();
      Point upperLeft = c.getLocationOnScreen(); 
      Rectangle bounds = new Rectangle(upperLeft.x, upperLeft.y, c.getWidth(), c.getHeight());
      Point p = new Point(new Double(bounds.getCenterX()).intValue(), new Double(bounds.getCenterY()).intValue());
      click(p, mask);
    }
  }

  @Override
  public String toString() {
    return "tagName=" + getType() + " " + component.toString();
  }

  @Override
  public TafProperties getProperties() {
    return properties;
  }

  @Override
  public void setProperties(TafProperties props) {
    properties = props;
  }

  public String getPropertiesAsString() {
    String attributes = "";
    for (Object key : getProperties().keySet()) {
      attributes = attributes + " " + key.toString() + "=\"" + properties.getObject(key.toString()) + "\"";
    }
    return attributes;
  }
  
  @Override
  public Component getReference() {
    return component;
  }

  @Override
  public void setApplication(ISwApplication application) {}

  @Override
  public void setReference(Component component) {
    this.component = component;
  }

  public TafProperties execCommand(TafProperties props) {
    // TODO
    return new TafProperties();
  }
  
}
