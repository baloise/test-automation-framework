package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwTimeout;

class WaitUntilDone implements InteractionCommand {

  private static final long serialVersionUID = 1L;

  private int id;
  private int clientTimeoutInMsecs;

  public WaitUntilDone(int id, int clientTimeoutInMsecs) {
    this.id = id;
    this.clientTimeoutInMsecs = clientTimeoutInMsecs;
  }

  @Override
  public void run() {
    long time = System.currentTimeMillis();
    while (!SwCommand.isAllDone(id)) {
      if (System.currentTimeMillis() > time + clientTimeoutInMsecs) {
        throw new SwTimeout();
      }
    }
  }

  @Override
  public TafProperties getReturnValue() {
    return null;
  }

  @Override
  public boolean isEquivalent(InteractionCommand otherCommand) {
    if (!(otherCommand instanceof WaitUntilDone)) {
      return false;
    }
    return (this.getId() == ((WaitUntilDone)otherCommand).getId())
        && (this.getClientTimeoutInMsecs() == ((WaitUntilDone)otherCommand).getClientTimeoutInMsecs());
  }

  private int getClientTimeoutInMsecs() {
    return clientTimeoutInMsecs;
  }

  private int getId() {
    return id;
  }

  @Override
  public void injectReturnValueInto(InteractionCommand otherCommand) {}
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + " (id: " + id + "; clientTimeoutInMsecs: " + clientTimeoutInMsecs + ")";
  }

}
