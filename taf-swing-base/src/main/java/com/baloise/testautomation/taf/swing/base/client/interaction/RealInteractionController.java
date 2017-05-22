package com.baloise.testautomation.taf.swing.base.client.interaction;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baloise.testautomation.taf.common.utils.TafProperties;

public class RealInteractionController implements InteractionController {

  private List<InteractionCommand> journal = new ArrayList<InteractionCommand>();

  private final boolean isJournalEnabled;

  private static Logger logger = LoggerFactory.getLogger(RealInteractionController.class);

  private RealInteractionController(boolean isJournalEnabled) {
    this.isJournalEnabled = isJournalEnabled;
  }

  public static RealInteractionController withJournal() {
    return new RealInteractionController(true);
  }

  public static RealInteractionController withoutJournal() {
    return new RealInteractionController(false);
  }

  @Override
  public void init() {
    runCommand(new Init());
  }

  @Override
  public void deleteCommandPropertiesForId(int id) {
    runCommand(new DeleteCommandPropertiesForId(id));
  }

  @Override
  public void deleteCommandsForId(int id) {
    runCommand(new DeleteCommandsForId(id));
  }

  @Override
  public void startCommand(int id, TafProperties props) {
    runCommand(new StartCommand(id, props));
  }

  @Override
  public void waitUntilDone(int id, int clientTimeoutInMsecs) {
    runCommand(new WaitUntilDone(id, clientTimeoutInMsecs));
  }

  @Override
  public TafProperties getTafPropertiesForId(int id) {
    InteractionCommand command = new GetTafPropertiesForId(id);
    runCommand(command);
    return command.getReturnValue();
  }

  private void runCommand(InteractionCommand command) {
    if (isJournalEnabled) {
      journal.add(command);
    }
    command.run();
    logger.info("Interaction command executed: " + command);
  }

  @Override
  public void serializeJournal(String path) {
    if (!isJournalEnabled) {
      throw new IllegalStateException("Attempting to serliazie journal, but journal was disabled");
    }
    try {
      FileOutputStream out = new FileOutputStream(path);
      ObjectOutputStream oos = new ObjectOutputStream(out);
      oos.writeObject(journal);
      oos.flush();
      oos.close();
    }
    catch (Exception e) {
      throw new RuntimeException("Error trying to serialize: " + journal, e);
    }
  }

  @Override
  public void startApplication(String commandline) {
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(commandline);
      p.waitFor();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void killWithPID(Integer pid) {
    logger.debug("Killing task with PID = " + pid + "...");
    String command = "Taskkill /F /T /FI \"PID eq " + pid + "\"";
    try {
      Runtime rt = Runtime.getRuntime();

      Process pr = rt.exec(command);
      BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line = null;
      while ((line = input.readLine()) != null && (!line.contains("keine Tasks") || line.contains("No tasks"))) {
        logger.debug(line);
      }
    }
    catch (Exception e) {
      throw new RuntimeException("Error trying to kill pid: " + pid, e);
    }
  }

  @Override
  public void stopApplication(String jnlp) {
    try {
      String line;
      Process p = Runtime.getRuntime()
          .exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe /v /fi \"IMAGENAME eq javaw.exe*\" /fo csv");
      BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
      Vector<Integer> pids = new Vector<Integer>();
      while ((line = input.readLine()) != null) {
        try {
          Integer pid = Integer.parseInt(line.split(",")[1].replace("\"", ""));
          logger.debug("javaw.exe found with PID = " + pid);
          pids.add(pid);
        }
        catch (Exception e) {}
      }
      input.close();
      Vector<Integer> pidsToKill = new Vector<Integer>();
      for (Integer pid : pids) {
        p = Runtime.getRuntime().exec("wmic process where processid=\"" + pid + "\" get CommandLine");
        input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
          if (line.toLowerCase().contains("appstarter") || line.toLowerCase().contains(jnlp)) {
            logger.debug("javaw.exe contains 'appstarter' or '" + jnlp + "', PID = " + pid + " slated for termination");
            pidsToKill.add(pid);
            break;
          }
        }
      }
      input.close();
      for (Integer pid : pidsToKill) {
        killWithPID(pid);
      }
    }
    catch (Exception err) {
      throw new RuntimeException("Error trying to stop jnlp: " + jnlp, err);
    }
  }

}
