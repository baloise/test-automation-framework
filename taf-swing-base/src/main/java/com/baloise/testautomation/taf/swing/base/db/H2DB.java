package com.baloise.testautomation.taf.swing.base.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2DB {

  private static Connection conn = null;

  static Connection conn() {
    return conn;
  }

  static void createTables() {
    initConnection();
    SwCommand.createTable();
    SwCommandProperties.createTable();
    SwCommandProperties.deleteAll();
    SwCommand.deleteAll();
  }

  public static void init() {
    try {
      createTables();
    }
    catch (Exception e) {}
  }

  public static void initConnection() {
    try {
      try {
        if (conn != null) {
          conn.close();
        }
      }
      catch (Exception e1) {}
      finally {
        conn = null;
      }
      Class.forName("org.h2.Driver");
      String tempDir = System.getProperty("java.io.tmpdir");
      System.out.println("looking for h2db at " + tempDir);
      conn = DriverManager.getConnection("jdbc:h2:" + tempDir + "/swinginstrumentation;AUTO_SERVER=TRUE", "sa", "");
    }
    catch (Exception e2) {
      System.out.println("error initialising database");
      e2.printStackTrace();
      return;
    }
  }

}
