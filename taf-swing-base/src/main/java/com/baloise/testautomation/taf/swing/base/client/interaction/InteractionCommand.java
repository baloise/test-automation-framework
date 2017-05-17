package com.baloise.testautomation.taf.swing.base.client.interaction;

import java.io.Serializable;
import com.baloise.testautomation.taf.common.utils.TafProperties;

interface InteractionCommand extends Serializable {

  void run();

  TafProperties getReturnValue();

  boolean isEquivalent(InteractionCommand otherCommand);

  void injectReturnValueInto(InteractionCommand otherCommand);

}
