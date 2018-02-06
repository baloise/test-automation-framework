package com.baloise.testautomation.taf.swing.server.utils;

public class Logger {

  public void info(String s) {
    System.out.println(s);
  }

  public void error(String s, Exception e) {
    System.out.println("[ERROR] " + s);
    e.printStackTrace();
  }

}
