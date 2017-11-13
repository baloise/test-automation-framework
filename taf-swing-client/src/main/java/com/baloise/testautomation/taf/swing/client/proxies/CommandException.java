package com.baloise.testautomation.taf.swing.client.proxies;

import com.baloise.testautomation.taf.common.utils.TafProperties;

public class CommandException extends RuntimeException {

  public CommandException(TafProperties returnProperties) {
    super("Command encountered an error: " + returnProperties.getString("message"));
  }
}
