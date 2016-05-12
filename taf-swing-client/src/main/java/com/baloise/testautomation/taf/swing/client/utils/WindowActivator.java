/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.utils;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class WindowActivator {

  public static void activate(String title) throws IOException {
    new WindowActivator().activateByTitle(title);
  }
  
  protected void activateByTitle(String title) throws IOException {
    URL resource = getClass().getClassLoader().getResource("activate.vbs");
    String file = resource.toString().replace("file:/", "");
    System.out.println(file);
    Runtime.getRuntime().exec("wscript " + file + " " + title);
  }
  
  public static void main(String[] args) throws IOException {
    activate("Parsys");
  }
}
