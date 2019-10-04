package com.baloise.testautomation.taf.base.types;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import com.baloise.testautomation.taf.base._interfaces.IType;

import static com.baloise.testautomation.taf.base._base.TafAssert.fail;

public class TafInteger extends TafType implements IType {

  public static TafInteger emptyInteger() {
    TafInteger i = TafInteger.nullInteger();
    i.setIsEmpty(true);
    return i;
  }

  public static TafInteger normalInteger(Integer i) {
    if (i != null) {
      return new TafInteger(i);
    }
    return nullInteger();
  }

  public static TafInteger normalInteger(String value) {
    if (value == null) {
      return nullInteger();
    }
    if (value.trim().isEmpty()) {
      return emptyInteger();
    }
    if (value.trim().equalsIgnoreCase(EMPTY)) {
      return emptyInteger();
    }
    if (value.trim().equalsIgnoreCase(SKIP)) {
      return skipInteger();
    }
    if (value.trim().equalsIgnoreCase(NULL)) {
      return nullInteger();
    }
    try {
      Integer i = Integer.parseInt(value);
      return normalInteger(i);
    }
    catch (Exception e) {}
    return customOrNullInteger(value);
  }

  private static TafInteger customOrNullInteger(String value) {
    String custom = getCustom(value);
    if (custom != null) {
      TafInteger ti = new TafInteger();
      ti.setIsCustom(true);
      ti.value = custom;
      return ti;
    }
    return nullInteger();
  }

  public static TafInteger nullInteger() {
    return new TafInteger((Integer)null);
  }

  public static TafInteger parameterInteger(String parameterName) {
    TafInteger result = TafInteger.nullInteger();
    result.setParameterName(parameterName);
    return result;
  }

  public static TafInteger skipInteger() {
    TafInteger i = TafInteger.nullInteger();
    i.setIsSkip(true);
    return i;
  }

  public TafInteger() {
    super();
    this.value = null;
  }

  public TafInteger(int value) {
    super();
    this.value = value;
  }

  public TafInteger(Integer value) {
    super();
    this.value = value;
  }

  public TafInteger(String text) {
    super();
    if (text == null) {
      value = null;
      return;
    }
    if (text.isEmpty()) {
      value = null;
      setIsEmpty(true);
      return;
    }
    if (SKIP.equalsIgnoreCase(text)) {
      value = null;
      setIsSkip(true);
    }
    NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
    Number n;
    try {
      n = format.parse(text);
      value = new Integer(Math.round(n.floatValue()));
    }
    catch (ParseException e) {
      fail("illegal number format");
      value = null;
    }
  }

  @Override
  public BigDecimal asBigDecimal() {
    try {
      return BigDecimal.valueOf(asLong());
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public Boolean asBoolean() {
    return null;
  }

  @Override
  public Date asDate() {
    return null;
  }

  @Override
  public Double asDouble() {
    if (!isNull()) {
      return new Double((Integer)value);
    }
    return null;
  }

  @Override
  public Integer asInteger() {
    if (isCustom()) {
      return null;
    }
    if (!isNull()) {
      return (Integer)value;
    }
    return null;
  }

  @Override
  public Long asLong() {
    try {
      return new Long(asInteger());
    }
    catch (Exception e) {
      return null;
    }
  }

  @Override
  public String asString() {
    if (isEmpty()) {
      return "";
    }
    if (isSkip()) {
      return null;
    }
    if (!isNull()) {
      if (isCustom()) {
        return CUSTOM + value;
      }
      return ((Integer)value).toString();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void basicSet(String s) {
    value = normalInteger(s).value;
  }

  public void setValue(Integer i) {
    value = i;
  }
}
