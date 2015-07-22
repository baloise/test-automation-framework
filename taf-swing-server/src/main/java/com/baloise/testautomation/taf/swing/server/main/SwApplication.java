/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package com.baloise.testautomation.taf.swing.server.main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.server.elements.ASwElement;
import com.baloise.testautomation.taf.swing.server.elements.SwButton;
import com.baloise.testautomation.taf.swing.server.elements.SwCell;
import com.baloise.testautomation.taf.swing.server.elements.SwCheckBox;
import com.baloise.testautomation.taf.swing.server.elements.SwFrame;
import com.baloise.testautomation.taf.swing.server.elements.SwInput;
import com.baloise.testautomation.taf.swing.server.elements.SwInternalFrame;
import com.baloise.testautomation.taf.swing.server.elements.SwLabel;
import com.baloise.testautomation.taf.swing.server.elements.SwMenuItem;
import com.baloise.testautomation.taf.swing.server.elements.SwTabbedPane;
import com.baloise.testautomation.taf.swing.server.elements.SwTable;
import com.baloise.testautomation.taf.swing.server.elements.SwTableColumn;
import com.baloise.testautomation.taf.swing.server.elements.SwUnsupportedElement;

/**
 * 
 */
public class SwApplication implements ISwApplication<ISwElement<Component>> {

  public int id = 0;
  public Hashtable<Long, ISwElement<Component>> components;
  private long counter;
  private Component root = null;

  // private Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();

  private int timeoutInSeconds = 60;

  public void allCellsToXML(StringBuilder xml, JTable table) {
    for (int c = 0; c < table.getColumnCount(); c++) {
      xml.append("<header tid = \"" + counter + "\" col = \"" + c + "\" value = \""
          + asValidAttribute(table.getColumnModel().getColumn(c).getHeaderValue().toString()) + "\"></header>");
      components.put(counter, new SwTableColumn(counter, c, table));
      counter++;
    }
    for (int c = 0; c < table.getColumnCount(); c++) {
      for (int r = 0; r < table.getRowCount(); r++) {
        xml.append("<cell tid = \"" + counter + "\" row = \"" + r + "\" col = \"" + c + "\" value = \""
            + asValidAttribute(table.getValueAt(r, c).toString()) + "\"></cell>");
        components.put(counter, new SwCell(counter, r, c, table));
        counter++;
      }
    }
  }

  public String allComponentsToXML(Component c) {
    StringBuilder xml = new StringBuilder();
    components = new Hashtable<Long, ISwElement<Component>>();
    counter = 0;
    allComponentsToXML(xml, c);
    return xml.toString();
  }

  public void allComponentsToXML(StringBuilder xml, Component c) {
    if (c == null) {
      return;
    }
    if (c.getBounds().isEmpty()) {
      return;
    }
    ISwElement<?> se = putComponent(c);
    xml.append("<");
    xml.append(se.getType());
    // xml.append(" tid=");
    // xml.append(se.getTID());
    // xml.append(" name=");
    // xml.append(c.getName());
    xml.append(((ASwElement)se).getPropertiesAsString());
    xml.append(">");
    // level++;
    if (c instanceof Container) {
      Component[] cs;
      if (c instanceof JMenu) {
        cs = ((JMenu)c).getMenuComponents();
      }
      else {
        if (c instanceof JTable) {
          cs = new Component[] {};
          allCellsToXML(xml, (JTable)c);
        }
        else {
          // if (c instanceof JTabbedPane) {
          // Component tab = ((JTabbedPane)c).getSelectedComponent();
          // if (tab instanceof Container) {
          // cs = ((Container)tab).getComponents();
          // } else {
          // cs = ((Container)c).getComponents();
          // }
          // } else {
          cs = ((Container)c).getComponents();
        }
      }
      // }
      if (cs.length > 0) {
        xml.append(System.getProperty("line.separator"));
      }
      for (Component innerC : cs) {
        if (innerC != c) {
          allComponentsToXML(xml, innerC);
        }
      }
    }
    else {
      // System.out.println(c.toString());
    }
    // level--;
    xml.append("</");
    xml.append(se.getType());
    xml.append(">");
    xml.append(System.getProperty("line.separator"));
  }

  // private int level = 0;

  private String asValidAttribute(String s) {
    return s.replace("&", "&amp;");
  }

  private void debugRoot() {
    if (root instanceof JFrame) {
      // FrameFixture ff = new FrameFixture(robot, (JFrame)root);
      // System.out.println(ff);
    }

    // FrameFixture ff = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
    // @Override
    // protected boolean isMatching(JFrame frame) {
    // if (frame.getTitle() != null) {
    // return "Login".equals(frame.getTitle());
    // }
    // return false;
    // }
    // }).using(robot);
    // System.out.println("FrameFixtrure: " + ff.toString());
  }

  private TafProperties execApplicationCommand(TafProperties props) {
    String command = props.getString(paramCommand);
    TafProperties result = new TafProperties();
    if (Command.findelementbyxpath.toString().equalsIgnoreCase(command)) {
      try {
        ASwElement element = (ASwElement)findElementByXpath(props.getLong(paramRoot), props.getString(paramXPath));
        result.putObject(new Long(element.getTID()).toString(), element.getType());
        return result;
      }
      catch (Exception e) {}
    }
    if (Command.findelementsbyxpath.toString().equalsIgnoreCase(command)) {
      try {
        List<ISwElement<Component>> elements = findElementsByXpath(props.getLong(paramRoot),
            props.getString(paramXPath));
        for (ISwElement<Component> element : elements) {
          result.putObject(new Long(((ASwElement)element).getTID()).toString(), element.getType());
        }
        return result;
      }
      catch (Exception e) {}
    }
    if (Command.storehierarchy.toString().equalsIgnoreCase(command)) {
      try {
        storeHierarchy(props.getString(paramPath));
        return result;
      }
      catch (Exception e) {}
    }
    return getError("application command returns errors or command is invalid: " + command);
  }

  @Override
  public TafProperties execCommand(TafProperties props) {
    String type = props.getString(paramType);
    if (ISwApplication.type.equalsIgnoreCase(type)) {
      return execApplicationCommand(props);
    }
    else {
      return execElementCommand(props);
    }
  }

  /**
   * @param props
   */
  private TafProperties execElementCommand(TafProperties props) {
    System.out.println("execElementCommand");
    String type = props.getString(paramType);
    try {
      ISwElement<Component> swElement = components.get(props.getLong("tid"));
      if (swElement.getType().equalsIgnoreCase(type)) {
        return ((ASwElement)swElement).execCommand(props);
      }
      else {
        return getError("element type does not match. Expected = " + swElement.getType() + ", actual = " + type);
      }
    }
    catch (Exception e) {
      System.out.println("SwApplication --> execElementCommand --> not executed");
      return getError("element command not executed (element not found) --> " + e.getMessage());
    }
  }

  @Override
  public ISwElement<Component> find(Annotation annotion) {
    return null;
  }

  @Override
  public ISwElement<Component> find(ISwElement<Component> root, Annotation annotation) {
    return null;
  }

  private NodeList findAllByXpath(Long root, String s) {
    // 1. Instantiate an XPathFactory.

    javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();

    // 2. Use the XPathFactory to create a new XPath object
    javax.xml.xpath.XPath xpath = factory.newXPath();

    String xml = null;

    try {
      // 3. Compile an XPath string into an XPathExpression
      javax.xml.xpath.XPathExpression expression = xpath.compile(s);
      // 4. Evaluate the XPath expression on an input document
      // NodeList result = (NodeList)expression.evaluate(new
      // org.xml.sax.InputSource("C:\somepath\\somefile.xml"),
      // XPathConstants.NODESET);

      // System.out.println("Root will NOT be set: " + root);
      // System.out.println("XPath: " + s);
      xml = toFullXML();
      if (xml == null) {
        return null;
      }
      if (xml.isEmpty()) {
        return null;
      }
      // System.out.println("XML: " + xml);
      NodeList result = (NodeList)expression.evaluate(new org.xml.sax.InputSource(new StringReader(xml)),
          XPathConstants.NODESET);
      return result;
    }
    catch (XPathExpressionException e) {
      e.printStackTrace();
      System.out.println("XML = " + xml);
    }
    return null;
  }

  // @Override
  // public String getFullXML() {
  // return null;
  // }
  //
  // @Override
  // public String getMappedXML() {
  // return null;
  // }
  //
  // @Override
  // public String toString(long tid) {
  // return null;
  // }

  @Override
  public ISwElement<Component> findElementByXpath(Long root, String s) {
    List<ISwElement<Component>> elements = findElementsByXpath(root, s);
    if (elements.size() > 0) {
      return elements.get(0);
    }
    return null;
  }

  @Override
  public List<ISwElement<Component>> findElementsByXpath(Long root, String s) {
    long time = System.currentTimeMillis();
    boolean timeout = false;
    while (!timeout) {
      try {
        setRoot();
        NodeList nl = findAllByXpath(root, s);
        if (nl.getLength() > 0) {
          Vector<ISwElement<Component>> result = new Vector<ISwElement<Component>>();
          for (int i = 0; i < nl.getLength(); i++) {
            try {
              String l = nl.item(i).getAttributes().getNamedItem("tid").getNodeValue();
              System.out.println("Found: " + l);
              System.out.println(nl.item(i).getNodeName());
              System.out.println(nl.item(i));
              result.add(components.get(Long.parseLong(l)));
            }
            catch (Exception e) {}
          }
          return result;
        }
        else {
          try {
            Thread.sleep(100);
          }
          catch (Exception e2) {}
        }
      }
      catch (Exception e) {
        try {
          Thread.sleep(100);
        }
        catch (Exception e2) {}
        // e.printStackTrace();
      }
      if (System.currentTimeMillis() > time + 1000 * timeoutInSeconds) {
        timeout = true;
      }
    }
    System.out.println("timed out");
    return new Vector<ISwElement<Component>>();
  }

  public ISwElement<Component> get(long tid) {
    return components.get(tid);
  }

  // TODO

  private TafProperties getError(String statusMessage) {
    TafProperties result = new TafProperties();
    result.putObject("status", "error");
    result.putObject("message", statusMessage);
    return result;
  }

  public ISwElement<Component> getSwElement(long tid, Component c) {
    if (c instanceof Frame) {
      return new SwFrame(tid, (Frame)c);
    }
    if (c instanceof JInternalFrame) {
      return new SwInternalFrame(tid, (JInternalFrame)c);
    }
    // if (c instanceof JMenu) {
    // return "menu";
    // }
    if (c instanceof JMenuItem) {
      return new SwMenuItem(tid, (JMenuItem)c);
    }
    if (c instanceof JButton) {
      return new SwButton(tid, (JButton)c);
    }
    if (c instanceof JLabel) {
      return new SwLabel(tid, (JLabel)c);
    }
    if (c instanceof JTabbedPane) {
      return new SwTabbedPane(tid, (JTabbedPane)c);
    }
    if (c instanceof JCheckBox) {
      return new SwCheckBox(tid, (JCheckBox)c);
    }
    if (c instanceof JTable) {
      return new SwTable(tid, (JTable)c);
    }
    // if (c instanceof JRadioButton) {
    // return "radiobutton";
    // }
    if (c instanceof JTextField) {
      return new SwInput(tid, (JTextField)c);
    }
    return new SwUnsupportedElement(tid, c);
  }

  private ISwElement<Component> putComponent(Component c) {
    ISwElement<Component> se = getSwElement(counter, c);
    components.put(counter, se);
    counter++;
    return se;
  }

  public void setRoot() {
    Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    root = SwingUtilities.getRoot(c);
  }

  @Override
  public void startJNLPInstrumentation(String url) {}

  @Override
  public void startJNLPInstrumentationWithSpy(String url, String filename) {}

  private String toFullXML() {
    setRoot();
    return allComponentsToXML(root);
  }

  private String toMappedXML() {
    return toFullXML();
  }

  @Override
  public void storeHierarchy(String path) {
    String xml = toFullXML();
    storeFormatted(xml, path);
  }

  public String storeFormatted(String xml, String path) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    DocumentBuilder db;
    try {
      StringReader sr = new StringReader(xml);
      InputSource is = new InputSource(sr);
      db = dbf.newDocumentBuilder();
      Document doc = db.parse(is);
      return storeFormatted(doc, path);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public String storeFormatted(Document xml, String path) throws Exception {
    Transformer tf = TransformerFactory.newInstance().newTransformer();
    tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    tf.setOutputProperty(OutputKeys.INDENT, "yes");
    tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    Writer out = new StringWriter();
    tf.transform(new DOMSource(xml), new StreamResult(out));
    String xmlAsString = out.toString().trim();
    if (!xmlAsString.isEmpty()) {
      if (path == null) {
        System.out.println(xmlAsString);
      }
      else {
        try {
          FileWriter fw = new FileWriter(path);
          fw.write(xmlAsString);
          fw.close();
        }
        catch (Exception e) {}
      }
    }
    return xmlAsString;
  }

}
