package com.baloise.testautomation.taf.swing.client.proxies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;

import static com.baloise.testautomation.taf.base._base.TafAssert.fail;

public abstract class ASwElementProxy implements ISwElement<Long> {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected ISwApplication<?> application = null;

  protected Long tid = null;

  protected TafProperties executeCommand(String command) {
    return executeCommand(command, new TafProperties());
  }

  protected final TafProperties executeCommand(String command, TafProperties props) {
    if (application == null) {
      fail("client is not set --> use ASwWElementElement.setApplication(yourApplication)");
    }
    
    if (props == null) {
      props = new TafProperties();
    }
    props.putObject("tid", getReference());
    props.putObject("command", command);
    props.putObject("type", getType());
    
    logger.debug("Executing command with properties: " + props.toString());   
    return application.execCommand(props); 
  }

  @Override
  public TafProperties getProperties() {
    // TODO
    return null;
  }

  @Override
  public Long getReference() {
    return tid;
  }

  @Override
  public void setApplication(ISwApplication<?> application) {
    this.application = application;
  }

  @Override
  public void setProperties(TafProperties props) {
    // TODO
  }

  @Override
  public void setReference(Long tid) {
    this.tid = tid;
  }
}
