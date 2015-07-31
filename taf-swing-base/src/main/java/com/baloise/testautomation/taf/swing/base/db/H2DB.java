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
import java.sql.SQLException;

import org.h2.tools.Server;

/**
 * 
 */
public class H2DB {

  private static Connection conn = null;

  private static Server server = null;

  private static String port = "9092";

  public static Connection conn() {
    return conn;
  }

  public static void createTables() {
    initConnection();
    SwCommand.createTable();
    SwCommandProperties.createTable();
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
      conn = DriverManager.getConnection("jdbc:h2:tcp://localhost:" + port
          + "/mem:swinginstrumentation;DB_CLOSE_DELAY=-1", "sa", "");
    }
    catch (Exception e2) {
      System.out.println("error initialising database");
      e2.printStackTrace();
      return;
    }
  }

  public static void init() {
    try {
      startServer();
      initConnection();
      createTables();
    }
    catch (Exception e) {
    }
  }

  public static void startServer() {
    try {
      if (server != null) {
        if (server.isRunning(false)) {
          System.out.println("Server is already running on 'jdbc:h2:" + server.getURL() + "/mem:swinginstrumentation'");
          return;
        }
        stopServer();
      }
      server = Server.createTcpServer("-tcpPort", port, "-tcpAllowOthers");
      server.start();
      System.out.println("Server started and running on 'jdbc:h2:" + server.getURL() + "/mem:swinginstrumentation'");
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void stopServer() {
    if (server != null) {
      server.stop();
      server = null;
    }
  }

}
