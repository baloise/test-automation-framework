package com.baloise.testautomation.taf.base._interfaces;

import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.common.interfaces.IFinder;
import com.baloise.testautomation.taf.common.interfaces.ISwApplication;

public interface IComponent extends IFill, ICheck, IElement {

  public boolean canCheck();

  public boolean canFill();

  @Override
  public void check();

  @Override
  public void fill();

  public IFinder<?> getBrowserFinder();

  public TafString getCheck();

  public TafString getFill();

  public IFinder<?> getSwingFinder();

  public void setCheck(String id);

  public void setCheck(TafString id);

  public void setFill(String id);

  public void setFill(TafString id);

}
