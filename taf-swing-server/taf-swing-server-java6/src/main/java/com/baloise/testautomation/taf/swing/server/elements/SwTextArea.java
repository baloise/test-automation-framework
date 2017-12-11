/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.elements;

import java.awt.Component;

import javax.swing.JTextArea;

import com.baloise.testautomation.taf.swing.base._interfaces.ISwTextArea;

/**
 * 
 */
public class SwTextArea extends ASwTextComponent implements ISwTextArea<Component> {

  public SwTextArea(long tid, JTextArea c) {
    super(tid, c);
  }

  @Override
  public String getType() {
    return ISwTextArea.type;
  }

  
}
