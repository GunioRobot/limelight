//- Copyright 2008 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs;

import junit.framework.TestCase;
import limelight.ui.model.RootPanel;
import limelight.ui.model.MockFrame;

import java.awt.*;

public class ComboBoxTest extends TestCase
{
  private ComboBoxPanel panel;
  private ComboBox comboBox;
  private RootPanel root;

  public void setUp() throws Exception
  {
    root = new RootPanel(new MockFrame());
    panel = new ComboBoxPanel();
    comboBox = new ComboBox(panel);
    root.setPanel(panel);
  }

  public void testRepaint() throws Exception
  {
    comboBox.repaint();

    assertEquals(true, root.dirtyRegionsContains(panel.getAbsoluteBounds()));
  }

  public void testRapaintWithParams() throws Exception
  {
    comboBox.repaint(1, 2, 3, 4);

    assertEquals(true, root.dirtyRegionsContains(new Rectangle(1, 2, 3, 4)));
  }

  public void testRapaintWithRectangle() throws Exception
  {
    comboBox.repaint(new Rectangle(1, 2, 3, 4));

    assertEquals(true, root.dirtyRegionsContains(new Rectangle(1, 2, 3, 4)));
  }

}
