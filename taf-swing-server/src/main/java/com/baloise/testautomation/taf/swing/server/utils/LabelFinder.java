package com.baloise.testautomation.taf.swing.server.utils;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JLabel;

public class LabelFinder {

  private Component component;

  public static int yTolerance = 5;

  public static int xTolerance = 5;

  public static int maxYTop = 25;

  public static int maxXRight = 20;

  public LabelFinder(Component component) {
    this.component = component;
  }

  public String findLeftLabelProperty() {
    Vector<JLabel> labels = new Vector<JLabel>();
    // System.out.println("Starting left label search: " + getComponent().toString());
    for (Component c : component.getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getY() - yTolerance, getY() + yTolerance, c.getY())) {
          if (c.getX() < getX()) {
            // System.out.println("Adding leftlabel for component: " + c);
            labels.add((JLabel)c);
          }
        }
      }
    }
    if (labels.size() == 1) {
      String text = labels.get(0).getText().trim();
      if (!text.isEmpty()) {
        return text;
      }
    }
    if (labels.size() > 1) {
      JLabel closestLabel = null;
      for (JLabel label : labels) {
        if (closestLabel == null) {
          closestLabel = label;
        }
        else {
          if (!"".equals(label.getText().trim())) {
            int closestX = component.getX() - closestLabel.getX();
            int labelX = component.getX() - label.getX();
            if (closestX > labelX) {
              if (labelX > 0) {
                closestLabel = label;
              }
            }
          }
        }
      }
      return closestLabel.getText();
    }
    return null;
  }

  public String findRightLabelProperty() {
    // System.out.println("Starting right label search: " + getComponent().toString());
    for (Component c : component.getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getY() - yTolerance, getY() + yTolerance, c.getY())) {
          if (inRange(getX(), getRightX(), c.getX())) {
            // System.out.println("Adding rightlabel for component: " + c);
            String text = ((JLabel)c).getText().trim();
            if (!text.isEmpty()) {
              return text;
            }
          }
        }
      }
    }
    return null;
  }

  public String findTopLabelProperty() {
    // System.out.println("Starting top label search: " + getComponent().toString());
    for (Component c : component.getParent().getComponents()) {
      if (c instanceof JLabel) {
        // System.out.println("Label: " + c.toString());
        if (inRange(getX() - xTolerance, getX() + xTolerance, c.getX())) {
          if (inRange(getY() - maxYTop, getY(), c.getY())) {
            // System.out.println("Adding toplabel for component: " + c);
            String text = ((JLabel)c).getText().trim();
            if (!text.isEmpty()) {
              return text;
            }
          }
        }
      }
    }
    return null;
  }

  private int getRightX() {
    return component.getX() + component.getWidth();
  }

  private int getX() {
    return component.getX();
  }

  private int getY() {
    return component.getY();
  }

  private boolean inRange(int min, int max, int value) {
    return (min <= value && value <= max);
  }

}
