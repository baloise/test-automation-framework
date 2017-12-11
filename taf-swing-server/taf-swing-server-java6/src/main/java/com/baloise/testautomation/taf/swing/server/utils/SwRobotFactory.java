/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.utils;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;

/**
 * 
 */
public class SwRobotFactory {

  private static Robot robot = null;
  
  public static int delayBetweenEvents = 60;
  public static int eventPostingDelay = 70; // muss gleich oder grösser als delayBetweenEvents sein
  public static int delayBetweenKeystrokes = 1;

  public static Robot getRobot() {
    if (robot == null) {
      robot = BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock();
      robot.settings().delayBetweenEvents(delayBetweenEvents);
      robot.settings().eventPostingDelay(eventPostingDelay);
    }
    return robot;
  }
  
  public static void setDelayBetweenEvents(Integer ms) {
    System.out.println("setting delay between events (ms): " + ms);
    getRobot().settings().delayBetweenEvents(ms);
  }
  
  public static void setEventPostingDelay(Integer ms) {
    System.out.println("setting event posting delay (ms): " + ms);
    getRobot().settings().eventPostingDelay(ms);
  }
  
  public static void setDelayBetweenKeystrokes(Integer ms) {
    System.out.println("setting delay between keystrokes (ms): " + ms);
    delayBetweenKeystrokes = ms;
  }
  
}
