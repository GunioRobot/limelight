package limelight.ui.model2;

import limelight.LimelightError;

public class SterilePanelException extends LimelightError
{
  SterilePanelException(String name)
  {
    super("The panel for prop named '" + name + "' has been sterilized. Child components may not be added.");
  }
}
