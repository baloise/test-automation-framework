package com.baloise.testautomation.taf.base.types;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;

import com.baloise.testautomation.taf.base._interfaces.IType;

public class TafDouble extends TafType implements IType {

  public static TafDouble emptyDouble() {
    TafDouble i = new TafDouble(0);
    i.setIsEmpty(true);
    return i;
  }

  public static TafDouble normalDouble(Double i) {
    if (i != null) {
      return new TafDouble(i);
    }
    return nullDouble();
  }

  public static TafDouble normalDouble(String value) {
    if (value == null) {
      return nullDouble();
    }
    if (value.trim().equalsIgnoreCase(SKIP)) {
      return skipDouble();
    }
    if (value.trim().equalsIgnoreCase(EMPTY)) {
      return emptyDouble();
    }
    if (value.trim().equalsIgnoreCase(NULL)) {
      return nullDouble();
    }
    try {
      Double d = Double.parseDouble(value);
      return normalDouble(d);
    }
    catch (Exception e) {}
    return customOrNullDouble(value);
  }

  public static TafDouble customOrNullDouble(String d) {
    String custom = getCustom(d);
    if (custom != null) {
      TafDouble td = new TafDouble();
      td.setIsCustom(true);
      td.value = custom;
      return td;
    }
    return nullDouble();
  }
  public static TafDouble nullDouble() {
    return new TafDouble((Double)null);
  }

  public static TafDouble parameterDouble(String parameterName) {
    TafDouble result = TafDouble.nullDouble();
    result.setParameterName(parameterName);
    return result;
  }

  public static TafDouble skipDouble() {
    TafDouble i = new TafDouble(0);
    i.setIsSkip(true);
    return i;
  }

  public TafDouble() {
    super();
    value = null;
  }

  public TafDouble(double value) {
    super();
    this.value = value;
  }

  public TafDouble(Double value) {
    super();
    this.value = value;
  }

  public TafDouble(String text) {
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
      value = new Double(n.doubleValue());
    }
    catch (ParseException e) {
      Assert.fail("illegal number format");
      value = null;
    }
  }

  @Override
  public BigDecimal asBigDecimal() {
    Double d = asDouble();
    if (d != null) {
      return BigDecimal.valueOf(d.doubleValue());
    }
    else {
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
    if (isCustom()) {
      return null;
    }
    if (value != null) {
      return (Double)value;
    }
    return null;
  }

  @Override
  public Integer asInteger() {
    if (isCustom()) {
      return null;
    }
    if (value != null) {
      return ((Double)value).intValue();
    }
    return null;
  }

  @Override
  public Long asLong() {
    if (isCustom()) {
      return null;
    }
    if (value != null) {
      return ((Double)value).longValue();
    }
    return null;
  }

  @Override
  public String asString() {
    if (isCustom()) {
      return CUSTOM + value;
    }
    if (isEmpty()) {
      return "";
    }
    return asString("0.0000");
  }

  public String asString(String decimalFormat) {
    if (isCustom()) {
      return null;
    }
    if (value != null) {
      DecimalFormat resultFormat = new DecimalFormat(decimalFormat);
      String formattedString = resultFormat.format(value);
      return formattedString;
    }
    return null;
  }

  @Override
  public void basicSet(String s) {
    value = normalDouble(s).value;
  }

  public void setValue(Double d) {
    value = d;
  }
}
