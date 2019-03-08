package uk.ac.qub.eeecs.gage;

import org.junit.Test;

import android.content.Context;
import uk.ac.qub.eeecs.game.RiskGame.Area;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AreaTest {

    private Context Context;

    private Area a1 = new Area("Telecommunications", 0xFFeb1c23);

    @Test
    private void getNameTest() {
        assertEquals("Telecommunications", a1.getName());
    }

    @Test
    private void setNameTest() {
        a1.setName("Testing");
        assertEquals("Testing", a1.getName());
    }
}


