//@Author: Kalvin Johnston
package uk.ac.qub.eeecs.gage;
import android.content.Context;
import android.graphics.Color;
import org.junit.Test;
import uk.ac.qub.eeecs.game.RiskGame.Player;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Context Context;
    private Player p1 = new Player("TestName", Color.BLUE);

    @Test
    public void getNameTest() {
        assertEquals("TestName", p1.getName());
    }

    @Test
    public void setNameTest() {
        p1.setName("Testing");
        assertEquals("Testing", p1.getName());
    }

    @Test
    public void getColourTest() {
        assertEquals(Color.BLUE, p1.getColour());
    }

    @Test
    public void setColourTest() {
        p1.setColour(0xFFeb1A11);
        assertEquals(0xFFeb1A11, p1.getColour());
    }

    @Test
    public void incrementCardsTest() {
        // This method also tests getNumOfRiskCards();
        assertEquals(0, p1.getNumOfRiskCards());
        p1.incrementRiskCards();
        assertEquals(1, p1.getNumOfRiskCards());
        p1.incrementRiskCards();
        assertEquals(2, p1.getNumOfRiskCards());
    }

    @Test
    public void useCardsTest() {
        p1.incrementRiskCards();
        p1.incrementRiskCards();
        p1.incrementRiskCards();
        assertEquals(3, p1.getNumOfRiskCards());
        p1.useRiskCards();
        assertEquals(0, p1.getNumOfRiskCards());
    }

}