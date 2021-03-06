//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.model.api;

import limelight.model.Stage;

import java.util.Map;

public interface TheaterProxy
{
  Stage buildStage(String name, Map<String, Object> options);
}
