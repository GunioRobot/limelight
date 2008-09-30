package limelight.styles.abstrstyling;

import limelight.util.Util;

public class StringAttribute implements StyleAttribute
{
  private String stringValue;

  public StringAttribute(String value)
  {
    this.stringValue = value;
  }

  public String toString()
  {
    return stringValue;
  }

  public boolean equals(Object obj)
  {
    if(obj instanceof StringAttribute)
      return Util.equal(stringValue, ((StringAttribute) obj).stringValue);
    else
      return false;
  }

  public String getValue()
  {
    return stringValue;
  }
}
