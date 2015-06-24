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
import java.io.PrintStream;
import java.util.List;

import javax.swing.event.SwingPropertyChangeSupport;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication.Command;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.H2DB;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;

/**
 * 
 */
public class SwStarter {

  private SwApplication swApplication = new SwApplication();

  public SwStarter() {
    info("try to start instrumentation");
    try {
      // Server.createTcpServer().start();
      H2DB.init(getDbName());
      SwCommand nextCommand = getNextCommand();
      if (nextCommand != null) {
        execStartInstrumentation(nextCommand);
        info("started! Id = " + swApplication.id);
      }
      else {
        info("command " + nextCommand + " not executed, use startinstrumentation to start instrumentation");
      }
    }
    catch (Exception e) {
      File file = new File("c:/testing/error.log");
      try {
        PrintStream ps = new PrintStream(file);
        e.printStackTrace(ps);
        ps.close();
      }
      catch (Exception ex) {}
      error("error starting instrumentation", e);
    }

    Thread watcher = new Thread() {
      public void run() {
        watch();
      }
    };
    watcher.start();

    if (swApplication.id != 0) {
      info("Start polling");
      Thread thread = new Thread() {
        public void run() {
          poll();
        }
      };
      thread.start();
    }
  }

  private void info(String s) {
    System.out.println(s);
  }

  private static void error(String s, Exception e) {
    System.out.println("[ERROR] " + s);
    e.printStackTrace();
  }

  private boolean pollingActive = false;

  public void watch() {
    while (true) {
      try {
        Thread.sleep(10000);
        System.out.println("watching!");
        System.out.println("id = " + swApplication.id);
        System.out.println("polling active = " + pollingActive);
        pollingActive = false;
      }
      catch (Exception e) {
        // TODO: handle exception
      }
    }
  }

  public void poll() {
    while (true) {
      pollingActive = true;
      try {
        SwCommand nextCommand = getNextCommand();
        if (nextCommand != null) {
          info("Executing command");
          TafProperties props = SwCommandProperties.getTafPropertiesForId(swApplication.id);
          props = swApplication.execCommand(props.getString("type"), props.getString("command"), props);
          setCommandProperties(swApplication.id, props);
        }
      }
      catch (Exception e) {
        error("error executing command", e);
      }
    }
  }

  private Long getLong(String s) {
    try {
      return Long.parseLong(s);
    }
    catch (Exception e) {}
    return null;
  }

  // @Override
  public String getDbName() {
    return "c:/db/test";
  }

  // @Override
  public ISwApplication getSwApplication() {
    return swApplication;
  }

  private void execStartInstrumentation(SwCommand command) {
    TafProperties resultProps = new TafProperties();
    String status = "status";
    try {
      info("starting instrumentation");
      command.setToWorking();
      TafProperties props = SwCommandProperties.getForId(command.id);
      String c = props.getString("command");
      if (ISwApplication.Command.startinstrumentation.toString().equalsIgnoreCase(c)) {
        swApplication.id = props.getLong("id").intValue();
        props = new TafProperties();
        resultProps.putObject(status, "started");
        info("started with id = " + swApplication.id);
      }
    } catch (Exception e) {
      resultProps.putObject(status, "error");
      resultProps.putObject("message", e.getMessage());
    }
    finally {
      setCommandProperties(0, resultProps);
      command.setToDone();
    }
  }

  private void setCommandProperties(int id, TafProperties props) {
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommandProperties.insertForId(id, props);
  }

  private SwCommand getNextCommand() {
    try {
      List<SwCommand> commands = SwCommand.getReadyCommandsForId(swApplication.id);
      return commands.get(0);
    }
    catch (Exception e) {}
    return null;
  }
}
