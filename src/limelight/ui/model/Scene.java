//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model;

import limelight.model.Production;
import limelight.model.Stage;
import limelight.model.api.PropProxy;
import limelight.styles.RichStyle;
import limelight.ui.Panel;
import java.awt.*;
import java.util.Collection;
import java.util.Map;

public interface Scene extends Panel, ParentPanel
{

  void setStage(Stage frame);

  boolean hasPanelsNeedingLayout();

  boolean hasDirtyRegions();

  void getAndClearPanelsNeedingLayout(Collection<Panel> panelBuffer);

  void getAndClearDirtyRegions(Collection<Rectangle> regionBuffer);

  void addDirtyRegion(Rectangle bounds);

  public Map<String, RichStyle> getStylesStore();

  void addPanelNeedingLayout(Panel panel);

  Stage getStage();

  void setCursor(Cursor cursor);

  Cursor getCursor();

  ImageCache getImageCache();

  void addToIndex(PropPanel prop);

  void removeFromIndex(PropPanel prop);

  Production getProduction();

  void setProduction(Production production);

  boolean shouldAllowClose();

  boolean isVisible();

  PropProxy getProxy();
}


