//- Copyright � 2008-2009 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model;

import limelight.Context;

import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.util.HashSet;

public class AlertFrameManager implements WindowFocusListener, WindowListener, FrameManager
{
  private StageFrame activeFrame;
  private final HashSet<StageFrame> frames;

  public AlertFrameManager()
  {
    frames = new HashSet<StageFrame>();
  }

  public void windowGainedFocus(WindowEvent e)
  {
    Window window = e.getWindow();  
    if(window instanceof Frame)
    {
      activeFrame = ((StageFrame)window);

      Context.instance().keyboardFocusManager.focusFrame((Frame)window);
//      theater.stage_activated(frame.getStage());
    }
  }

  public void windowLostFocus(WindowEvent e)
  {
  }

  public void watch(StageFrame frame)
  {
    if(!frames.contains(frame))
    {
      frame.addWindowFocusListener(this);
      frame.addWindowListener(this);
      frames.add(frame);
    }
  }

  public void windowOpened(WindowEvent e)
  {
  }

  public void windowClosing(WindowEvent e)
  {
  }

  public void windowClosed(WindowEvent e)
  {
    StageFrame frame = ((StageFrame)e.getWindow());
    frames.remove(frame);
    if(frames.isEmpty() || allFramesHidden())
      Context.instance().shutdown();
  }

  private boolean allFramesHidden()
  {
    for(StageFrame frame : frames)
    {
      if(frame.isVisible())
        return false;
    }
    return true;
  }

  public void windowIconified(WindowEvent e)
  {
  }

  public void windowDeiconified(WindowEvent e)
  {
  }

  public void windowActivated(WindowEvent e)
  {
  }

  public void windowDeactivated(WindowEvent e)
  {
  }

  public StageFrame getActiveFrame()
  {
    return activeFrame;
  }

  public boolean isWatching(StageFrame frame)
  {
    boolean found = false;
    for(WindowFocusListener listener : frame.getWindowFocusListeners())
    {
      if(listener == this)
        found = true;
    }
    return found;
  }

  public int getFrameCount()
  {
    return frames.size();
  }

  public void closeAllFrames()
  {
    for(StageFrame frame : frames)
      frame.close();
  }
}