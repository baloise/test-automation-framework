package com.baloise.testautomation.taf.server;

import java.io.StringReader;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.baloise.testautomation.taf.swing.server.main.SwApplication;

public class SwApplicationListItemsTest {

  // A list item containing "--" (e.g. the message "... required: Muster--Meier") must not make the XML invalid
  @Test
  public void listItemWithDoubleHyphenKeepsXmlValid() throws Exception {
    DefaultListModel<String> model = new DefaultListModel<>();
    model.addElement("Basic salary is required: Muster--Meier AG, Basel");
    model.addElement("Triple --- and ends with -");
    JList<String> list = new JList<>(model);

    StringBuilder xml = new StringBuilder("<list>");
    new SwApplication().allListItemsToXML(xml, list);
    xml.append("</list>");

    org.w3c.dom.Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .parse(new InputSource(new StringReader(xml.toString())));
    Assert.assertEquals("2", XPathFactory.newInstance().newXPath().evaluate("count(//listitem)", doc));
    // the text of the listitem itself stays unchanged
    Assert.assertTrue(XPathFactory.newInstance().newXPath()
        .evaluate("//listitem[@index='0']", doc).contains("Muster--Meier"));
  }
}
