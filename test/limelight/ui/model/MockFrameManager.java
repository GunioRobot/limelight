//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.ui.model;

import java.util.ArrayList;

public class MockFrameManager implements FrameManager
{
  public StageFrame focusedFrame;
  public boolean allFramesClosed;

  public void watch(StageFrame frame)
  {
  }

  public StageFrame getActiveFrame()
  {
    return focusedFrame;
  }

  public void getVisibleFrames(ArrayList<StageFrame> result)
  {
    if(focusedFrame != null)
      result.add(focusedFrame);
  }

  public boolean isWatching(StageFrame frame)
  {
    return false;
  }

  public int getFrameCount()
  {
    return 0;
  }

  public void closeAllFrames()
  {
    allFramesClosed = true;
  }
}
