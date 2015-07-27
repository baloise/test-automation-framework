/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.main;

import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramCommand;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramId;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramMessage;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramPath;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramSpy;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramStatus;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramWatch;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.H2DB;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;

/**
 * 
 */
public class SwStarter {

  private static void error(String s, Exception e) {
    System.out.println("[ERROR] " + s);
    e.printStackTrace();
  }

  private SwApplication swApplication = new SwApplication();

  private boolean pollingActive = false;
  private boolean watch = false;
  private boolean spy = false;
  private String spyFileName = null;

  public SwStarter() {
    info("will try to start instrumentation");
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

    if (watch) {
      Thread watcher = new Thread() {
        public void run() {
          watch();
        }
      };
      watcher.start();
    }

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

  private void execStartInstrumentation(SwCommand command) {
    TafProperties resultProps = new TafProperties();
    try {
      info("starting instrumentation");
      command.setToWorking();
      TafProperties props = SwCommandProperties.getForId(command.id);
      String c = props.getString(paramCommand);
      if (ISwApplication.Command.startinstrumentation.toString().equalsIgnoreCase(c)) {
        swApplication.id = props.getLong(paramId).intValue();
        spy = props.getBoolean(paramSpy);
        watch = props.getBoolean(paramWatch);
        spyFileName = props.getString(paramPath);
        props = new TafProperties();
        resultProps.putObject(paramStatus, "started");
        info("started with id = " + swApplication.id);
      }
    }
    catch (Exception e) {
      resultProps.putObject(paramStatus, "error");
      resultProps.putObject(paramMessage, e.getMessage());
    }
    finally {
      setCommandProperties(0, resultProps);
      command.setToDone();
    }
  }

  // @Override
  public String getDbName() {
    return "c:/db/test";
  }

  private Long getLong(String s) {
    try {
      return Long.parseLong(s);
    }
    catch (Exception e) {}
    return null;
  }

  private SwCommand getNextCommand() {
    try {
      List<SwCommand> commands = SwCommand.getReadyCommandsForId(swApplication.id);
      return commands.get(0);
    }
    catch (Exception e) {}
    return null;
  }

  // @Override
  public ISwApplication<?> getSwApplication() {
    return swApplication;
  }

  private void info(String s) {
    System.out.println(s);
  }

  public void poll() {
    while (true) {
      pollingActive = true;
      try {
        if (spy) {
          swApplication.storeLastHierarchy(spyFileName);
        }
        Thread.sleep(500);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      try {
        SwCommand nextCommand = getNextCommand();
        if (nextCommand != null) {
          nextCommand.setToWorking();
          TafProperties props = SwCommandProperties.getTafPropertiesForId(swApplication.id);
          info("Executing command: " + nextCommand.id);
          info("Incoming properties: " + props);
          props = swApplication.execCommand(props);
          setCommandProperties(swApplication.id, props);
          nextCommand.setToDone();
          info("Outgoing properties: " + props);
          info("Finished command: " + nextCommand.id);
        }
      }
      catch (Exception e) {
        error("error executing command", e);
      }
    }
  }

  private void setCommandProperties(int id, TafProperties props) {
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommandProperties.insertForId(id, props);
  }

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
}
