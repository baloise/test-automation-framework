/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.main;

import java.io.File;
import java.io.IOException;

/**
 * 
 */
public class SimpleSwStarter {

  public SimpleSwStarter() {
    System.out.println("Instrumentation successful");
    File f = new File("c:/testing/" + System.currentTimeMillis() + ".txt");
    try {
      f.createNewFile();
    }
    catch (IOException e) {
      System.out.println("File was not created");
    }
  }

}
