/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.base._interfaces;

/**
 * 
 */
public interface ICheckbox extends IElement {

  public boolean isSelected();

  public boolean isUnselected();

  public void select();

  public void select(boolean selection);

  public void unselect();

}
