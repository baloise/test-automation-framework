package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;

class DeleteCommandsForId implements InteractionCommand {

  private static final long serialVersionUID = 1L;

  private int id;
  
  DeleteCommandsForId(int id) {
    this.id = id;
  }

  @Override
  public void run() {
    SwCommand.deleteCommandsForId(id);
  }

  @Override
  public TafProperties getReturnValue() {
    return null;
  }

  @Override
  public boolean isEquivalent(InteractionCommand otherCommand) {
    if (!(otherCommand instanceof DeleteCommandsForId)) {
      return false;
    }
    return this.getId() == ((DeleteCommandsForId)otherCommand).getId();
  }

  private int getId() {
    return id;
  }

  @Override
  public void injectReturnValueInto(InteractionCommand otherCommand) {}
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + " (id: " + id + ")";
  }

}
