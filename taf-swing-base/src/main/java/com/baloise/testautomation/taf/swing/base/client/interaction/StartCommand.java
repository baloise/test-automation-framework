package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.SwCommand;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;

class StartCommand implements InteractionCommand {

  private static final long serialVersionUID = 1L;

  private int id;
  private TafProperties inputProperties;

  StartCommand(int id, TafProperties properties) {
    this.id = id;
    this.inputProperties = properties;
  }

  @Override
  public void run() {
    SwCommandProperties.deleteCommandPropertiesForId(id);
    SwCommand.deleteCommandsForId(id);
    SwCommand c = new SwCommand(id);
    c.insert();
    SwCommandProperties.insertForId(id, inputProperties);
    c.setToReady();
  }

  @Override
  public TafProperties getReturnValue() {
    return null;
  }

  @Override
  public boolean isEquivalent(InteractionCommand otherCommand) {
    if (!(otherCommand instanceof StartCommand)) {
      return false;
    }
    return (this.getId() == ((StartCommand)otherCommand).getId())
        && (this.getInputProperties().equals(((StartCommand)otherCommand).getInputProperties()));
  }

  private TafProperties getInputProperties() {
    return inputProperties;
  }
  
  private int getId() {
    return id;
  }

  @Override
  public void injectReturnValueInto(InteractionCommand otherCommand) {}
  
  @Override
  public String toString() {
    return getClass().getSimpleName() + " (id: " + id + "; inputProperties: " + inputProperties + ")";
  }

}
