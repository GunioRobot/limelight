package limelight.ui.model;

import limelight.styles.Style;
import limelight.ui.Panel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MockRootPanel extends MockPropablePanel implements RootPanel
{
  public void destroy()
  {
  }

  public void setFrame(PropFrame frame)
  {
  }

  public boolean hasPanelsNeedingLayout()
  {
    return false;
  }

  public boolean hasDirtyRegions()
  {
    return false;
  }

  public void getAndClearPanelsNeedingLayout(ArrayList<Panel> panelBuffer)
  {
  }

  public void getAndClearDirtyRegions(ArrayList<Rectangle> regionBuffer)
  {
  }

  public void addDirtyRegion(Rectangle bounds)
  {
  }

  public boolean isAlive()
  {
    return false;
  }

  public Map<String, Style> getStyles()
  {
    return null;
  }
}
