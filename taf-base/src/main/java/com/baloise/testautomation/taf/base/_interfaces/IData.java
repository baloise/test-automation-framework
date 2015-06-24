package com.baloise.testautomation.taf.base._interfaces;

public interface IData<DataType extends IType> extends IFill, ICheck {

  public boolean canCheck();

  public boolean canFill();

  public DataType get();

  public Class<DataType> getDataTypeClass();

  public void setCheck(DataType value);

  public void setCheck(String value);

  public void setFill(DataType value);

  public void setFill(String value);

}
