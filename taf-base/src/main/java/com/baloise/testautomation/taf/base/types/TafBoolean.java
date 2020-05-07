package com.baloise.testautomation.taf.base.types;

import java.math.BigDecimal;
import java.util.Date;

public class TafBoolean extends TafType {

  public static final String TRUE = "{true}";
  public static final String FALSE = "{false}";

  public static TafBoolean emptyBoolean() {
    TafBoolean i = new TafBoolean(null);
    i.setIsEmpty(true);
    return i;
  }

  public static TafBoolean falseBoolean() {
    return normalBoolean(false);
  }

  public static TafBoolean normalBoolean(Boolean b) {
    if (b != null) {
      return new TafBoolean(b);
    }
    return nullBoolean();
  }

  public static TafBoolean normalBoolean(String value) {
    if (value == null) {
      return nullBoolean();
    }
    if (value.trim().equalsIgnoreCase(SKIP)) {
      return skipBoolean();
    }
    if (value.trim().equalsIgnoreCase(EMPTY)) {
      return emptyBoolean();
    }
    if (value.trim().equalsIgnoreCase(NULL)) {
      return nullBoolean();
    }
    if (value.trim().equalsIgnoreCase(TRUE)) {
      return normalBoolean(true);
    }
    if (value.trim().equalsIgnoreCase(FALSE)) {
      return normalBoolean(false);
    }
    return customOrNullBoolean(getCustom(value));
  }

  public static TafBoolean customOrNullBoolean(String value) {
    if (value == null) {
      return nullBoolean();
    }
    TafBoolean tafBoolean = new TafBoolean();
    tafBoolean.value = value;
    tafBoolean.setIsCustom(true);
    return tafBoolean;
  }

  public static TafBoolean nullBoolean() {
    return new TafBoolean(null);
  }

  public static TafBoolean parameterBoolean(String parameterName) {
    TafBoolean result = TafBoolean.nullBoolean();
    result.setParameterName(parameterName);
    return result;
  }

  public static TafBoolean skipBoolean() {
    TafBoolean i = new TafBoolean(null);
    i.setIsSkip(true);
    return i;
  }

  public static TafBoolean trueBoolean() {
    return normalBoolean(true);
  }

  public TafBoolean() {
    this(null);
  }

  public TafBoolean(Boolean value) {
    super();
    this.value = value;
  }

  @Override
  public BigDecimal asBigDecimal() {
    Long l = asLong();
    if (l != null) {
      return BigDecimal.valueOf(l);
    }
    else {
      return null;
    }
  }

  private boolean isNotBoolean() {
    return isNull() | isEmpty() | isSkip() | isCustom();
  }

  @Override
  public Boolean asBoolean() {
    if (isNotBoolean()) {
      return null;
    }
    return (Boolean)value;
  }

  @Override
  public Date asDate() {
    return null;
  }

  @Override
  public Double asDouble() {
    try {
      return Double.parseDouble(String.valueOf(asInteger()));
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public Integer asInteger() {
    if (isNotBoolean()) {
      return null;
    }
    if ((Boolean) value) {
      return 1;
    }
    return 0;
  }

  @Override
  public Long asLong() {
    try {
      return Long.parseLong(String.valueOf(asInteger()));
    }
    catch (Exception e) {
      return null;
    }
  }

  public boolean asPrimitiveBoolean() {
    Boolean result = asBoolean();
    if (result == null) {
      return false;
    }
    return result;
  }

  @Override
  public String getCustom() {
    if (value == null) {
      return null;
    }
    if (isCustom()) {
      return value.toString();
    }
    return null;
  }

  @Override
  public String asString() {
    if (isCustom()) {
      if (value != null) {
        return CUSTOM + value.toString();
      }
      else {
        return CUSTOM;
      }
    }
    if (isNotBoolean()) {
      return null;
    }
    if ((Boolean) value) {
      return TRUE;
    }
    return FALSE;
  }

  @Override
  public void basicSet(String s) {
    value = normalBoolean(s).value;
  }

  public boolean isFalse() {
    return !isTrue();
  }

  public boolean isTrue() {
    if (isNull() | isEmpty() | isSkip() | isCustom()) {
      return false;
    }
    return (Boolean)value;
  }

  public void setValue(Boolean b) {
    value = b;
  }

}
