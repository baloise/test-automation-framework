package com.baloise.testautomation.taf.base;

import java.text.DecimalFormatSymbols;

import org.junit.Test;

import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafComboString;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

import static org.junit.Assert.*;

public class TestTafTypes {

  @Test
  public void tafComboString() {
    TafComboString cs = TafComboString.normalString("{true}");
    assertEquals("{true}", cs.asString());
    assertTrue(cs.asTafBoolean().asBoolean());
    assertTrue(cs.asTafInteger().isNull());
    assertTrue(cs.asTafDouble().isNull());
    assertTrue(cs.asTafDate().isNull());
    cs = TafComboString.normalString("{Skip}");
    assertTrue(cs.isSkip());
    assertNull(cs.asString());
  }

  @Test
  public void tafInteger() {
    TafInteger i = TafInteger.normalInteger(111);
    assertEquals(Integer.valueOf(111), i.asTafInteger().asInteger());
    assertEquals("111", i.asTafString().asString());
    assertEquals(Double.valueOf(111.0), i.asTafDouble().asDouble());
    assertTrue(i.asTafDate().isNull());
    assertTrue(i.asTafBoolean().isNull());
    i = TafInteger.skipInteger();
    assertTrue(i.asTafString().isSkip());
    assertTrue(i.asTafDate().isSkip());
    assertTrue(i.asTafDouble().isSkip());
    assertTrue(i.asTafInteger().isSkip());
    i = new TafInteger("124");
    assertEquals(Integer.valueOf(124), i.asInteger());
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() + "49");
    assertEquals(Integer.valueOf(124), i.asInteger());
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() +"51");
    assertEquals(Integer.valueOf(125), i.asInteger());
    try {
      new TafInteger("abc");
      fail();
    }
    catch (Throwable e) {
      // ignore exception
    }
    i = TafInteger.normalInteger("{custom}{test}");
    assertNull(i.asInteger());
    assertNull(i.asLong());
    assertEquals("{custom}{test}", i.asString());
    assertTrue(i.isCustom());
    assertTrue(i.asTafInteger().isCustom());
    assertEquals("{custom}{test}", i.asTafString().asString());
  }

  @Test
  public void tafString() {
    TafString s = TafString.normalString("{true}");
    assertEquals("{true}", s.asString());
    assertTrue(s.asTafBoolean().asBoolean());
    assertTrue(s.asTafInteger().isNull());
    assertTrue(s.asTafDouble().isNull());
    assertTrue(s.asTafDate().isNull());
    s = TafString.normalString("21.02.2000");
    assertTrue(s.asTafInteger().isNull());
    assertTrue(s.asTafDouble().isNull());
    assertEquals("21.02.2000", s.asTafDate().asString());
    s = TafString.skipString();
    assertTrue(s.asTafString().isSkip());
    assertTrue(s.asTafDate().isSkip());
    assertTrue(s.asTafDouble().isSkip());
    assertTrue(s.asTafInteger().isSkip());
    s = new TafString("{sKip}");
    assertTrue(s.isSkip());
    assertNull(s.asString());
    s = TafString.normalString("{custom}{test}");
    assertEquals("{test}", s.getCustom());
    assertEquals("{custom}{test}", s.asString());
    assertEquals("{test}", s.asTafString().getCustom());
    s = TafString.normalString("{custom}");
    assertEquals("", s.getCustom());
    assertEquals("{custom}", s.asString());
  }
  
  @Test
  public void tafDouble() {
    TafDouble d = TafDouble.normalDouble(2.2);
    assertEquals(Double.valueOf(2.2), d.asDouble());
    assertEquals(Integer.valueOf(2), d.asInteger());
    d = TafDouble.normalDouble(2.6);
    assertEquals(Integer.valueOf(2), d.asInteger());
    d = TafDouble.normalDouble("{custom}{test}");
    assertEquals("{custom}{test}", d.asString());
    assertEquals("{test}", d.getCustom());
    assertEquals("{custom}{test}", d.asTafString().asString());
  }
  
  @Test
  public void tafBoolean() {
    TafBoolean b = TafBoolean.normalBoolean("{true}");
    assertEquals(true, b.asBoolean());
    assertTrue(b.asPrimitiveBoolean());
    b = TafBoolean.normalBoolean("{false}");
    assertEquals(false, b.asBoolean());
    assertFalse(b.asPrimitiveBoolean());
    b = TafBoolean.normalBoolean("{null}");
    assertNull(b.asBoolean());
    b = TafBoolean.normalBoolean("{custom}{test}");
    assertTrue(b.isCustom());
    assertNull(b.asBoolean());
    assertEquals("{custom}{test}", b.asString());
    assertEquals("{test}", b.getCustom());
    assertEquals("{test}", b.asTafString().getCustom());
  }
}
