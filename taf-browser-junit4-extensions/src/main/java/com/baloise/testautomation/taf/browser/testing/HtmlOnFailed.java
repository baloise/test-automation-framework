package com.baloise.testautomation.taf.browser.testing;

import java.io.File;
import java.io.PrintWriter;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;

public class HtmlOnFailed extends TestWatcher {

  private static WebDriver driver;

  public static void SetDriver(WebDriver aDriver) {
    driver = aDriver;
  }

  private String path = "";

  public HtmlOnFailed(String path) {
    super();
    this.path = path;
    this.path = this.path.replace("\\", "/");
    if (this.path.length() > 0) {
      if (!this.path.endsWith("/")) {
        this.path = this.path + "/";
      }
    }
  }

  @Override
  protected void failed(Throwable e, Description description) {
    saveHtml();
  }

  public void saveHtml() {
    if (driver != null) {
      File f = new File(path + "failed_" + System.currentTimeMillis() + ".html");
      PrintWriter pw = null;
      try {
        pw = new PrintWriter(f);
        pw.print(driver.getPageSource());
        pw.close();
      }
      catch (Exception e) {}
      finally {
        if (pw != null) {
          pw.close();
        }
      }
    }
  }

}
