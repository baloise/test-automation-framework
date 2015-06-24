package com.baloise.testautomation.taf.base.robot;

import static org.junit.Assert.assertNotNull;

import org.sikuli.script.Region;

import com.baloise.testautomation.taf.base._base.AButton;

public class RoButton extends AButton {

  private Region r = null;

  public RoButton(Region r) {
    this(r, true);
  }

  public RoButton(Region r, boolean isEnabled) {
    assertNotNull(r);
    this.r = r;
  }

  @Override
  public void click() {
    r.click();
  }

//  @Override
  public Region find() {
    // TODO
    return null; //roFind();
  }

}
