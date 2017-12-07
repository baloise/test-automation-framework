package com.baloise.testautomation.taf.browser.elements;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCssSelector;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByCustom;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ById;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByName;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByText;
import com.baloise.testautomation.taf.base._interfaces.IAnnotations.ByXpath;
import com.baloise.testautomation.taf.browser.interfaces.IBrowserFinder;

public class BrFinder implements IBrowserFinder<WebElement> {

  protected WebDriver driver = null;

  protected int timeoutInSeconds = 10;

  private Long currentTimeout = 0L;

  public BrFinder(WebDriver driver) {
    this.driver = driver;
  }

  public BrFinder(WebDriver driver, int timeoutInSeconds) {
    this.driver = driver;
    this.timeoutInSeconds = timeoutInSeconds;
    setDefaultTimeoutInMsecs();
  }

  protected void assertDriverAssigned() {
    assertNotNull("WebDriver not initialized", driver);
  }

  public void assumeDriverAssigned() {
    assumeNotNull("WebDriver not initialized", driver);
  }

  @Override
  public WebElement find(Annotation annotation) {
    return find(null, annotation);
  }

  @Override
  public WebElement find(WebElement root, Annotation annotation) {
    if (annotation instanceof ById) {
      return findById(root, (ById)annotation);
    }
    if (annotation instanceof ByText) {
      return findByText(root, (ByText)annotation);
    }
    if (annotation instanceof ByCssSelector) {
      return findByCssSelector(root, (ByCssSelector)annotation);
    }
    if (annotation instanceof ByCustom) {
      return findByCustom(root, (ByCustom)annotation);
    }
    if (annotation instanceof ByXpath) {
      return findByXpath(root, (ByXpath)annotation);
    }
    if (annotation instanceof ByName) {
      return findByName(root, (ByName)annotation);
    }
    fail("annotation not yet supported: " + annotation.annotationType());
    return null;
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
      driver.manage().timeouts().implicitlyWait(new Double(currentTimeout).intValue(), TimeUnit.MILLISECONDS);
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

}
