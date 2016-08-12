package com.baloise.testautomation.taf.base.testing.e2e;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class LongRunner extends OrderedRunner {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  public @interface Data {
  }

  public class LongRunnerClassInfo {
    public Class<?> testClass;
    public long id = new Date().getTime();
    public Collection<LongRunnerMethodInfo> methods;

    public LongRunnerClassInfo() {
      testClass = null;
      methods = new Vector<LongRunnerMethodInfo>();
    }

    public LongRunnerClassInfo(Class<?> aTestClass) {
      this(aTestClass, new Vector<LongRunnerMethodInfo>());
    }

    public LongRunnerClassInfo(Class<?> aTestClass, Collection<LongRunnerMethodInfo> methods) {
      assertNotNull(aTestClass);
      assertNotNull(methods);
      id = new Date().getTime();
      testClass = aTestClass;
      this.methods = methods;
    }

    public LongRunnerClassInfo(long anId) {
      this();
      id = anId;
    }

    public void addMethod(String aMethodName, TestStatus aTestStatus, Double aFirstTryHours, Double aMaxTryHours) {
      assertFalse("'addMethod' wurde für Methode " + aMethodName
          + " bereits durchgeführt --> zweites 'addMethod' nicht möglich", longRunnerMethodInfoExists(aMethodName));
      LongRunnerMethodInfo lrmi = new LongRunnerMethodInfo(aMethodName, aTestStatus, new Date(), aFirstTryHours,
          aMaxTryHours);
      lrci.methods.add(lrmi);
    }

    private void archiveIfCompleted() {
      LongRunnerMethodInfo method = (LongRunnerMethodInfo)methods.toArray()[methods.size() - 1];
      if (!method.isSuspended() && !method.isUnknown()) {
        new File(getProcessFileName(false)).renameTo(new File(getProcessFileName(true)));
        new File(getDataFileName(false)).renameTo(new File(getDataFileName(true)));
      }
    }

    private Properties getData() {
      Properties data = new Properties();
      Field[] fields = getFields();
      for (Field field : fields) {
        try {
          if (field.getType().equals(Date.class)) {
            data.put(field.getName(), LongRunnerMethodInfo.dateFormat().format(field.get(null)));
          }
          else {
            data.put(field.getName(), field.get(null).toString());
          }
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch (NullPointerException npe) {
          data.put(field.getName(), "{null}");
        }
      }
      return data;
    }

    private String getDataFileName(boolean isCompleted) {
      return getFileName(isCompleted, ".props");
    }

    private Field[] getFields() {
      List<Field> dataFields = new ArrayList<Field>();
      Field[] fields = testClass.getFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Data.class)) {
          if (Modifier.isStatic(field.getModifiers())) {
            dataFields.add(field);
          }
        }
      }
      return dataFields.toArray(fields);
    }

    private String getFileName(boolean isCompleted, String extension) {
      String completed = "";
      if (isCompleted) {
        completed = "completed.";
      }
      return path + completed + testClass.getName() + "." + id + extension;
    }

    public LongRunnerMethodInfo getLongRunnerMethodInfo(String methodName) {
      for (LongRunnerMethodInfo lrmi : methods) {
        if (lrmi.methodName.equals(methodName)) {
          return lrmi;
        }
      }
      return null;
    }

    private String getProcessFileName(boolean isCompleted) {
      return getFileName(isCompleted, ".csv");
    }

    public void initWith(LongRunnerInfo aLongRunnerInfo) {
      assertNotNull(aLongRunnerInfo);
      id = aLongRunnerInfo.id;
      testClass = aLongRunnerInfo.testClass;
      load();
    }

    public void load() {
      loadProcess();
      loadData();
    }

    public void loadData() {
      FileReader fileReader = null;
      try {
        fileReader = new FileReader(getDataFileName(false));
        Properties data = getData();
        data.load(fileReader);
        for (Object key : data.keySet()) {
          try {
            Field field = testClass.getField((String)key);
            String value = data.getProperty((String)key);
            if ("{null}".equalsIgnoreCase(value)) {
              field.set(null, null);
            }
            else {
              if (field.getType().equals(String.class)) {
                field.set(null, value);
              }
              if (field.getType().equals(int.class)) {
                field.set(null, Integer.parseInt(value));
              }
              if (field.getType().equals(Integer.class)) {
                field.set(null, Integer.parseInt(value));
              }
              if (field.getType().equals(double.class)) {
                field.set(null, Double.parseDouble(value));
              }
              if (field.getType().equals(Double.class)) {
                field.set(null, Double.parseDouble(value));
              }
              if (field.getType().equals(Date.class)) {
                field.set(null, LongRunnerMethodInfo.parseDate(value));
              }
            }
          }
          catch (Exception e) {
            assertTrue("Some error occurred when trying to set variable " + key + " to " + data.get(key), false);
          }
        }
      }
      catch (IOException e) {
        fail("store of test data has NOT been correctly done");
      }
      finally {
        try {
          fileReader.close();
        }
        catch (Exception e) {}
      }
    }

    public void loadProcess() {
      FileReader fileReader = null;
      try {
        fileReader = new FileReader(getProcessFileName(false));
        methods = LongRunnerMethodInfo.loadWith(fileReader);
      }
      catch (IOException e) {
        fail("load of test state information has NOT been correctly done");
      }
      finally {
        try {
          fileReader.close();
        }
        catch (Exception e) {}
      }
    }

    public boolean longRunnerMethodInfoExists(String methodName) {
      return (getLongRunnerMethodInfo(methodName) != null);
    }

    public void store() {
      storeProcess();
      storeData();
      archiveIfCompleted();
    }

    public void storeData() {
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(getDataFileName(false));
        Properties data = getData();
        data.store(fileWriter, "Data for long running JUnit test");
      }
      catch (IOException e) {
        fail("store of test data has NOT been correctly done");
      }
      finally {
        try {
          fileWriter.close();
        }
        catch (Exception e) {}
      }
    }

    public void storeProcess() {
      FileWriter fileWriter = null;
      try {
        fileWriter = new FileWriter(getProcessFileName(false));
        LongRunnerMethodInfo.print(fileWriter, methods);
      }
      catch (IOException e) {
        fail("store of test state information has NOT been correctly done");
      }
      finally {
        try {
          fileWriter.close();
        }
        catch (Exception e) {}
      }
    }

  }

  public static class LongRunnerInfo implements Comparable {
    Class<?> testClass;
    long id;

    public LongRunnerInfo(Class<?> aTestClass, long anId) {
      assertNotNull(aTestClass);
      assertTrue(anId >= 0);
      testClass = aTestClass;
      id = anId;
    }

    @Override
    public int compareTo(Object o) {
      if (o instanceof LongRunnerInfo) {
        LongRunnerInfo lri = (LongRunnerInfo)o;
        return new Long(id).compareTo(new Long(lri.id));
      }
      return 0;
    }
  }

  public static class LongRunnerMethodInfo {
    private static String cellContents(CSVRecord header, CSVRecord line, String columnName) {
      int colIndex = -1;
      for (int i = 0; i < header.size(); i++) {
        if (columnName.equalsIgnoreCase(header.get(i))) {
          colIndex = i;
          break;
        }
      }
      if (colIndex >= 0) {
        return line.get(colIndex);
      }
      ;
      return "";
    }

    private static SimpleDateFormat dateFormat() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private static CSVFormat getCSVFormat() {
      return CSVFormat.EXCEL.withDelimiter(';');
    }

    private static Object[] getHeaders() {
      return new String[] {"methodname", "laststatus", "modified", "nexttry", "lasttry"};
    }

    public static Collection<LongRunnerMethodInfo> loadWith(FileReader fileReader) throws IOException {
      CSVParser csvParser = new CSVParser(fileReader, getCSVFormat());
      Vector<LongRunnerMethodInfo> result = new Vector<LongRunnerMethodInfo>();
      List<CSVRecord> records = csvParser.getRecords();
      CSVRecord header = null;
      for (int i = 0; i < records.size(); i++) {
        if (i == 0) {
          header = records.get(i);
        }
        else {
          CSVRecord line = records.get(i);
          LongRunnerMethodInfo lrmi = new LongRunnerMethodInfo(cellContents(header, line, "methodname"),
              TestStatus.valueOf(cellContents(header, line, "laststatus")), parseDate(cellContents(header, line,
                  "modified")), parseDate(cellContents(header, line, "nexttry")), parseDate(cellContents(header, line,
                  "lasttry")));
          result.add(lrmi);
        }
      }
      csvParser.close();
      return result;
    }

    private static Date parseDate(String date) {
      if (date.trim().isEmpty()) {
        return null;
      }
      try {
        return dateFormat().parse(date);
      }
      catch (ParseException e) {
        return null;
      }
    }

    private static Double parseDouble(String d) {
      if (d.trim().isEmpty()) {
        return null;
      }
      return Double.parseDouble(d);
    }

    public static void print(FileWriter fileWriter, Collection<LongRunnerMethodInfo> methods) throws IOException {
      CSVPrinter csvPrinter = new CSVPrinter(fileWriter, getCSVFormat());
      csvPrinter.printRecord(getHeaders());
      for (LongRunnerMethodInfo lrmi : methods) {
        lrmi.printRecord(csvPrinter);
      }
      csvPrinter.close();
    }

    public String methodName = "";

    private TestStatus lastStatus = TestStatus.unknown;

    private Date modified = null;

    private Date nextTry = null;

    private Date lastTry = null;

    public LongRunnerMethodInfo(String aMethodName, TestStatus aLastStatus, Date aModified, Date aNextTry, Date aLastTry) {
      methodName = aMethodName;
      lastStatus = aLastStatus;
      modified = aModified;
      nextTry = aNextTry;
      lastTry = aLastTry;
    }

    public LongRunnerMethodInfo(String aMethodName, TestStatus aLastStatus, Date aModified, Double nextTryHours,
        Double maxTryHours) {
      methodName = aMethodName;
      lastStatus = aLastStatus;
      assertNotNull(aModified);
      modified = aModified;

      long HOUR = 3600 * 1000;
      if (nextTryHours == null) {
        nextTry = null;
      }
      else {
        nextTry = new Date(modified.getTime() + (long)(nextTryHours * HOUR));
      }
      if (maxTryHours == null) {
        lastTry = null;
      }
      else {
        lastTry = new Date(nextTry.getTime() + (long)(maxTryHours * HOUR));
      }
    }

    public boolean cantResumeAnymore() {
      if (lastTry == null) {
        return false;
      }
      return (new Date().after(lastTry));
    }

    public boolean canTryToResume() {
      if (cantResumeAnymore()) {
        return false;
      }
      if (nextTry == null) {
        return true;
      }
      return (new Date().after(nextTry));
    }

    public boolean isFailed() {
      return (lastStatus.equals(TestStatus.failed));
    }

    public boolean isPassed() {
      return (lastStatus.equals(TestStatus.passed));
    }

    public boolean isSuspended() {
      return (lastStatus.equals(TestStatus.suspended));
    }

    public boolean isUnknown() {
      return (lastStatus.equals(TestStatus.unknown));
    }

    public void printRecord(CSVPrinter csvPrinter) throws IOException {
      csvPrinter.printRecord(methodName, lastStatus.toString(), timeAsString(modified), timeAsString(nextTry),
          timeAsString(lastTry));
    }

    public void setToFailed() {
      lastStatus = TestStatus.failed;
      modified = new Date();
    }

    public void setToPassed() {
      lastStatus = TestStatus.passed;
      modified = new Date();
    }

    public void setToSuspended() {
      lastStatus = TestStatus.suspended;
      modified = new Date();
    }

    private String timeAsString(Date d) {
      if (d == null) {
        return "";
      }
      return dateFormat().format(d);
    }

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface Suspend {
    public double firstTryAfterHours() default 0.01;

    public double maxHours() default 1.0;
  }

  public enum TestStatus {
    unknown, passed, failed, suspended, ignored
  }

  public static Logger logger = LogManager.getLogger("LongRunner");

  private static String path = "";

  public static Collection<LongRunnerInfo> getFor(Class<?> testClass) {
    Vector<LongRunnerInfo> result = new Vector<LongRunner.LongRunnerInfo>();
    // TODO korrekte Pr�fung
    assertTrue("Test class must be annotated with a Runner that is a LongRunner or a subclass of it", true);

    final String testClassName = testClass.getName();
    FilenameFilter fnf = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith(testClassName) && name.endsWith(".csv");
      }
    };
    File dir;
    if (path == null | path.isEmpty()) {
      dir = new File(".");
    }
    else {
      dir = new File(path);
    }
    final File[] files = dir.listFiles(fnf);

    for (File file : files) {
      final String id = file.getName().replace(testClassName, "").replace(".csv", "").replace(".", "");
      Long l = Long.parseLong(id);
      result.add(new LongRunnerInfo(testClass, l));
    }
    return result;
  }

  public static void setPath(String path) {
    LongRunner.path = path;
  }

  private LongRunnerClassInfo lrci = null;

  public LongRunner(Class<?> klass) throws InitializationError {
    super(klass);
  }

  private String getId() {
    Date start = new Date(getLRCI().id);
    String startAsString = LongRunnerMethodInfo.dateFormat().format(start);
    return "[" + startAsString + "]";
  }

  private LongRunnerMethodInfo getLongRunnerMethodInfo(FrameworkMethod method) {
    return lrci.getLongRunnerMethodInfo(method.getName());
  }

  private LongRunnerClassInfo getLRCI() {
    if (lrci == null) {
      lrci = new LongRunnerClassInfo(getTestClass().getJavaClass());
    }
    return lrci;
  }

  @Override
  protected String getName() {
    return getId() + " " + super.getName();
  }

  protected void info(String message) {
    logger.info(message);
  }

  public void initWith(LongRunnerInfo aLongRunnerInfo) {
    lrci = new LongRunnerClassInfo();
    lrci.initWith(aLongRunnerInfo);
  }

  @Override
  protected Statement methodBlock(FrameworkMethod method) {
    LongRunnerMethodInfo lrmi = getLongRunnerMethodInfo(method);
    assertNotNull("No method info --> no run possible", lrmi);
    final FrameworkMethod m = method;

    if (lrmi.isPassed()) {
      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          m.getName();
          info(m.getName() + " has passed in a previous test");
        }
      };
    }
    if (lrmi.isFailed()) {
      return new Statement() {
        @Override
        public void evaluate() throws Throwable {
          Assert.fail(m.getName() + "has failed in a previous test run");
        }
      };
    }
    if (lrmi.isSuspended()) {
      if (lrmi.cantResumeAnymore()) {
        return new Statement() {
          @Override
          public void evaluate() throws Throwable {
            Assert.fail(m.getName() + " has timeout and cannot be resumed anymore");
          }
        };
      }
      if (lrmi.canTryToResume()) {
        final Statement aSs = super.methodBlock(method);
        final LongRunnerMethodInfo aLrmi = lrmi;
        return new Statement() {
          Statement superStatement = aSs;
          LongRunnerMethodInfo lrmi = aLrmi;

          @Override
          public void evaluate() throws Throwable {
            try {
              superStatement.evaluate();
              lrmi.setToPassed();
              stopped = false;
            }
            catch (Throwable t) {
              stopped = true;
              Assume
                  .assumeFalse(
                      "test has failed but is in suspension mode --> reported as 'ignored', will/must try until really passed or timed out",
                      stopped);
            }
          }
        };
      }
      fail("Should not reach this code! Check the suspend handling");
    }
    if (lrmi.isUnknown()) {
      final Statement aSs = super.methodBlock(method);
      final LongRunnerMethodInfo aLrmi = lrmi;
      return new Statement() {
        Statement superStatement = aSs;
        LongRunnerMethodInfo lrmi = aLrmi;

        @Override
        public void evaluate() throws Throwable {
          try {
            superStatement.evaluate();
            lrmi.setToPassed();
          }
          catch (Throwable t) {
            lrmi.setToFailed();
            throw t;
          }
        }
      };
    }
    return super.methodBlock(method);
  }

  @Override
  public void run(final RunNotifier notifier) {
    super.run(notifier);
    store();
  }

  @Override
  protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
    if (!lrci.longRunnerMethodInfoExists(method.getName())) {
      // Dieser Runner wurde ohne load erstmals gestartet
      TestStatus testStatus = TestStatus.unknown;
      Double firstTryHours = null;
      Double maxHours = null;
      Suspend a = method.getAnnotation(Suspend.class);
      if (a != null) {
        testStatus = TestStatus.suspended;
        if (a.firstTryAfterHours() > 0.0) {
          firstTryHours = a.firstTryAfterHours();
        }
        if (a.maxHours() > 0.0) {
          maxHours = a.maxHours();
        }
      }
      lrci.addMethod(method.getName(), testStatus, firstTryHours, maxHours);
    }
    LongRunnerMethodInfo lrmi = getLongRunnerMethodInfo(method);
    assertNotNull(lrmi);
    if (!lrmi.cantResumeAnymore()) {
      if (!lrmi.canTryToResume()) {
        stopped = true;
      }
    }
    super.runChild(method, notifier);
  }

  public void setId(LongRunnerInfo aLongRunnerInfo) {
    lrci = new LongRunnerClassInfo(aLongRunnerInfo.id);
  }

  public void store() {
    assertNotNull("store nicht möglich da LongRunnerClassInfo nicht vorhanden", lrci);
    lrci.store();
  }

  @Override
  protected String testName(FrameworkMethod method) {
    return getId() + " " + method.getName();
  }

  protected void warn(String message) {
    logger.warn(message);
  }

}
