//@Author: Kalvin Johnston

package uk.ac.qub.eeecs.gage;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

import uk.ac.qub.eeecs.game.RiskGame.Area;
import uk.ac.qub.eeecs.game.RiskGame.Field;
import uk.ac.qub.eeecs.game.RiskGame.Player;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class AreaTest {

    private Context Context;

    private Area a1 = new Area("Telecommunications", 0xFFeb1c23, 1);

    @Test
    public void getNameTest() {
        assertEquals("Telecommunications", a1.getName());
    }

    @Test
    public void setNameTest() {
        a1.setName("Testing");
        assertEquals("Testing", a1.getName());
    }

    @Test
    public void getColourTest() {
        assertEquals(0xFFeb1c23, a1.getColour());
    }

    @Test
    public void setColourTest() {
        a1.setColour(0xFFeb1A11);
        assertEquals(0xFFeb1A11, a1.getColour());
    }

    @Test
    public void addFieldTest() {
        Field testField = new Field(1,"Internet Provider",0xFFDE7879);
        a1.addField(testField);
        assertEquals(testField, a1.getField(0));
    }
}


