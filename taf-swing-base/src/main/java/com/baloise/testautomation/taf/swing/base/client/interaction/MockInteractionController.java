package com.baloise.testautomation.taf.swing.base.client.interaction;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baloise.testautomation.taf.common.utils.TafProperties;

public class MockInteractionController implements InteractionController {

  private static Logger logger = LoggerFactory.getLogger(MockInteractionController.class);
  
  public List<InteractionCommand> dbJournal = new ArrayList<InteractionCommand>();

  public List<InteractionCommand> storedDbJournal = new ArrayList<InteractionCommand>();
  
  public static MockInteractionController newWithJournal(String path) {
    MockInteractionController interactionController = new MockInteractionController();
    interactionController.deserializeJournal(path);
    return interactionController;
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
    dbJournal.add(command);
    InteractionCommand expectedCommand = storedDbJournal.get(dbJournal.size() - 1);
    if (!command.isEquivalent(expectedCommand)) {
      throw new RuntimeException(
          "Unexpected command: " + command + " doesn't match recored history: " + expectedCommand);
    }
    expectedCommand.injectReturnValueInto(command);
    logger.info("Mock Interaction command executed: " + command);
  }

  @Override
  public void serializeJournal(String string) {}

  @SuppressWarnings("unchecked")
  public void deserializeJournal(String string) {
    try {
      FileInputStream fis = new FileInputStream(string);
      ObjectInputStream ois = new ObjectInputStream(fis);
      storedDbJournal = (List<InteractionCommand>)ois.readObject();
      ois.close();
    }
    catch (Exception e) {
      throw new RuntimeException("Error trying to deserialize: " + string, e);
    }
  }

  @Override
  public void startApplication(String commandline) {
    // Do nothing
  }

  @Override
  public void stopApplication(String jnlp) {
    // Do nothing
  }

}
