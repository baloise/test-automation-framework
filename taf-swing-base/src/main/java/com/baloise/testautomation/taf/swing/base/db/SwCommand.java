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

  public SwCommand(int id) {
    this(id, Status.preparing.ordinal());
  }

  public SwCommand(int id, int status) {
    // TODO 
    super();
    this.id = id;
    this.status = status;
  }

//  public enum Command {
//    notsupported, startinstrumentation, execute
//  }

  public enum Status {
    preparing, ready, working, done
  }

//  public String command = "";
  public int status = 0;
  public int id = 0;

  public static List<SwCommand> getReadyCommandsForId(int id) {
    return getForId(id, Status.ready);
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

  public static boolean isAllDone(int id) {
    return getForId(id, Status.done).size() > 0;
  }

//  public static boolean isAllDone(int id, String type) {
//    return getForId(id, type, Status.done).size() > 0;
//  }

//  private static List<SwCommand> getForId(int id, String type, Status status) {
//    Vector<SwCommand> result = new Vector<SwCommand>();
//    ResultSet rs = null;
//    PreparedStatement ps = null;
//    try {
//      ps = conn().prepareStatement(
//          "select status from commands where id = " + id + " and status = " + status.ordinal()
//              + " and command = '" + type + "'");
//      rs = ps.executeQuery();
//      // System.out.println(ps);
//      if (rs != null) {
//        while (rs.next()) {
//          SwCommand command = new SwCommand(id, rs.getInt(1));
//          result.add(command);
//        }
//      }
//    }
//    catch (SQLException e) {
//      error("error loading commands", e);
//      e.printStackTrace();
//    }
//    finally {
//      closePreparedStatement(ps);
//    }
//    return result;
//  }

  private static List<SwCommand> getForId(int id, Status status) {
    Vector<SwCommand> result = new Vector<SwCommand>();
    ResultSet rs = null;
    PreparedStatement ps = null;
    try {
      ps = conn().prepareStatement(
          "select status from commands where id = " + id + " and status = " + status.ordinal());
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
    }
    finally {
      closePreparedStatement(ps);
    }
    return result;
  }

//  public Command asCommand() {
//    try {
//      return Command.valueOf(command.toLowerCase());
//    }
//    catch (Exception e) {
//      return null;
//    }
//  }

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

  public void setToWorking() {
    updateFromTo(Status.ready, Status.working);
  }

  public void setToReady() {
    updateFromTo(Status.preparing, Status.ready);
  }

  public void setToDone() {
    updateFromTo(Status.working, Status.done);
  }

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

  // public static boolean isAllDone(int id) {
  // return (getForId(id, Status.working).size() == 0) && (getForId(id, Status.ready).size() == 0);
  // }

}
