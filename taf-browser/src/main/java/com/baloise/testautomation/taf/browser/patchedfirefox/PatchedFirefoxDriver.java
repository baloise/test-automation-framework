package com.baloise.testautomation.taf.browser.patchedfirefox;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;

public class PatchedFirefoxDriver extends FirefoxDriver {

  public PatchedFirefoxDriver(FirefoxOptions ffo) {
    super(ffo);
  }

  // when quitting the browser shows am modal dialog about 'sure to leave this page....', then
  // original RemoteWebDriver will hang forever
  // -> in case of quit-command, wrap it into a timeout capable callable
  @Override
  protected Response execute(final String driverCommand, final Map<String, ?> parameters) {
    if ("quit".equalsIgnoreCase(driverCommand)) {
      Response r = null;
      ExecutorService executorService = Executors.newCachedThreadPool();
      Callable<Response> callable = new Callable<Response>() {

        @Override
        public Response call() throws Exception {
          return basicExecute(driverCommand, parameters);
        }

      };
      Future<Response> future = executorService.submit(callable);
      try {
        r = future.get(5, TimeUnit.SECONDS);
      }
      catch (Exception e) {
        e.printStackTrace();
        Robot robot;
        try {
          robot = new Robot();
          robot.keyPress(KeyEvent.VK_ENTER);
          robot.keyRelease(KeyEvent.VK_ENTER);
        }
        catch (AWTException e1) {}
      }
      finally {
        future.cancel(true);
      }
      return r;
    }
    else {
      return basicExecute(driverCommand, parameters);
    }
  }

  private Response basicExecute(String driverCommand, Map<String, ?> parameters) {
    return super.execute(driverCommand, parameters);
  }

  @Override
  public List<WebElement> findElements(By by) {
    List<WebElement> elements = super.findElements(by);
    List<WebElement> patchedElements = new ArrayList<WebElement>();
    for (WebElement element : elements) {
      patchedElements.add(new PatchedWebElement((RemoteWebElement)element));
    }
    return patchedElements;
  }

  @Override
  public WebElement findElement(By by) {
    return new PatchedWebElement((RemoteWebElement)super.findElement(by));
  }

  // @Override
  // public void quit() {
  // Assert.fail("do not call this method because it doesn't react in the way expected");
  // }
  //
  // @Override
  // public void close() {
  // Assert.fail("do not call this method because it doesn't react in the way expected");
  // }

}
