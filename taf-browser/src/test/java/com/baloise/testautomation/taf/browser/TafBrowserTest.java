package com.baloise.testautomation.taf.browser;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.baloise.testautomation.taf.base._base.ABase;
import com.baloise.testautomation.taf.browser.elements.BrFinder;

public abstract class TafBrowserTest extends ABase {

  private final BrFinder brFinder = new BrFinder(new HtmlUnitDriver(true));

  @Before
  public void setUpFinder() {
    String pathToHtmlFile = this.getClass().getResource("/testPages/" + getTestPageFileName()).getPath();
    getBrowserFinder().getDriver().get("file://" + pathToHtmlFile);
  }

  @Override
  public BrFinder getBrowserFinder() {
    return brFinder;
  }

  protected void assertBrowserLogContainsMessage(String matchingMessageText) {
    assertEquals(1, getBrowserLogEntriesMatching(matchingMessageText).size());
  }

  private String getTestPageFileName() {
    return getClass().getSimpleName() + ".html";
  }

  private List<LogEntry> getBrowserLogEntriesMatching(String matchingMessageText) {
    List<LogEntry> logEntries = getBrowserFinder().getDriver().manage().logs().get(LogType.BROWSER).getAll();
    return logEntries.stream().filter(e -> e.getMessage().equals(matchingMessageText)).collect(Collectors.toList());
  }

}
