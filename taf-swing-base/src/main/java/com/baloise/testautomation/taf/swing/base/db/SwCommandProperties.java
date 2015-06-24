/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.base.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import com.baloise.testautomation.taf.common.utils.TafProperties;

/**
 * 
 */
public class SwCommandProperties extends H2Table {

  public String key = "";
  public String value = "";
  public int id = 0;

  public static void deleteCommandPropertiesForId(int id) {
    String deleteSQL = "DELETE FROM COMMAND_PROPERTIES WHERE c_id = " + id;
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(deleteSQL);
      ps.execute();
    }
    catch (Exception e) {
      error("error deleting commands properties for c_id = " + id, e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  public static TafProperties getTafPropertiesForId(int id) {
    Vector<SwCommandProperties> result = new Vector<SwCommandProperties>();
    ResultSet rs = null;
    if (conn() == null) {
      System.out.println("H2 database is NOT connected");
      return new TafProperties();
    }
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement("select key, value from command_properties where c_id = " + id);
      rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          SwCommandProperties command = new SwCommandProperties(id, rs.getString(1), rs.getString(2));
          result.add(command);
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      error("error loading command properties", e);
    }
    finally {
      closePreparedStatement(ps);
    }
    TafProperties props = new TafProperties();
    for (SwCommandProperties cp : result) {
      props.putObject(cp.key, cp.value);
    }
    return props;
  }

  public static TafProperties getForId(int id) {
    Vector<SwCommandProperties> result = new Vector<SwCommandProperties>();
    ResultSet rs = null;
    if (conn() == null) {
      System.out.println("H2 database is NOT connected");
      return new TafProperties();
    }
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement("select key, value from command_properties where c_id = " + id);
      rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          SwCommandProperties command = new SwCommandProperties(id, rs.getString(1), rs.getString(2));
          result.add(command);
        }
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      error("error loading command properties", e);
    }
    finally {
      closePreparedStatement(ps);
    }
    TafProperties props = new TafProperties();
    System.out.println("properties will be filled");
    for (SwCommandProperties cp : result) {
      System.out.println("key = " + cp.key + " value = " + cp.value);
      props.putObject(cp.key, cp.value);
    }
    return props;
  }

  public SwCommandProperties(int id, String key, String value) {
    super();
    this.id = id;
    this.key = key;
    this.value = value;
  }

  public void insert() {
    String insertSQL = "INSERT INTO COMMAND_PROPERTIES " + "(c_id, key, value) " + "VALUES (?, ?, ?)";
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(insertSQL);
      ps.setInt(1, id);
      ps.setString(2, key);
      ps.setString(3, value);
      ps.execute();
    }
    catch (SQLException e) {
      error("error inserting property", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  public static void insertForId(int id, Properties props) {
    for (Object key : props.keySet()) {
      SwCommandProperties cp = new SwCommandProperties(id, (String)key, props.getProperty((String)key));
      cp.insert();
    }
  }

  public static void insertForId(int id, TafProperties props) {
    for (String key : props.keySet()) {
      SwCommandProperties cp = new SwCommandProperties(id, key, props.getString(key));
      cp.insert();
    }
  }

}
