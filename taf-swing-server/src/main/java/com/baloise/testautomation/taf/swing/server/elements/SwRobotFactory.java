/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.util.RobotFactory;

/**
 * 
 */
public class SwRobotFactory {

  private static Robot robot = null;
  
  public static Robot getRobot() {
    if (robot == null) {
      robot = BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock();
    }
    return robot;
  }
}
