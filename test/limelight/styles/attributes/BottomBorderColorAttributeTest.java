//- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
//- Limelight and all included source files are distributed under terms of the MIT License.

package limelight.styles.attributes;

import limelight.styles.compiling.DimensionAttributeCompiler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BottomBorderColorAttributeTest extends Assert
{
  private BottomBorderColorAttribute attribute;

  @Before
  public void setUp() throws Exception
  {
    attribute = new BottomBorderColorAttribute();
  }

  @Test
  public void shouldCreation() throws Exception
  {
    assertEquals("Bottom Border Color", attribute.getName());
    assertEquals("color", attribute.getCompiler().type);
    assertEquals("#000000ff", attribute.getDefaultValue().toString());
  }
}
