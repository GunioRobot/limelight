//- Copyright © 2008-2010 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.ui.model.inputs.offsetting;

import limelight.ui.model.inputs.TextModel;

public interface YOffsetStrategy
{
  YOffsetStrategy STATIONARY = new StationaryYOffsetStrategy();
  YOffsetStrategy FITTING = new FittingYOffsetStrategy();

  int calculateYOffset(TextModel model);
}
