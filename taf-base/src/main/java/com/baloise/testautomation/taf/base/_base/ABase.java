package com.baloise.testautomation.taf.base._base;

import com.baloise.testautomation.taf.base._interfaces.*;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.*;
import com.baloise.testautomation.taf.base.csv.CsvDataImporter;
import com.baloise.testautomation.taf.base.excel.ExcelDataImporter;
import com.baloise.testautomation.taf.base.types.TafId;
import com.baloise.testautomation.taf.base.types.TafString;
import com.baloise.testautomation.taf.base.types.TafType;
import com.baloise.testautomation.taf.common.interfaces.IFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static com.baloise.testautomation.taf.base._base.TafAssert.*;

public abstract class ABase implements IComponent {

  public static Logger logger = LoggerFactory.getLogger("TAF");

  private static final String separator = "|";

  protected TafString fillId = TafString.emptyString();

  protected TafString checkId = TafString.emptyString();
  public IComponent parent = null;

  public String name = "";
  public Annotation by = null;

  public Annotation check = null;

  public ABase() {
    initFields();
  }

  public void basicCheck() {
    List<Field> fields = getCheckFields();
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
      catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  public void basicFill() {
    List<Field> fields = getFillFields();
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
      catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean canCheck() {
    if (checkId == null) {
      return false;
    }
    return !(checkId.isSkip() || checkId.isEmpty());
  }

  @Override
  public boolean canFill() {
    if (fillId == null) {
      return false;
    }
    return !(fillId.isSkip() || fillId.isEmpty());
  }

  @Override
  public void check() {
    if (!canCheck()) {
      logger.warn(this.getClass() + ": could not execeute check method, because checkId is either {skip} or {empty}");
      return;
    }
    if (isCheckCustom()) {
      checkCustom();
      return;
    }
    basicCheck();
  }

  public void checkCustom() {
    fail("Must override method 'checkCustom()'");
  }

  @Override
  public void click() {}

  @Override
  public void fill() {
    if (!canFill()) {
      logger.warn(this.getClass() + ": could not execeute fill method, because fillId is either {skip} or {empty}");
      return;
    }
    if (isFillCustom()) {
      fillCustom();
      return;
    }
    basicFill();
  }

  public void fillCustom() {
    fail("Must override method 'fillCustom()'");
  }

  public IComponent findFirstParent(Class<? extends IComponent> clazz) {
    if (parent == null) {
      return null;
    }
    if (clazz.isInstance(parent)) {
      return parent;
    }
    else {
      return parent.findFirstParent(clazz);
    }
  }

  private List<Field> getAllFields() {
    List<Field> allFields = new ArrayList<>();
    Class<?> currentClass = getClass();
    while (currentClass != null) {
      Field[] declaredFields = currentClass.getDeclaredFields();
      for (Field field : declaredFields) {
        if (!field.isAnnotationPresent(PreserveNull.class)) {
          allFields.add(field);
        }
      }
      currentClass = currentClass.getSuperclass();
    }
    return allFields;
  }

  @Override
  public IFinder<?> getBrowserFinder() {
    fail("method getBrowserFinder must be overridden (if it used)");
    return null;
  }

  @Override
  public Annotation getBy() {
    return this.by;
  }

  public Annotation getByAnnotation(Field f) {
    Annotation[] annotations = f.getAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().isAnnotationPresent(IAnnotations.By.class)) {
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

  public List<Field> getCheckDataFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
    List<Field> result = new ArrayList<>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(CheckData.class)) {
        result.add(field);
      }
    }
    return result;
  }

  public List<Field> getCheckFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
    List<Field> result = new ArrayList<>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Check.class)) {
        result.add(field);
      }
    }
    result.sort(Comparator.comparingInt(f -> f.getAnnotation(Check.class).value()));
    return result;
  }

  public Method getCheckMethod(Field f) {
    return getMethod("check", f);
  }

  public List<Field> getDataFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
    List<Field> result = new ArrayList<>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Data.class)) {
        result.add(field);
      }
    }
    return result;
  }

  private Class<?> getDataProviderClass() {
    Class<?> klass = this.getClass();
    boolean found = false;
    while (!found) {
      if (klass.isAnnotationPresent(Excel.class) || klass.isAnnotationPresent(DataProvider.class)) {
        logger.info("Annotated dataprovider class found: " + klass);
        return klass;
      }
      else {
        klass = klass.getSuperclass();
      }
      if (klass == null) {
        logger.warn("NO annotated dataprovider class found!");
        return this.getClass();
      }
    }
    return klass;
  }

  private String getFilename(Class<?> klass, String qualifierIdAndDetail, String suffix, String extension) {
    return klass.getSimpleName() + suffix + getQualifier(qualifierIdAndDetail) + extension;
  }

  @Override
  public TafString getFill() {
    return fillId;
  }

  public List<Field> getFillFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
    List<Field> result = new ArrayList<>();
    for (Field field : fields) {
      if (field.isAnnotationPresent(Fill.class)) {
        result.add(field);
      }
    }
    result.sort(Comparator.comparingInt(f -> f.getAnnotation(Fill.class).value()));
    return result;
  }

  public Method getFillMethod(Field f) {
    return getMethod("fill", f);
  }

  private String getIdAndDetail(String qualifierAndIdAndDetail) {
    String[] parts = qualifierAndIdAndDetail.split("\\" + separator);
    if (parts.length == 0) {
      return "";
    }
    return parts[parts.length - 1];
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

  private String getQualifier(String qualifierAndIdAndDetail) {
    String[] parts = qualifierAndIdAndDetail.split("\\" + separator);
    if (parts.length > 1) {
      return "-" + parts[0];
    }
    return "";
  }

  @Override
  public IFinder<?> getSwingFinder() {
    fail("method getSwingFinder must be overridden (if it is used)");
    return null;
  }

  public void initByFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
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
        e.printStackTrace();
        fail("error initializing 'by' fields (must be declared as public and need an no-arg-constructor): "
                + f.getName() + " --> " + getClass());
      }
    }
  }

  public void initDataFields() {
    List<Field> fields = makeFieldsAccessible(getAllFields());
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
    List<Field> fields = makeFieldsAccessible(getAllFields());
    for (Field f : fields) {
      if (!f.isAnnotationPresent(PreserveNull.class) && !isFieldRuleAnnotated(f)) {
        try {
          Object o = f.get(this);
          if (o == null) {
            o = f.getType().newInstance();
            f.set(this, o);
          }
          if (o instanceof ABase) {
            if (!f.getName().equalsIgnoreCase("parent")) {
              ((ABase) o).setComponent(this);
            }
          }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
          logger.trace("private fields must be initialized in the constructor: " + f.getName() + " --> " + getClass());
        }
      }
    }
  }

  private boolean isFieldRuleAnnotated(Field f) {
    for (Annotation annotation : f.getAnnotations()) {
      if (annotation.annotationType().getName().equals("org.junit.Rule")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isCheckCustom() {
    if (checkId == null) {
      return false;
    }
    return checkId.isCustom();
  }

  @Override
  public boolean isFillCustom() {
    if (fillId == null) {
      return false;
    }
    return fillId.isCustom();
  }

  public Collection<IDataRow> loadCheck(String idAndDetail) {
    Class<?> dataProviderClass = getDataProviderClass();
    if (dataProviderClass.isAnnotationPresent(Excel.class)) {
      return loadExcel(dataProviderClass, idAndDetail, "Check");
    }
    if (dataProviderClass.isAnnotationPresent(DataProvider.class)) {
      DataProvider dataprovider = dataProviderClass.getAnnotation(DataProvider.class);
      switch (dataprovider.value()) {
        case EXCEL:
          return loadExcel(dataProviderClass, idAndDetail, "Check");
        case CSV:
          return loadCsv(dataProviderClass, idAndDetail, "Check");
        case SELF:
          if (this instanceof IDataProvider) {
            return ((IDataProvider)this).loadCheckData(idAndDetail);
          }
          fail(dataProviderClass.getSimpleName()
                    + " is marked as dataprovider with type = self,  but does not implement IDataProvider ");
        default:
          fail("loading check data FAILED, unknown dataprovider.type found : " + dataprovider.value());
      }
    }
    fail("loading check data FAILED (either file not found or class annotation wrong/missing: "
        + dataProviderClass.getSimpleName());
    return null;
  }

  public Collection<IDataRow> loadCsv(Class<?> klass, String pathAndIdAndDetail, String suffix) {
    String filename = getFilename(klass, pathAndIdAndDetail, suffix, ".csv");
    String idAndDetail = getIdAndDetail(pathAndIdAndDetail);
    String path = ResourceHelper.getResource(klass, filename).getPath();
    File f = new File(path);
    return loadCsvFrom(f, idAndDetail);
  }

  private Collection<IDataRow> loadCsvFrom(File f, String idAndDetail) {
    CsvDataImporter csvImporter = new CsvDataImporter(f);
    return csvImporter.getWith(new TafId(idAndDetail));
  }

  public Collection<IDataRow> loadExcel(Class<?> klass, String qualifierAndIdAndDetail, String suffix) {
    String filename = getFilename(klass, qualifierAndIdAndDetail, suffix, ".xls");
    String idAndDetail = getIdAndDetail(qualifierAndIdAndDetail);
    try (InputStream is = ResourceHelper.getResource(klass, filename).openStream()) {
      return loadExcelFrom(is, idAndDetail);
    }
    catch (Exception e) {
      // ignore Exception
    }
    fail("excel file with data NOT found: " + filename + " --> " + idAndDetail);
    return null;
  }

  public Collection<IDataRow> loadExcelFrom(InputStream is, String idAndDetail) {
    ExcelDataImporter edl = new ExcelDataImporter(is, 0);
    return edl.getWith(new TafId(idAndDetail));
    // Vector<IDataRow> tempData = new Vector<IDataRow>();
    // tempData.addAll(dataRows);
    // return tempData.get(0);
  }

  public Collection<IDataRow> loadFill(String idAndDetail) {
    Class<?> dataProviderClass = getDataProviderClass();
    if (dataProviderClass.isAnnotationPresent(Excel.class)) {
      return loadExcel(dataProviderClass, idAndDetail, "Fill");
    }

    if (dataProviderClass.isAnnotationPresent(DataProvider.class)) {
      DataProvider dataprovider = dataProviderClass.getAnnotation(DataProvider.class);
      switch (dataprovider.value()) {
        case EXCEL:
          return loadExcel(dataProviderClass, idAndDetail, "Fill");
        case CSV:
          return loadCsv(dataProviderClass, idAndDetail, "Fill");
        case SELF:
          if (this instanceof IDataProvider) {
            return ((IDataProvider)this).loadFillData(idAndDetail);
          }
          fail(dataProviderClass.getSimpleName()
                    + " is marked as dataprovider with type = self,  but does not implement IDataProvider ");
        default:
          fail("loading fill data FAILED, unknown dataprovider.type found : " + dataprovider.value());
      }
    }
    fail("loading fill data FAILED (either file not found or class annotation wrong/missing: "
        + dataProviderClass.getSimpleName());
    return null;
  }

  private List<Field> makeFieldsAccessible(List<Field> allFields) {
    for (Field field : allFields) {
      field.setAccessible(true);
    }
    return allFields;
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
    if (id.isCustom()) {
      return;
    }
    Vector<IDataRow> dataRows = new Vector<>(loadCheck(checkId.asString()));
    assertFalse("too much data found (" + TafId.GetGlobalMandant() + "): '" + id + "' --> " + this.getClass(),
        dataRows.size() > 1);
    assertTrue("no check data found (" + TafId.GetGlobalMandant() + "): '" + id + "' --> " + this.getClass(),
        dataRows.size() == 1);
    IDataRow data = dataRows.firstElement();
    setCheckDataFields(data);
    setCheckFields(data);
  }

  public void setCheckDataFields(IDataRow data) {
    List<Field> fields = getCheckDataFields();
    setFields(data, fields);
  }

  public void setCheckFields(IDataRow data) {
    List<Field> fields = getCheckFields();
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
      catch (IllegalArgumentException | IllegalAccessException e) {
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

  private void setFields(IDataRow data, List<Field> fields) {
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
      catch (IllegalArgumentException | IllegalAccessException e) {
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
    if (id.isCustom()) {
      return;
    }
    Vector<IDataRow> dataRows = new Vector<>(loadFill(fillId.asString()));
    assertFalse("too much data found (" + TafId.GetGlobalMandant() + "): '" + id + "' --> " + this.getClass(),
        dataRows.size() > 1);
    assertTrue("no fill data found (" + TafId.GetGlobalMandant() + "): '" + id + "' --> " + this.getClass(),
        dataRows.size() == 1);
    IDataRow data = dataRows.firstElement();
    setFillDataFields(data);
    setFillFields(data);
  }

  public void setFillDataFields(IDataRow data) {
    List<Field> fields = getDataFields();
    setFields(data, fields);
  }

  public void setFillFields(IDataRow data) {
    List<Field> fields = getFillFields();
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
        fail(
                "instantiating field has FAILED (must be declared as public): " + f.getName() + " --> " + getClass());
      }
    }
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public void sleep(double seconds) {
    try {
      Thread.sleep(Double.valueOf(seconds * 1000).longValue());
    }
    catch (Exception e) {
      // ignore exception
    }
  }

}
