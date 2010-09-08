package limelight.ui.model.inputs.keyProcessors;


import limelight.ui.events.KeyEvent;
import limelight.ui.text.TextLocation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectionOnAltCmdKeyProcessorTest extends AbstractKeyProcessorTest
{
  @Before
  public void setUp()
  {
    setUpSingleLine();
    model.startSelection(TextLocation.at(0, 4));
    processor = SelectionOnAltCmdKeyProcessor.instance;
    modifiers = 12;
  }

  @Test
  public void canProcessCharacterAndDoNothing()
  {
    processor.processKey(press(KeyEvent.KEY_A), model);

    assertEquals(1, model.getCaretIndex());
    assertEquals(4, model.getSelectionIndex());
    assertEquals(true, model.isSelectionActivated());
  }

}
