package com.baloise.testautomation.taf.base._base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCssSelector;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCustom;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ById;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByLeftLabel;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByName;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Check;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.CheckData;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Data;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.DataProvider;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Excel;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.Fill;
import com.baloise.testautomation.taf.base._interfaces.ICheck;
import com.baloise.testautomation.taf.base._interfaces.IComponent;
import com.baloise.testautomation.taf.base._interfaces.IData;
import com.baloise.testautomation.taf.base._interfaces.IDataProvider;
import com.baloise.testautomation.taf.base._interfaces.IDataRow;
import com.baloise.testautomation.taf.base._interfaces.IElement;
import com.baloise.testautomation.taf.base._interfaces.IFill;
import com.baloise.testautomation.taf.base._interfaces.IType;
import com.baloise.testautomation.taf.base.excel.ExcelDataImporter;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.base.types.TafType;
import com.baloise.testautomation.taf.common.interfaces.IFinder;

public abstract class ABase implements IComponent {

  public static Logger logger = LogManager.getLogger("TAF");

  protected TafString fillId = TafString.emptyString();

  protected TafString checkId = TafString.emptyString();

  public IComponent parent = null;
  public String name = "";

  public Annotation by = null;
  public Annotation check = null;

  public ABase() {
    validate();
    initFields();
  }

  public void basicCheck() {
    Field[] fields = getCheckFields();
    for (Field f : fields) {
      try {
        Method m = getCheckMethod(f);
        if (m != null) {
          m.invoke(this);
        }
        else {
          Object o;
          o = f.get(this);
          if (o instanceof ICheck) {
            ((ICheck)o).check();
          }
          else {
            fail("field cannot be checked (beacause ICheck is not implemented): " + f.getName());
          }
        }
      }
      catch (InvocationTargetException e1) {
        fail("error in field-check-method (executed by reflection), " + f.getName() + ": " + e1.getCause().getMessage());
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  public void basicFill() {
    Field[] fields = getFillFields();
    for (Field f : fields) {
      try {
        Method m = getFillMethod(f);
        if (m != null) {
          assertTrue("method must be zero arg: " + m.getName() + " (implemented for " + f.getName() + ")",
              m.getParameterTypes().length == 0);
          m.invoke(this);
        }
        else {
          Object o;
          o = f.get(this);
          if (o instanceof IFill) {
            ((IFill)o).fill();
          }
          else {
            fail("field cannot be filled (beacause IFill is not implemented): " + f.getName());
          }
        }
      }
      catch (InvocationTargetException e1) {
        fail("error in field-fill-method (executed by reflection), " + f.getName() + ": " + e1.getCause().getMessage());
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean canCheck() {
    if (checkId == null) {
      return false;
    }
    return !checkId.isSkip();
  }

  @Override
  public boolean canFill() {
    if (fillId == null) {
      return false;
    }
    return !fillId.isSkip();
  }

  @Override
  public void check() {
    basicCheck();
  }

  @Override
  public void click() {}

  @Override
  public void fill() {
    basicFill();
  }

  @Override
  public IFinder<?> getBrowserFinder() {
    fail("method getBrowserFinder must be overridden (if it used)");
    return null;
  }

  public Annotation getByAnnotation(Field f) {
    Annotation[] annotations = f.getAnnotations();
    for (Annotation annotation : annotations) {
      if (getSupportedBys().contains(annotation.annotationType())) {
        return annotation;
      }
    }
    return null;
  }

  @Override
  public TafString getCheck() {
    return checkId;
  }

  public Annotation getCheckAnnotation(Field f) {
    Annotation[] annotations = f.getAnnotations();
    for (Annotation annotation : annotations) {
      if (Check.class == annotation.annotationType()) {
        return annotation;
      }
    }
    return null;
  }

  public Field[] getCheckDataFields() {
    List<Field> fields = Arrays.asList(getClass().getFields());
    List<Field> result = new ArrayList<Field>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(CheckData.class)) {
        result.add(field);
      }
    }
    return result.toArray(new Field[result.size()]);
  }

  public Field[] getCheckFields() {
    List<Field> fields = Arrays.asList(getClass().getFields());
    List<Field> result = new ArrayList<Field>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Check.class)) {
        result.add(field);
      }
    }
    Collections.sort(result, new Comparator<Field>() {
      @Override
      public int compare(Field f1, Field f2) {
        return new Integer(f1.getAnnotation(Check.class).value()).compareTo(new Integer(f2.getAnnotation(Check.class)
            .value()));
      }
    });
    return result.toArray(new Field[result.size()]);
  }

  public Method getCheckMethod(Field f) {
    return getMethod("check", f);
  }

  public Field[] getDataFields() {
    List<Field> fields = Arrays.asList(getClass().getFields());
    List<Field> result = new ArrayList<Field>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Data.class)) {
        result.add(field);
      }
    }
    return result.toArray(new Field[result.size()]);
  }

  @Override
  public TafString getFill() {
    return fillId;
  }

  public Field[] getFillFields() {
    List<Field> fields = Arrays.asList(getClass().getFields());
    List<Field> result = new ArrayList<Field>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Fill.class)) {
        result.add(field);
      }
    }
    Collections.sort(result, new Comparator<Field>() {
      @Override
      public int compare(Field f1, Field f2) {
        return new Integer(f1.getAnnotation(Fill.class).value()).compareTo(new Integer(f2.getAnnotation(Fill.class)
            .value()));
      }
    });
    return result.toArray(new Field[result.size()]);
  }

  public Method getFillMethod(Field f) {
    return getMethod("fill", f);
  }

  private Method getMethod(String prefix, Field f) {
    for (Method m : getClass().getMethods()) {
      if (m.getName().equalsIgnoreCase(prefix + f.getName())) {
        assertTrue("method must be zero arg: " + m.getName() + " (implemented for " + f.getName() + ")",
            m.getParameterTypes().length == 0);
        return m;
      }
    }
    return null;
  }

  public Collection<Class<?>> getSupportedBys() {
    Vector<Class<?>> bys = new Vector<Class<?>>();
    bys.add(ById.class);
    bys.add(ByText.class);
    bys.add(ByName.class);
    bys.add(ByXpath.class);
    bys.add(ByCssSelector.class);
    bys.add(ByCustom.class);
    bys.add(ByLeftLabel.class);
    return bys;
  }

  @Override
  public IFinder<?> getSwingFinder() {
    fail("method getSwingFinder must be overridden (if it used)");
    return null;
  }

  public void initByFields() {
    Field[] fields = getClass().getFields();
    for (Field f : fields) {
      try {
        Annotation by = getByAnnotation(f);
        if (by != null) {
          Object o = f.getType().newInstance();
          if (o instanceof IElement) {
            IElement e = (IElement)o;
            e.setComponent(this);
            e.setBy(by);
            Annotation check = getCheckAnnotation(f);
            if (check != null) {
              e.setCheck(check);
            }
            e.setName(f.getName());
            f.set(this, e);
          }
          else {
            fail("unsupported object class: " + f.getName() + " --> " + o.getClass());
          }
        }
      }
      catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
        fail("error initializing 'by' fields (must be declared as public): " + f.getName() + " --> " + getClass());
        e.printStackTrace();
      }
    }
  }

  public void initDataFields() {
    Field[] fields = getClass().getFields();
    for (Field f : fields) {
      try {
        if (f.isAnnotationPresent(Data.class)) {
          Object o = f.getType().newInstance();
          f.set(this, o);
        }
      }
      catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
        fail("error initializing data fields (field must be declared as public, and '" + f.getType()
            + "' must have a no-argument constructor): " + f.getName());
        e.printStackTrace();
      }
    }
  }

  public void initFields() {
    initDataFields();
    initByFields();
    initOtherFields();
  }

  public void initOtherFields() {
    Field[] fields = getClass().getFields();
    for (Field f : fields) {
      try {
        Object o = f.get(this);
        if (o == null) {
          o = f.getType().newInstance();
          f.set(this, o);
        }
      }
      catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
        logger.trace("private fields must be initialized in the constructor: " + f.getName() + " --> " + getClass());
      }
    }
  }

  public Collection<IDataRow> loadCheck(String idAndDetail) {
    if (getClass().isAnnotationPresent(Excel.class)) {
      return loadExcel(idAndDetail, "Check.xls");
    }
    if (getClass().isAnnotationPresent(DataProvider.class)) {
      DataProvider dataprovider = getClass().getAnnotation(DataProvider.class);
      switch (dataprovider.value()) {
        case EXCEL:
          return loadExcel(idAndDetail, "Check.xls");
        case CSV:
          return loadCsv(idAndDetail, "Check.csv");
        case SELF:
          if (this instanceof IDataProvider) {
            return ((IDataProvider)this).loadCheckData(idAndDetail);
          }
          fail(this.getClass().getSimpleName()
              + " is marked as dataprovider with type = self,  but does not implement IDataProvider ");
        default:
          fail("loading check data FAILED, unknown dataprovider.type found : " + dataprovider.value());
      }
    }
    fail("loading check data FAILED (either file not found or class annotation wrong/missing: "
        + getClass().getSimpleName());
    return null;
  }

  public Collection<IDataRow> loadCsv(String idAndDetail, String suffix) {
    fail("Csv files not yet supported");
    return null;
  }

  public Collection<IDataRow> loadExcel(String idAndDetail, String suffix) {
    try (InputStream is = ResourceHelper.getResource(this, this.getClass().getSimpleName() + suffix).openStream()) {
      return loadFrom(is, idAndDetail);
    }
    catch (Exception e) {
    }
    fail("excel file with data NOT found for suffix = " + suffix + " --> " + getClass().getSimpleName());
    return null;
  }

  public Collection<IDataRow> loadFill(String idAndDetail) {
    if (getClass().isAnnotationPresent(Excel.class)) {
      return loadExcel(idAndDetail, "Fill.xls");
    }

    if (getClass().isAnnotationPresent(DataProvider.class)) {
      DataProvider dataprovider = getClass().getAnnotation(DataProvider.class);
      switch (dataprovider.value()) {
        case EXCEL:
          return loadExcel(idAndDetail, "Fill.xls");
        case CSV:
          return loadCsv(idAndDetail, "Fill.csv");
        case SELF:
          if (this instanceof IDataProvider) {
            return ((IDataProvider)this).loadFillData(idAndDetail);
          }
          fail(this.getClass().getSimpleName()
              + " is marked as dataprovider with type = self,  but does not implement IDataProvider ");
        default:
          fail("loading fill data FAILED, unknown dataprovider.type found : " + dataprovider.value());
      }
    }
    fail("loading fill data FAILED (either file not found or class annotation wrong/missing: "
        + getClass().getSimpleName());
    return null;
  }

  public Collection<IDataRow> loadFrom(InputStream is, String idAndDetail) {
    ExcelDataImporter edl = new ExcelDataImporter(is, 0);
    Collection<IDataRow> dataRows = edl.getWith(new TafId(idAndDetail));
    return dataRows;
    // Vector<IDataRow> tempData = new Vector<IDataRow>();
    // tempData.addAll(dataRows);
    // return tempData.get(0);
  }

  @Override
  public void setBy(Annotation by) {
    assertNotNull("'by' annotation may NOT be null", by);
    this.by = by;
  }

  @Override
  public void setCheck(Annotation check) {
    assertNotNull("'check' annotation may NOT be null", check);
    this.check = check;
  }

  @Override
  public void setCheck(String id) {
    setCheck(TafString.normalString(id));
  }

  @Override
  public void setCheck(TafString id) {
    checkId = id;
    if (id.isSkip()) {
      return;
    }
    Vector<IDataRow> dataRows = new Vector<>(loadCheck(checkId.asString()));
    assertFalse("too much data found: '" + id + "' --> " + this.getClass(), dataRows.size() > 1);
    assertTrue("no check data found: '" + id + "' --> " + this.getClass(), dataRows.size() == 1);
    IDataRow data = dataRows.firstElement();
    setCheckDataFields(data);
    setCheckFields(data);
  }

  public void setCheckDataFields(IDataRow data) {
    Field[] fields = getCheckDataFields();
    setFields(data, fields);
  }

  public void setCheckFields(IDataRow data) {
    Field[] fields = getCheckFields();
    for (Field f : fields) {
      try {
        Object o = f.get(this);
        if (o instanceof IComponent) {
          assertNotNull("component data NOT found: " + f.getName(), data.get(f.getName()));
          ((IComponent)o).setCheck(data.get(f.getName()).asTafString());
        }
        if (o instanceof IData) {
          assertNotNull("field data NOT found: " + f.getName(), data.get(f.getName()));
          IType t = data.get(f.getName());
          assertNotNull("field data NOT found: " + f.getName(), t);
          if (t.isSkip()) {
            setElementCheck(TafType.SKIP, (IData<?>)o);
          }
          else {
            setElementCheck(data.get(f.getName()).asString(), (IData<?>)o);
          }
        }
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setComponent(IComponent parent) {
    this.parent = parent;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void setElementCheck(String fieldContents, IData<?> o) {
    if (o instanceof IData) {
      try {
        IType t;
        t = ((IData<?>)o).getDataTypeClass().newInstance();
        t.set(fieldContents);
        ((IData)o).setCheck(t);
      }
      catch (InstantiationException | IllegalAccessException e) {
        fail("setElementCheck NOT successful");
      }
      return;
    }
    fail("element type not supported: " + o.getClass());
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void setElementFill(String fieldContents, IData<?> o) {
    if (o instanceof IData) {
      try {
        IType t;
        t = ((IData<?>)o).getDataTypeClass().newInstance();
        t.set(fieldContents);
        ((IData)o).setFill(t);
      }
      catch (InstantiationException | IllegalAccessException e) {
        fail("setElementFill NOT successful, may the no-argument constructor is missing for datatype of: "
            + o.getClass());
      }
      return;
    }
    fail("element type not supported: " + o.getClass());
  }

  private void setFields(IDataRow data, Field[] fields) {
    for (Field f : fields) {
      try {
        Object o = f.get(this);
        if (o instanceof IType) {
          assertNotNull("data not found: " + f.getName(), data.get(f.getName()));
          if (data.get(f.getName()).isSkip()) {
            ((IType)o).set(TafType.SKIP);
          }
          else {
            ((IType)o).set(data.get(f.getName()).asString());
          }
        }
        else {
          fail("data annotated with @Data or @CheckData must be an instance of IType: " + f.getName() + " --> "
              + getClass());
        }
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void setFill(String id) {
    setFill(TafString.normalString(id));
  }

  @Override
  public void setFill(TafString id) {
    fillId = id;
    if (id.isSkip()) {
      return;
    }
    Vector<IDataRow> dataRows = new Vector<>(loadFill(fillId.asString()));
    assertFalse("too much data found: '" + id + "' --> " + this.getClass(), dataRows.size() > 1);
    assertTrue("no fill data found: '" + id + "' --> " + this.getClass(), dataRows.size() == 1);
    IDataRow data = dataRows.firstElement();
    setFillDataFields(data);
    setFillFields(data);
  }

  public void setFillDataFields(IDataRow data) {
    Field[] fields = getDataFields();
    setFields(data, fields);
  }

  public void setFillFields(IDataRow data) {
    Field[] fields = getFillFields();
    for (Field f : fields) {
      try {
        Object o = f.get(this);
        if (o instanceof IComponent) {
          assertNotNull("component data NOT found: " + f.getName() + " --> " + getClass(), data.get(f.getName()));
          ((IComponent)o).setFill(data.get(f.getName()).asTafString());
        }
        if (o instanceof IData) {
          assertNotNull("field data NOT found: " + f.getName(), data.get(f.getName()));
          IType t = data.get(f.getName());
          assertNotNull("field data NOT found: " + f.getName(), t);
          if (t.isSkip()) {
            setElementFill(TafType.SKIP, (IData<?>)o);
          }
          else {
            setElementFill(data.get(f.getName()).asString(), (IData<?>)o);
          }
        }
      }
      catch (IllegalArgumentException | IllegalAccessException e) {
        fail("instantiating field has FAILED (must be declared as public): " + f.getName() + " --> " + getClass());
      }
    }
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void validate() {
    Field[] fields = getClass().getDeclaredFields();
    for (Field f : fields) {
      if (f.isAnnotationPresent(Check.class) || f.isAnnotationPresent(Fill.class) || f.isAnnotationPresent(Data.class)) {
        if (!Modifier.isPublic(f.getModifiers())) {
          fail("Fill, Check or Data-annotated field must be declared as public: " + f.getName() + " --> " + getClass());
        }
      }
    }
  }

}
