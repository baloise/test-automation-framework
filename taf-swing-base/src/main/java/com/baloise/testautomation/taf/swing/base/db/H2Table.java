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
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 */
public class H2Table {

  protected static Connection conn() {
    return H2DB.conn();
  }

  protected static void error(String s, Exception e) {
    System.out.println("[ERROR] " + s);
  }

  protected static void closePreparedStatement(PreparedStatement ps) {
    if (ps != null) {
      try {
        ps.close();
      }
      catch (SQLException e) {
        error("error closing prepared statement", e);
      }
    }
  }




}
