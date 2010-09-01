package limelight.ui.model.inputs.painting;

import com.android.ninepatch.NinePatch;
import limelight.styles.HorizontalAlignment;
import limelight.styles.VerticalAlignment;
import limelight.styles.values.SimpleHorizontalAlignmentValue;
import limelight.styles.values.SimpleVerticalAlignmentValue;
import limelight.ui.model.TextPanel;
import limelight.ui.model.inputs.ComboBoxPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.io.IOException;

public class ComboBoxPainter
{
  private static NinePatch normalPatch;
  private static NinePatch focusPatch;
  private static final int BUTTON_WIDTH = 25;
  private static final int LEFT_PADDING = 8; //TODO MDM - Use style padding

  static
  {
    try
    {
      ClassLoader classLoader = ComboBoxPanel.class.getClassLoader();

      normalPatch = NinePatch.load(ImageIO.read(classLoader.getResource("limelight/ui/images/combo_box.9.png")), true, true);
      focusPatch = NinePatch.load(ImageIO.read(classLoader.getResource("limelight/ui/images/combo_box_focus.9.png")), true, true);
    }
    catch(IOException e)
    {
      throw new RuntimeException("Could not load ButtonPanel images", e);
    }
    catch(Exception e)
    {
      System.err.println("e = " + e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }


  private static Font font = new Font("Arial", Font.BOLD, 12);
  private static SimpleHorizontalAlignmentValue horizontalTextAlignment = new SimpleHorizontalAlignmentValue(HorizontalAlignment.LEFT);
  private static SimpleVerticalAlignmentValue verticalTextAlignment = new SimpleVerticalAlignmentValue(VerticalAlignment.CENTER);

  public static void paintOn(Graphics2D graphics, ComboBoxPanel panel)
  {
    if(panel.hasFocus())
      focusPatch.draw(graphics, 0, 0, panel.getWidth(), panel.getHeight());
    normalPatch.draw(graphics, 0, 0, panel.getWidth(), panel.getHeight());

    if(panel.getSelectedOption() != null)
    {
      graphics.setColor(Color.BLACK);

      TextLayout textLayout = new TextLayout(panel.getSelectedOption().toString(), font, TextPanel.staticFontRenderingContext);
      int height = (int) ((textLayout.getAscent() + textLayout.getDescent() + textLayout.getLeading()) + 0.5);
      int width = (int) (textLayout.getBounds().getWidth() + textLayout.getBounds().getX() + 0.5);
      final Dimension textDimensions = new Dimension(width, height);

      int textX = horizontalTextAlignment.getX(textDimensions.width, panel.getBoundingBox()) + LEFT_PADDING;
      float textY = verticalTextAlignment.getY(textDimensions.height, panel.getBoundingBox()) + textLayout.getAscent();
      textLayout.draw(graphics, textX, textY + 1);
    }
  }

}
