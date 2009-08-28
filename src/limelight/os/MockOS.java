//- Copyright � 2008-2009 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the GNU LGPL.

package limelight.os;

public class MockOS extends OS
{
  protected void turnOnKioskMode()
  {
  }

  protected void turnOffKioskMode()
  {
  }

  protected void launch(String URL)
  {
  }

  public void configureSystemProperties()
  {
    System.setProperty("jruby.shell", "silly shell");
    System.setProperty("jruby.script", "sticky script");
  }
}
