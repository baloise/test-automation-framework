package com.baloise.testautomation.taf.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DecimalFormatSymbols;

import org.junit.Test;

import com.baloise.testautomation.taf.base.types.TafBoolean;
import com.baloise.testautomation.taf.base.types.TafComboString;
import com.baloise.testautomation.taf.base.types.TafDouble;
import com.baloise.testautomation.taf.base.types.TafInteger;
import com.baloise.testautomation.taf.base.types.TafString;

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
    assertEquals(null, cs.asString());
  }

  @Test
  public void tafInteger() {
    TafInteger i = TafInteger.normalInteger(111);
    assertEquals(new Integer(111), i.asTafInteger().asInteger());
    assertEquals("111", i.asTafString().asString());
    assertEquals(new Double(111.0), i.asTafDouble().asDouble());
    assertTrue(i.asTafDate().isNull());
    assertTrue(i.asTafBoolean().isNull());
    i = TafInteger.skipInteger();
    assertTrue(i.asTafString().isSkip());
    assertTrue(i.asTafDate().isSkip());
    assertTrue(i.asTafDouble().isSkip());
    assertTrue(i.asTafInteger().isSkip());
    i = new TafInteger("124");
    assertEquals(i.asInteger(), new Integer(124));
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() + "49");
    assertEquals(new Integer(124), i.asInteger());
    i = new TafInteger("124" + DecimalFormatSymbols.getInstance().getDecimalSeparator() +"51");
    assertEquals(new Integer(125), i.asInteger());
    try {
      i = new TafInteger("abc");
      fail();
    }
    catch (Throwable e) {}
    i = TafInteger.normalInteger("{custom}{test}");
    assertEquals(null, i.asInteger());
    assertEquals(null, i.asLong());
    assertEquals("{custom}{test}", i.asString());
    assertEquals(true, i.isCustom());
    assertEquals(true, i.asTafInteger().isCustom());
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
    assertEquals(null, s.asString());
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
    assertEquals(new Double(2.2), d.asDouble());
    assertEquals(new Integer(2), d.asInteger());
    d = TafDouble.normalDouble(2.6);
    assertEquals(new Integer(2), d.asInteger());
    d = TafDouble.normalDouble("{custom}{test}");
    assertEquals("{custom}{test}", d.asString());
    assertEquals("{test}", d.getCustom());
    assertEquals("{custom}{test}", d.asTafString().asString());
  }
  
  @Test
  public void tafBoolean() {
    TafBoolean b = TafBoolean.normalBoolean("{true}");
    assertEquals(true, b.asBoolean());
    assertEquals(true, b.asPrimitiveBoolean());
    b = TafBoolean.normalBoolean("{false}");
    assertEquals(false, b.asBoolean());
    assertEquals(false, b.asPrimitiveBoolean());
    b = TafBoolean.normalBoolean("{null}");
    assertEquals(null, b.asBoolean());
    b = TafBoolean.normalBoolean("{custom}{test}");
    assertEquals(true, b.isCustom());
    assertEquals(null, b.asBoolean());
    assertEquals("{custom}{test}", b.asString());
    assertEquals("{test}", b.getCustom());
    assertEquals("{test}", b.asTafString().getCustom());
  }
}
