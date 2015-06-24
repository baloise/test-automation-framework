/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.base.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 */
public class H2DB {

  private static Connection conn = null;

  public static Connection conn() {
    return conn;
  }
  
  public static void init(String dbname) {
    try {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e1) {
      } finally {
        conn = null;
      }
      Class.forName("org.h2.Driver");
      conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/" + dbname, "sa", "");
    }
    catch (Exception e2) {
      System.out.println("error initialising database");
      e2.printStackTrace();
      return;
    }
  }
  

}
