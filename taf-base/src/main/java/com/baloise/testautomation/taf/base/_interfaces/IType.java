package com.baloise.testautomation.taf.base._interfaces;

import java.math.BigDecimal;
import java.util.Date;

import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafDate;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

public interface IType {

  public BigDecimal asBigDecimal();

  public Boolean asBoolean();

  public Date asDate();

  public Double asDouble();

  public Integer asInteger();

  public Long asLong();

  public String asString();

  public TafBoolean asTafBoolean();

  public TafDate asTafDate();

  public TafDouble asTafDouble();

  public TafInteger asTafInteger();

  public TafString asTafString();

  public String getParameterName();

  public boolean isEmpty();

  public boolean isNotNull();

  public boolean isNull();

  public boolean isParameter();

  public boolean isSkip();
  
  public boolean isCustom();

  public void set(String s);

  public void setIsEmpty(boolean isEmpty);

  public void setIsSkip(boolean isEmpty);

  public void setParameterName(String parameterName);

  public String getCustom();

}
