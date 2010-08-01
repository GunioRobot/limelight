package limelight.ui.model.inputs.painting;

import limelight.ui.model.inputs.ScrollBar2Panel;
import limelight.util.Box;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScrollBarPainter
{
  public static int OUTSIDE_CUSHION = 5;
  public static int INSIDE_CUSHION = 33;
  public static int MIN_GEM_SIZE = 16;

  public static ScrollBarImages horizontalImages;
  public static ScrollBarImages verticalImages;

  public static ScrollBarPainter instance = new ScrollBarPainter();

  public static class ScrollBarImages
  {
    public BufferedImage cap;
    public BufferedImage background;
    public BufferedImage buttons;
    public BufferedImage buttonsInsideSelected;
    public BufferedImage buttonsOutsideSelected;
    public BufferedImage gemCapInside;
    public BufferedImage gemCapOutside;
    public BufferedImage gemFiller;
  }

  static
  {
    try
    {
      ClassLoader classLoader = TextPanelBorderPainter.class.getClassLoader();

      horizontalImages = new ScrollBarImages();
      horizontalImages.background = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_background.png"));
      horizontalImages.buttons = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_buttons.png"));
      horizontalImages.buttonsInsideSelected = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_buttons_left_selected.png"));
      horizontalImages.buttonsOutsideSelected = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_buttons_right_selected.png"));
      horizontalImages.cap = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_cap.png"));
      horizontalImages.gemCapInside = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_gem_cap_right.png"));
      horizontalImages.gemCapOutside = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_gem_cap_left.png"));
      horizontalImages.gemFiller = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_horizontal_gem_filler.png"));

      verticalImages = new ScrollBarImages();
      verticalImages.background = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_background.png"));
      verticalImages.buttons = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_buttons.png"));
      verticalImages.buttonsInsideSelected = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_buttons_down_selected.png"));
      verticalImages.buttonsOutsideSelected = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_buttons_up_selected.png"));
      verticalImages.cap = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_cap.png"));
      verticalImages.gemCapInside = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_gem_cap_bottom.png"));
      verticalImages.gemCapOutside = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_gem_cap_top.png"));
      verticalImages.gemFiller = ImageIO.read(classLoader.getResource("limelight/ui/images/scroll_vertical_gem_filler.png"));
    }
    catch(IOException e)
    {
      throw new RuntimeException("Could not load TextPanel border images", e);
    }
    catch(Exception e)
    {
      System.err.println("e = " + e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public int getOutsideCusion()
  {
    return OUTSIDE_CUSHION;
  }

  public int getInsideCushion()
  {
    return INSIDE_CUSHION;
  }

  public int getMinGemSize()
  {
    return MIN_GEM_SIZE;
  }

  public void paintOn(Graphics2D graphics, ScrollBar2Panel scrollBar)
  {
    Box bounds = scrollBar.getBoundingBox();
    if(scrollBar.getOrientation() == ScrollBar2Panel.HORIZONTAL)
      drawHorizontally(graphics, bounds, scrollBar);
    else
      drawVertically(graphics, bounds, scrollBar);
  }

  private static void drawVertically(Graphics2D graphics, Box bounds, ScrollBar2Panel scrollBar)
  {
    ScrollBarImages images = verticalImages;
    for(int y = 0; y < bounds.height; y += images.background.getHeight())
      graphics.drawImage(images.background, 0, y, null);
    graphics.drawImage(images.cap, 0, 0, null);
    graphics.drawImage(images.buttons, 0, bounds.height - images.buttons.getHeight(), null);

    int y = OUTSIDE_CUSHION;
    graphics.drawImage(images.gemCapOutside, 0, y, null);
    y += images.gemCapOutside.getHeight();

    y = paintVerticalFiller(graphics, scrollBar, images, y);

    graphics.drawImage(images.gemCapInside, 0, y, null);
  }

  private static void drawHorizontally(Graphics2D graphics, Box bounds, ScrollBar2Panel scrollBar)
  {
    ScrollBarImages images = horizontalImages;
    for(int x = 0; x < bounds.width; x += images.background.getWidth())
      graphics.drawImage(images.background, x, 0, null);
    graphics.drawImage(images.cap, 0, 0, null);
    graphics.drawImage(images.buttons, bounds.width - images.buttons.getWidth(), 0, null);

    int x = OUTSIDE_CUSHION;
    graphics.drawImage(images.gemCapOutside, x, 0, null);
    x += images.gemCapOutside.getWidth();

    x = paintHorizontalFiller(graphics, scrollBar, images, x);

    graphics.drawImage(images.gemCapInside, x, 0, null);
  }

  private static int paintHorizontalFiller(Graphics2D graphics, ScrollBar2Panel scrollBar, ScrollBarImages images, int x)
  {
    int fillerSize = scrollBar.getGemSize() - images.gemCapOutside.getWidth() - images.gemCapInside.getWidth(); 
    if(fillerSize > 0)
    {
      Graphics fillerGraphics = graphics.create(x, 0, fillerSize, images.gemFiller.getHeight());
      int fillerWidth = images.gemFiller.getWidth();
      int fillerX = -(fillerWidth - (fillerSize % fillerWidth));
      while(fillerX < fillerSize)
      {
        fillerGraphics.drawImage(images.gemFiller, fillerX, 0, null);
        fillerX += fillerWidth;
      }
      x += fillerSize;
    }
    return x;
  }

  private static int paintVerticalFiller(Graphics2D graphics, ScrollBar2Panel scrollBar, ScrollBarImages images, int y)
  {
    int fillerSize = scrollBar.getGemSize() - images.gemCapOutside.getHeight() - images.gemCapInside.getHeight();
    if(fillerSize > 0)
    {
      Graphics fillerGraphics = graphics.create(0, y, images.gemFiller.getWidth(), fillerSize);
      int fillerHeight = images.gemFiller.getHeight();
      int fillerY = -(fillerHeight - (fillerSize % fillerHeight));
      while(fillerY < fillerSize)
      {
        fillerGraphics.drawImage(images.gemFiller, 0, fillerY, null);
        fillerY += fillerHeight;
      }
      y += fillerSize;
    }
    return y;
  }
}