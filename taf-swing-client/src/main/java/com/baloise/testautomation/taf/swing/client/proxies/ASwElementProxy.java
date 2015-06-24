/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.client.proxies;

import org.junit.Assert;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;

/**
 * 
 */
public abstract class ASwElementProxy implements ISwElement<Long> {

  protected ISwApplication application = null;
  
  protected Long tid = null;
  
  @Override
  public Long getReference() {
    return tid;
  }

  @Override
  public void setApplication(ISwApplication application) {
    this.application = application;
  }

  protected TafProperties executeCommand(String command) {
    return executeCommand(command, new TafProperties());
  }

  protected TafProperties executeCommand(String command, TafProperties props) {
    if (props == null) {
      props = new TafProperties();
    }
    props.putObject("tid", getReference());

    if (application != null) {
      return application.execCommand(getType(), command, props);
    } else {
    }
    Assert.fail("client is not set --> use ASWClientElement.setClient(yourClient)");
    return null;
  }
  
  @Override
  public void setReference(Long tid) {
    this.tid = tid;
  }

  @Override
  public TafProperties getProperties() {
    // TODO
    return null;
  }

  @Override
  public void setProperties(TafProperties props) {
    //TODO
  }
}
