package limelight.ui;

import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;

import limelight.ui.Style;
import limelight.ui.TextPane;

public class TextPaneTest extends TestCase
{
  private TextPane pane;
  private limelight.ui.Rectangle bounds;
  private MockBlock block;
  private Style style;
  private JFrame frame;
  private MockPanel panel;

  public void setUp() throws Exception
  {
    TextPane.widthPadding = 0;
    bounds = new limelight.ui.Rectangle(0, 0, 100, 100);
    panel = new MockPanel();
    panel.rectangleInsidePadding = bounds;
    style = panel.getStyle();
    pane = new TextPane(panel, "Some Text");
    style.setTextColor("black");
  }

  public void tearDown()
  {
    if(frame != null)
      frame.setVisible(false);
  }

  public void testConstructor() throws Exception
  {
    assertEquals(bounds, pane.getBounds());
    assertEquals(panel, pane.getPanel());
    assertEquals("Some Text", pane.getText());
  }

  public void testPreferredSize() throws Exception
  {
    useFrame();
    pane.doLayout();
    Dimension size = pane.getPreferredSize();
    assertEquals(50, size.width);
    assertEquals(11, size.height);
  }

  public void testPreferredSizeWithMoreText() throws Exception
  {
    useFrame();
    pane.setText("Once upon a time, there was a developer working on a tool called Limelight.");
    pane.doLayout();
    Dimension size = pane.getPreferredSize();
    assertEquals(98, size.width);
    assertEquals(57, size.height);
  }

  public void testPreferredSizeWithBigFontSize() throws Exception
  {
    useFrame();
    style.setFontSize("40");  
    pane.doLayout();
    Dimension size = pane.getPreferredSize();
    assertEquals(79, size.width);
    assertEquals(138, size.height);
  }

  public void testDimnsionsWhenLastLineIsLongest() throws Exception
  {
    useFrame();
    pane.setText("1\n2\n3\nlongest");
    pane.doLayout();
    Dimension size = pane.getPreferredSize();
    assertEquals(34, size.width);
    assertEquals(46, size.height);
  }

  private void useFrame()
  {
    frame = new JFrame();
    frame.add(pane);
    frame.setVisible(true);
  }

  public void testTextChanged() throws Exception
  {
    assertFalse(pane.textChanged());

    pane.setText("Something");
    assertTrue(pane.textChanged());

    pane.flushChanges();
    pane.setText("Something");
    assertFalse(pane.textChanged());

    pane.setText("Something Else");
    assertTrue(pane.textChanged());

    pane.flushChanges();
    assertFalse(pane.textChanged());
  }
}
