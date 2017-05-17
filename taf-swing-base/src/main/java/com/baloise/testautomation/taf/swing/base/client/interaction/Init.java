package com.baloise.testautomation.taf.swing.base.client.interaction;

import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.base.db.H2DB;

class Init implements InteractionCommand {

  private static final long serialVersionUID = 1L;

  @Override
  public void run() {
    H2DB.init();
  }

  @Override
  public TafProperties getReturnValue() {
    return null;
  }

  @Override
  public boolean isEquivalent(InteractionCommand otherCommand) {
    return (otherCommand instanceof Init);
  }

  @Override
  public void injectReturnValueInto(InteractionCommand otherCommand) {}
  
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
