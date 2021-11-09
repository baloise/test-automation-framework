package com.baloise.testautomation.taf.base._interfaces;

import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.common.interfaces.IFinder;

public interface IComponent extends IFill, ICheck, IElement {

  public boolean canCheck();

  public boolean canFill();
  
  @Override
  public void check();
  
  @Override
  public void fill();

  public IComponent findFirstParent(Class<? extends IComponent> clazz);

  public <T> IFinder<T> getBrowserFinder();

  public TafString getCheck();

  public TafString getFill();

  public <T> IFinder<T> getSwingFinder();

  public boolean isCheckCustom();

  public boolean isFillCustom();

  public void setCheck(String id);

  public void setCheck(TafString id);

  public void setFill(String id);

  public void setFill(TafString id);
  
}
