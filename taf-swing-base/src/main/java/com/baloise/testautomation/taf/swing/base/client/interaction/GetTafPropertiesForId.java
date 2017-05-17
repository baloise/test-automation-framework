package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.SwCommandProperties;

class GetTafPropertiesForId implements InteractionCommand {

  private static final long serialVersionUID = 1L;

  private int id;

  private TafProperties returnValue;

  public GetTafPropertiesForId(int id) {
    this.id = id;
  }

  @Override
  public void run() {
    returnValue = SwCommandProperties.getTafPropertiesForId(id);
  }

  @Override
  public TafProperties getReturnValue() {
    return returnValue;
  }

  @Override
  public boolean isEquivalent(InteractionCommand otherCommand) {
    if (!(otherCommand instanceof GetTafPropertiesForId)) {
      return false;
    }
    return this.getId() == ((GetTafPropertiesForId)otherCommand).getId();
  }

  private int getId() {
    return id;
  }

  @Override
  public void injectReturnValueInto(InteractionCommand otherCommand) {
    if (!isEquivalent(otherCommand)) {
      throw new RuntimeException("Unexpected trying to inject return value of: " + otherCommand + " into: " + this);
    }
    ((GetTafPropertiesForId)otherCommand).setReturnValue(this.getReturnValue());
  }

  private void setReturnValue(TafProperties properties) {
    returnValue = properties;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " (id: " + id + "; returnValue: " + returnValue + ")";
  }

}
