package com.baloise.testautomation.taf.server;

import com.baloise.testautomation.taf.swing.server.main.SwApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwApplicationTest {

  @Test
  public void formattedXMLToScreen() {
    SwApplication app = new SwApplication();
    String xml = app.storeFormatted("<frame><another>hello</another></frame>", null);
    assertEquals(true, !xml.isEmpty());
  }

  // @Test
  // public void formattedXML() {
  // SwApplication app = new SwApplication();
  // BufferedReader br = null;
  // try {
  // br = new BufferedReader(new FileReader("c:/testing/parsys.xml"));
  // StringBuilder sb = new StringBuilder();
  // String line = br.readLine();
  //
  // while (line != null) {
  // sb.append(line);
  // line = br.readLine();
  // }
  // String everything = sb.toString();
  // app.storeFormatted(everything, null);
  // }
  // catch (Exception e) {}
  // // finally {
  // // br.close();
  // // }
  // }
}
