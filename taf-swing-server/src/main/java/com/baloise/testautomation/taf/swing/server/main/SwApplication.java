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
import java.io.StringReader;
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
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.NodeList;

import com.baloise.testautomation.taf.common.interfaces.ISwApplication;
import com.baloise.testautomation.taf.common.interfaces.ISwElement;
import com.baloise.testautomation.taf.common.utils.TafProperties;
import com.baloise.testautomation.taf.swing.server.elements.ASwElement;
import com.baloise.testautomation.taf.swing.server.elements.SwButton;
import com.baloise.testautomation.taf.swing.server.elements.SwCell;
import com.baloise.testautomation.taf.swing.server.elements.SwCheckBox;
import com.baloise.testautomation.taf.swing.server.elements.SwColumnHeader;
import com.baloise.testautomation.taf.swing.server.elements.SwFrame;
import com.baloise.testautomation.taf.swing.server.elements.SwInput;
import com.baloise.testautomation.taf.swing.server.elements.SwInternalFrame;
import com.baloise.testautomation.taf.swing.server.elements.SwLabel;
import com.baloise.testautomation.taf.swing.server.elements.SwMenuItem;
import com.baloise.testautomation.taf.swing.server.elements.SwTabbedPane;
import com.baloise.testautomation.taf.swing.server.elements.SwTable;
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

  public ISwElement<Component> get(long tid) {
    return components.get(tid);
  }

  public void setRoot() {
    Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    root = SwingUtilities.getRoot(c);
  }

  private ISwElement<Component> putComponent(Component c) {
    ISwElement<Component> se = getSwElement(counter, c);
    components.put(counter, se);
    counter++;
    return se;
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

  // private int level = 0;

  private String asValidAttribute(String s) {
    return s.replace("&", "&amp;");
  }

  public void allCellsToXML(StringBuilder xml, JTable table) {
    for (int c = 0; c < table.getColumnCount(); c++) {
      xml.append("<header tid = \"" + counter + "\" col = \"" + c + "\" value = \""
          + asValidAttribute(table.getColumnModel().getColumn(c).getHeaderValue().toString()) + "\"></header>");
      components.put(counter, new SwColumnHeader(counter, c, table));
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

  private int timeoutInSeconds = 60;

  @Override
  public ISwElement<Component> findElementByXpath(Long root, String s) {
    List<ISwElement<Component>> elements = findElementsByXpath(root, s);
    if (elements.size() > 0) {
      return elements.get(0);
    }
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

  public String allComponentsToXML(Component c) {
    StringBuilder xml = new StringBuilder();
    components = new Hashtable<Long, ISwElement<Component>>();
    counter = 0;
    allComponentsToXML(xml, c);
    return xml.toString();
  }

  private String toFullXML() {
    setRoot();
    return allComponentsToXML(root);
  }

  private String toMappedXML() {
    return toFullXML();
  }

  // TODO

  @Override
  public TafProperties execCommand(String type, String command, TafProperties props) {
    if (type.equalsIgnoreCase(ISwApplication.type)) {
      if (command.equalsIgnoreCase(Command.findelementbyxpath.toString())) {
        try {
          ASwElement element = (ASwElement)findElementByXpath(props.getLong("root"), props.getString("xpath"));
          TafProperties result = new TafProperties();
          result.putObject(new Long(element.getTID()).toString(), element.getType());
          return result;
        }
        catch (Exception e) {}
      }
      if (command.equalsIgnoreCase(Command.findelementsbyxpath.toString())) {
        try {
          List<ISwElement<Component>> elements = findElementsByXpath(props.getLong("root"), props.getString("xpath"));
          TafProperties result = new TafProperties();
          for (ISwElement<Component> element : elements) {
            result.putObject(new Long(((ASwElement)element).getTID()).toString(), element.getType());
          }
          return result;
        }
        catch (Exception e) {}
      }
      if (command.equalsIgnoreCase(Command.execute.toString())) {
        try {
          ISwElement<Component> swElement = components.get(props.getLong("tid"));
          if (swElement.getType().equalsIgnoreCase(type)) {
            return ((ASwElement)swElement).execCommand(props);
          }
        }
        catch (Exception e) {}
      }
    }
    TafProperties result = new TafProperties();
    result.putObject("error", "something went wrong when executing command " + command);
    return result;
  }

  @Override
  public void startJNLPInstrumentation(String url) {}

  @Override
  public ISwElement<Component> find(ISwElement<Component> root, Annotation annotation) {
    return null;
  }

  /** 
   * {@inheritDoc}
   */
  @Override
  public ISwElement<Component> find(Annotation annotion) {
    return null;
  }


}
