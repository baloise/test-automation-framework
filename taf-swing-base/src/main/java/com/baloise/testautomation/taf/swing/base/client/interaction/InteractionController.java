package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;

public interface InteractionController {
  
  public void init();

  public void deleteCommandPropertiesForId(int id);
  
  public void deleteCommandsForId(int id);

  public void startCommand(int id, TafProperties props);
  
  public void waitUntilDone(int id, int clientTimeoutInMsecs);
  
  public TafProperties getTafPropertiesForId(int id);

  public void serializeJournal(String string);

  public void startApplication(String commandline);

  public void stopApplication(String jnlp);

}
