package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JTextField;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwInput;


public class SwInput extends ASwTextComponent implements ISwInput<Component> {

  public SwInput(long tid, JTextField c) {
    super(tid, c);
  }

  @Override
  public String getType() {
    return ISwInput.type;
  }

}
