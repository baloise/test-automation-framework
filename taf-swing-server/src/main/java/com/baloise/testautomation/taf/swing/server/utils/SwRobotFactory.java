/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.utils;

import static org.assertj.swing.util.Platform.osFamily;

import java.util.Locale;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.keystroke.KeyStrokeMap;

/**
 * 
 */
public class SwRobotFactory {

  private static Robot robot = null;

  public static Robot getRobot() {
    if (robot == null) {
      robot = BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock();
      robot.settings().delayBetweenEvents(10);
//    robot.settings().eventPostingDelay(10);
    }
    return robot;
  }
}
