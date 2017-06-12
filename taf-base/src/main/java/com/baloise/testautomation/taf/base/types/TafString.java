package com.baloise.testautomation.taf.base.types;

import java.math.BigDecimal;
import java.util.Date;

import com.baloise.testautomation.taf.base._interfaces.IType;

public class TafString extends TafType implements IType {

  public static TafString emptyString() {
    return new TafString("");
  }

  public static TafString normalString(String s) {
    if (s == null) {
      return nullString();
    }
    if (s.trim().equalsIgnoreCase(EMPTY)) {
      return emptyString();
    }
    if (s.trim().equalsIgnoreCase(SKIP)) {
      return skipString();
    }
    if (s.trim().equalsIgnoreCase(NULL)) {
      return nullString();
    }
    return new TafString(s);
  }

  public static TafString nullString() {
    return new TafString(null);
  }

  public static TafString parameterString(String parameterName) {
    TafString result = TafString.nullString();
    result.setParameterName(parameterName);
    return result;
  }

  public static TafString parameterString(String parameterName, String text) {
    TafString result = TafString.normalString(text);
    result.setParameterName(parameterName);
    return result;
  }

  public static TafString skipString() {
    TafString s = new TafString(null);
    s.setIsSkip(true);
    return s;
  }

  public TafString() {
    this("");
  }

  public TafString(String s) {
    super();
    setIsSkip(false);
    if (s == null) {
      setIsEmpty(false);
    }
    else {
      if (s.equals("")) {
        setIsEmpty(true);
      }
      else {
        setIsEmpty(false);
      }
    }
    if (SKIP.equalsIgnoreCase(s)) {
      s = null;
      setIsSkip(true);
    }
    value = s;
  }

  @Override
  public BigDecimal asBigDecimal() {
    return null;
  }

  @Override
  public Boolean asBoolean() {
    TafBoolean b = TafBoolean.normalBoolean(asString());
    if (b.isEmpty() || b.isSkip() || b.isNull()) {
      return null;
    }
    if (b.isNotNull()) {
      return b.asBoolean();
    }
    return null;
  }

  @Override
  public Date asDate() {
    TafDate d = TafDate.normalDate(asString());
    if (d.isEmpty() || d.isSkip() || d.isNull()) {
      return null;
    }
    if (d.isNotNull()) {
      return d.asDate();
    }
    return null;
  }

  @Override
  public Double asDouble() {
    try {
      return Double.parseDouble((String)value);
    }
    catch (Exception e) {}
    return null;
  }

  @Override
  public Integer asInteger() {
    try {
      return Integer.parseInt((String)value);
    }
    catch (Exception e) {}
    return null;
  }

  @Override
  public Long asLong() {
    try {
      return Long.parseLong((String)value);
    }
    catch (Exception e) {}
    return null;
  }

  @Override
  public String asString() {
    return (String)value;
  }

  @Override
  public void basicSet(String s) {
    value = normalString(s).value;
    isEmpty = "".equals(s);
  }

  public void setValue(String s) {
    value = s;
  }

}
