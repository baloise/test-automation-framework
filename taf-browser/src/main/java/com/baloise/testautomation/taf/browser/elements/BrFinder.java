package com.baloise.testautomation.taf.browser.elements;

import static com.baloise.testautomation.taf.base._base.TafAssert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.baloise.testautomation.taf.browser.elements.actions.StaleElementResilientCall;
import com.baloise.testautomation.taf.browser.elements.actions.StaleElementResilientRun;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCssSelector;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCustom;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ById;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByName;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.browser.elements.BrElementFinder.ByCssSelectorFinder;
import com.baloise.testautomation.taf.browser.elements.BrElementFinder.ByIdFinder;
import com.baloise.testautomation.taf.browser.elements.BrElementFinder.ByNameFinder;
import com.baloise.testautomation.taf.browser.elements.BrElementFinder.ByTextFinder;
import com.baloise.testautomation.taf.browser.elements.BrElementFinder.ByXpathFinder;
import com.baloise.testautomation.taf.browser.interfaces.IBrowserFinder;


public class BrFinder implements IBrowserFinder<WebElement> {

  protected WebDriver driver;

  protected int timeoutInSeconds;

  private Map<Class<? extends Annotation>, WebElementFinder> supportedFinders = new HashMap<>();

  private Long currentTimeout = 0L;

  public BrFinder(WebDriver driver) {
    this(driver, 10);
  }

  public BrFinder(WebDriver driver, int timeoutInSeconds) {
    createSupportedFinders();
    registerAdditionalFinders(getAdditionalFinders());
    this.driver = driver;
    this.timeoutInSeconds = timeoutInSeconds;
    setDefaultTimeoutInMsecs();
  }

  private void registerAdditionalFinders(Collection<WebElementFinder<? extends Annotation>> finders) {
//    finders.stream().forEach(finder -> registerFinder(finder));
    for (WebElementFinder<? extends Annotation> finder : finders) {
      registerFinder(finder);
    }
  }

  private void createSupportedFinders() {
    registerFinder(new ByCssSelectorFinder());
    registerFinder(new ByIdFinder());
    registerFinder(new ByNameFinder());
    registerFinder(new ByTextFinder());
    registerFinder(new ByXpathFinder());
  }

  private void registerFinder(WebElementFinder<? extends Annotation> finder) {
    supportedFinders.put(finder.getAnnotationClass(), finder);
  }

  protected Collection<WebElementFinder<? extends Annotation>> getAdditionalFinders() {
    return Collections.emptyList();
  }

  protected boolean isDriverAssigned() {
    return driver != null;
  }

//  TODO: this needs to be removed. The user can call isDriverAssigned and act upon it accordingly
  protected void assertDriverAssigned() {
//    assertNotNull("WebDriver not initialized", driver);
    assertTrue("WebDriver not initialized", isDriverAssigned());
  }

  @Override
  public WebElement find(Annotation annotation) {
    return find(null, annotation);
  }

  @Override
  public WebElement find(WebElement root, Annotation annotation) {
    if (annotation instanceof ByCustom) {
      return findByCustom(root, (ByCustom)annotation);
    }
    WebElementFinder finder = supportedFinders.get(annotation.annotationType());
    if (finder == null) {
      return findByDefault(root, annotation);
    } else {
      // DO NOT directly access driver - use getDriver() instead --> ajaxWaiting will be done correctly
      return finder.findElement(annotation, root == null ? getDriver() : root);
    }
//    fail("annotation not yet supported: " + annotation.annotationType());
//    return null;
  }

  private WebElement findByDefault(WebElement root, Annotation annotation) {
    final String name = annotation.annotationType().getSimpleName();
    String id;
    if (name.startsWith("By")) {
      id = name.substring(2);
    } else {
      id = name;
    }
    Logger.getGlobal().warning("could not find WebElementFinder for Annotation " + name
            + " trying to find ById(" + id + ") or ByName(" + id + ")");
    // DO NOT directly access driver - use getDriver() instead --> ajaxWaiting will be done correctly
    SearchContext searchContext = root == null ? getDriver() : root;
    WebElement element = exists(By.id(id), searchContext);
    if (element == null) {
      element = searchContext.findElement(By.name(id));
    }
    return element;
  }

  private WebElement exists(By by, SearchContext searchContext) {
    WebElement result = null;
    long timeout = getTimeoutInMsecs();
    setTimeoutInMsecs(100L);
    try {
      result = searchContext.findElement(by);
    } catch (Exception e) {
      // do nothing
    } finally {
      setTimeoutInMsecs(timeout);
    }
    return result;
  }

  @Override
  public List<WebElement> findAllBy(WebElement root, By by) {
    if (root == null) {
      return getDriver().findElements(by);
    }
    return root.findElements(by);
  }

  public List<WebElement> findAllByCssSelector(WebElement root, String cssSelector) {
    return findAllBy(root, By.cssSelector(cssSelector));
  }

  public List<WebElement> findAllByText(WebElement root, String text) {
    return findAllBy(root, By.xpath("//*[contains(text(), \"" + text + "\")]"));
  }

  @Override
  public WebElement findBy(WebElement root, By by) {
    if (root == null) {
      return getDriver().findElement(by);
    }
    return root.findElement(by);
  }

  public WebElement findByCssSelector(WebElement root, ByCssSelector annotation) {
    List<WebElement> elements = findAllByCssSelector(root, annotation.value());
    return get(elements, annotation.index());
  }

  public WebElement findByCustom(WebElement root, ByCustom annotation) {
    return null;
  }

  public WebElement findById(WebElement root, ById annotation) {
    List<WebElement> elements = findAllBy(root, By.id(annotation.value()));
    return get(elements, annotation.index());
  }

  public WebElement findByName(WebElement root, ByName annotation) {
    List<WebElement> elements = findAllBy(root, By.name(annotation.value()));
    return get(elements, annotation.index());
  }

  public WebElement findByText(WebElement root, ByText annotation) {
    List<WebElement> elements = findAllByText(root, annotation.value());
    return get(elements, annotation.index());
  }

  public WebElement findByText(WebElement root, String text) {
    List<WebElement> elements = findAllByText(root, text);
    return get(elements, 0);
  }

  public WebElement findByXpath(WebElement root, ByXpath annotation) {
    List<WebElement> elements = findAllBy(root, By.xpath(annotation.value()));
    return get(elements, annotation.index());
  }

  private WebElement get(List<WebElement> elements, int index) {
    if (elements.isEmpty()) {
      return null;
    }
    if (index < 0) {
      // TODO Warnung einbauen wenn mehr als ein Element enthalten --> einfachere Entwicklung möglich
      return elements.get(0);
    }
    if (elements.size() < index) {
      return null;
    }
    return elements.get(index);
  }

  @Override
  public WebDriver getDriver() {
    waitUntilLoadingComplete();
    return driver;
  }

  @Override
  public Long getTimeoutInMsecs() {
    return currentTimeout;
  }

  public String getXPath(WebElement we) {
    return (String)((JavascriptExecutor)getDriver()).executeScript("function absoluteXPath(element) {"
        + "var comp, comps = [];" + "var parent = null;" + "var xpath = '';" + "var getPos = function(element) {"
        + "var position = 1, curNode;" + "if (element.nodeType == Node.ATTRIBUTE_NODE) {" + "return null;" + "}"
        + "for (curNode = element.previousSibling; curNode; curNode = curNode.previousSibling) {"
        + "if (curNode.nodeName == element.nodeName) {" + "++position;" + "}" + "}" + "return position;" + "};" +

        "if (element instanceof Document) {" + "return '/';" + "}" +

        "for (; element && !(element instanceof Document); element = element.nodeType == Node.ATTRIBUTE_NODE ? element.ownerElement : element.parentNode) {"
        + "comp = comps[comps.length] = {};" + "switch (element.nodeType) {" + "case Node.TEXT_NODE:"
        + "comp.name = 'text()';" + "break;" + "case Node.ATTRIBUTE_NODE:" + "comp.name = '@' + element.nodeName;"
        + "break;" + "case Node.PROCESSING_INSTRUCTION_NODE:" + "comp.name = 'processing-instruction()';" + "break;"
        + "case Node.COMMENT_NODE:" + "comp.name = 'comment()';" + "break;" + "case Node.ELEMENT_NODE:"
        + "comp.name = element.nodeName;" + "break;" + "}" + "comp.position = getPos(element);" + "}" +

        "for (var i = comps.length - 1; i >= 0; i--) {" + "comp = comps[i];" + "xpath += '/' + comp.name.toLowerCase();"
        + "if (comp.position !== null) {" + "xpath += '[' + comp.position + ']';" + "}" + "}" +

        "return xpath;" +

        "} return absoluteXPath(arguments[0]);", we);
  }

  @Override
  public void setDefaultTimeoutInMsecs() {
    setTimeoutInMsecs(1000L * timeoutInSeconds);
  }

  @Override
  public void setTimeoutInMsecs(Long msecs) {
    currentTimeout = msecs;
    if (driver != null) {
      driver.manage().timeouts().implicitlyWait(currentTimeout, TimeUnit.MILLISECONDS);
    }
  }

  public void setToDefaultContent() {
    assertDriverAssigned();
    getDriver().switchTo().defaultContent();
  }

  public void setToFrameWithId(String id) {
    assertDriverAssigned();
    getDriver().switchTo().frame(driver.findElement(By.id(id)));
  }

  @Override
  public void waitUntilLoadingComplete() {}

  @Override
  public void safeInvoke(Runnable runnable) {
    new StaleElementResilientRun(getTimeoutInMsecs()).invoke(runnable);
  }

  @Override
  public <T> T safeInvoke(Callable<T> callable) {
    return new StaleElementResilientCall<T>(getTimeoutInMsecs()).invoke(callable);
  }

}
