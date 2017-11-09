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
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramJavaClassPath;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramMessage;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramPath;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramSpy;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramStatus;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramSunJavaCommand;
import static com.baloise.testautomation.taf.common.interfaces.ISwApplication.paramWatch;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.assertj.swing.keystroke.KeyStrokeMap;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.H2DB;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;
import com.baloise.testautomation.taf.swing.base.db.SwError;

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
    info("will try to start instrumentation V0.0.3-003");

    debugSystemProperties();

    tryToStartInstrumentation();

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
      KeyStrokeMap.reloadFromSystemSettings();
      Thread thread = new Thread() {
        public void run() {
          poll();
        }
      };
      thread.start();
    }
    else {
      info("Application seems to NOT need instrumentation --> application will run without instrumentation");
    }
  }

  /**
   * 
   */
  private void debugSystemProperties() {
    Properties props = System.getProperties();
    info("listing jvm properties");
    for (Object p : props.keySet()) {
      info(p + " = " + System.getProperty((String)p));
    }
  }

  private void execStartInstrumentation(SwCommand command) {
    TafProperties resultProps = new TafProperties();
    try {
      info("starting instrumentation");
      TafProperties props = SwCommandProperties.getTafPropertiesForId(command.id);
      String c = props.getString(paramCommand);
      if (ISwApplication.Command.startinstrumentation.toString().equalsIgnoreCase(c)) {
        String classPathContains = props.getString(paramJavaClassPath);
        String sunJavaCommandContains = props.getString(paramSunJavaCommand);
        info("looking for jvm java.class.path property containing '" + classPathContains + "'");
        boolean found = System.getProperty("java.class.path").toLowerCase().contains(classPathContains.toLowerCase());
        if (!found) {
          info("looking for jvm sun.java.command property containing '" + sunJavaCommandContains + "'");
          found = System.getProperty("sun.java.command").toLowerCase().contains(sunJavaCommandContains.toLowerCase());
        }
        if (found) {
          swApplication.id = props.getLong(paramId).intValue();
//          if (props.getLong(paramDelayBetweenEvents) != null) {
//            SwRobotFactory.delayBetweenEvents = props.getLong(paramDelayBetweenEvents).intValue();
//          }
          spy = props.getBoolean(paramSpy);
          watch = props.getBoolean(paramWatch);
          spyFileName = props.getString(paramPath);
          command.setToWorking();
          props = new TafProperties();
          resultProps.putObject(paramStatus, "started");
          info("started with id = " + swApplication.id);
          setCommandProperties(0, resultProps);
          command.setToDone();
        }
      }
    }
    catch (Exception e) {
      resultProps.putObject(paramStatus, "error");
      resultProps.putObject(paramMessage, e.getMessage());
      setCommandProperties(0, resultProps);
      command.setToDone();
    }
    finally {}
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
      try {
        return commands.get(0);
      }
      catch (ArrayIndexOutOfBoundsException e) {
        if (needsInfo) {
          info("no next command found --> try again at next poll intervall");
          needsInfo = false;
        }
      }
    }
    catch (SwError swe) {
      info("getting commands failed --> init database to be ready when next attempt is made");
      try {
        H2DB.initConnection();
      }
      catch (Exception e) {}
    }
    catch (Exception e) {
      error("unexpected exception", e);
    }
    return null;
  }

  // @Override
  public ISwApplication<?> getSwApplication() {
    return swApplication;
  }

  private void info(String s) {
    System.out.println(s);
  }

  private boolean needsInfo = false;
  private long pollingInfoIntervall = 10; // seconds
  
  public void poll() {
    long time = System.currentTimeMillis();
    try {
      while (true) {
        pollingActive = true;
        if (System.currentTimeMillis() > time + (pollingInfoIntervall *1000)) {
          needsInfo = true;
          time = System.currentTimeMillis();
        }
        if (needsInfo) {
          info("polling " + System.currentTimeMillis());
        } 
        try {
          if (spy) {
            swApplication.storeLastHierarchy(spyFileName);
          }
          Thread.sleep(50);
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
    finally {
      SwCommand.setAllToDone(swApplication.id);
    }
  }

  private void setCommandProperties(int id, TafProperties props) {
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommandProperties.insertForId(id, props);
  }

  private boolean tryToStartInstrumentation() {
    try {
      H2DB.initConnection();
      info("H2DB-connection initialised");
      SwCommand nextCommand = getNextCommand();
      if (nextCommand != null) {
        execStartInstrumentation(nextCommand);
        if (swApplication.id != 0) {
          info("started! Id = " + swApplication.id);
          return true;
        }
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
      error("starting instrumentation was NOT successful", e);
    }
    return false;
  }

  public void watch() {
    while (true) {
      try {
        Thread.sleep(10000);
        System.out.println("watching!");
        System.out.println("id = " + swApplication.id);
        System.out.println("polling active = " + pollingActive);
        SwCommand.listCommands();
        SwCommandProperties.listProperties();
        debugSystemProperties();
        pollingActive = false;
      }
      catch (Exception e) {
        e.printStackTrace();
        // TODO: handle exception
      }
    }
  }
}
