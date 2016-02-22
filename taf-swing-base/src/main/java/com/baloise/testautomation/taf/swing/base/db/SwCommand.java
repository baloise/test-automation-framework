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
import java.util.List;
import java.util.Vector;

/**
 * 
 */
public class SwCommand extends H2Table {

  public enum Status {
    preparing, ready, working, done
  }

  public static void createTable() {
    String sql = "CREATE TABLE COMMANDS (" + "id int, status int" + ");";
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(sql);
      ps.execute();
    }
    catch (SQLException e) {
      error("error creating table 'commands'", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  public static void deleteAll() {
    String deleteSQL = "DELETE FROM COMMANDS";
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(deleteSQL);
      ps.execute();
    }
    catch (Exception e) {
      error("error deleting all commands", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }
  
  public static void deleteCommandsForId(int id) {
    String deleteSQL = "DELETE FROM COMMANDS WHERE id = " + id;
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(deleteSQL);
      ps.execute();
    }
    catch (SQLException e) {
      error("error deleting commands for id = " + id, e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  // public enum Command {
  // notsupported, startinstrumentation, execute
  // }

  private static List<SwCommand> getForId(int id, Status status) {
    Vector<SwCommand> result = new Vector<SwCommand>();
    ResultSet rs = null;
    PreparedStatement ps = null;
    try {
      ps = conn()
          .prepareStatement("select status from commands where id = " + id + " and status = " + status.ordinal());
      rs = ps.executeQuery();
      // System.out.println(ps);
      if (rs != null) {
        while (rs.next()) {
          SwCommand command = new SwCommand(id, rs.getInt(1));
          result.add(command);
        }
      }
    }
    catch (SQLException e) {
      error("error loading commands", e);
      e.printStackTrace();
      throw new SwError(e);
    }
    finally {
      closePreparedStatement(ps);
    }
    return result;
  }

  public static List<SwCommand> getReadyCommandsForId(int id) {
    return getForId(id, Status.ready);
  }

  public static boolean isAllDone(int id) {
    try {
      return getForId(id, Status.done).size() > 0;
    }
    catch (Exception e) {
      return false;
    }
  }

  public static void setAllToDone(int id) {
    String updateSQL = "UPDATE COMMANDS SET status = " + Status.done.ordinal() + " WHERE id = " + id;
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(updateSQL);
      ps.execute();
    }
    catch (SQLException e) {
      error("error setting all to done status", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  // public String command = "";
  public int status = 0;

  public int id = 0;

  public SwCommand(int id) {
    this(id, Status.preparing.ordinal());
  }

  // public static boolean isAllDone(int id, String type) {
  // return getForId(id, type, Status.done).size() > 0;
  // }

  // private static List<SwCommand> getForId(int id, String type, Status status) {
  // Vector<SwCommand> result = new Vector<SwCommand>();
  // ResultSet rs = null;
  // PreparedStatement ps = null;
  // try {
  // ps = conn().prepareStatement(
  // "select status from commands where id = " + id + " and status = " + status.ordinal()
  // + " and command = '" + type + "'");
  // rs = ps.executeQuery();
  // // System.out.println(ps);
  // if (rs != null) {
  // while (rs.next()) {
  // SwCommand command = new SwCommand(id, rs.getInt(1));
  // result.add(command);
  // }
  // }
  // }
  // catch (SQLException e) {
  // error("error loading commands", e);
  // e.printStackTrace();
  // }
  // finally {
  // closePreparedStatement(ps);
  // }
  // return result;
  // }

  public SwCommand(int id, int status) {
    // TODO
    super();
    this.id = id;
    this.status = status;
  }

  // public Command asCommand() {
  // try {
  // return Command.valueOf(command.toLowerCase());
  // }
  // catch (Exception e) {
  // return null;
  // }
  // }

  /**
   * 
   */
  public void insert() {
    String insertSQL = "INSERT INTO COMMANDS " + "(id, status) " + "VALUES (?, ?)";
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(insertSQL);
      ps.setInt(1, id);
      ps.setInt(2, status);
      ps.execute();
    }
    catch (SQLException e) {
      error("error inserting command", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  public void setToDone() {
    updateFromTo(Status.working, Status.done);
  }

  public void setToReady() {
    updateFromTo(Status.preparing, Status.ready);
  }

  public void setToWorking() {
    updateFromTo(Status.ready, Status.working);
  }

  private void updateFromTo(Status from, Status to) {
    String updateSQL = "UPDATE COMMANDS SET status = ? WHERE id = " + id + " AND status = " + from.ordinal();
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(updateSQL);
      ps.setInt(1, to.ordinal());
      ps.execute();
    }
    catch (SQLException e) {
      error("error updating status", e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  /**
   * 
   */
  public static void listCommands() {
    ResultSet rs = null;
    PreparedStatement ps = null;
    try {
      ps = conn()
          .prepareStatement("select id, status from commands");
      rs = ps.executeQuery();
      // System.out.println(ps);
      if (rs != null) {
        while (rs.next()) {
          System.out.println("Record: id = " + rs.getInt(1) + ", status = " + rs.getInt(2));
        }
      }
    }
    catch (SQLException e) {
      error("error loading commands", e);
      e.printStackTrace();
      throw new SwError(e);
    }
    finally {
      closePreparedStatement(ps);
    }
  }

  // public static boolean isAllDone(int id) {
  // return (getForId(id, Status.working).size() == 0) && (getForId(id, Status.ready).size() == 0);
  // }

}
