package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

import uk.ac.qub.eeecs.game.RiskGame.Field;
import uk.ac.qub.eeecs.game.RiskGame.Player;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class FieldClassTests {


    private Context context;
    private Field Incrementer = new Field(1, "Incrementer", 1);
    private Field Attacker = new Field(2, "Attacker", 2);
    private Field Defender = new Field(3, "Defender", 3);
    private Player Player1 = new Player("Peter", 1);
    private Player Player2 = new Player("John", 2);



    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        Attacker.setPlayer(Player1);
        Defender.setPlayer(Player2);
    }

    @Test
    public void TestIncrement(){
        int previousNumOfTeams = Incrementer.getFNumOfTeams();
        Incrementer.incrementNumOfTeams();
        assertEquals(previousNumOfTeams + 1, Incrementer.getFNumOfTeams());
    }

    @Test
    public void TestHostileTakeover(){
        Defender.hostileTakeOver(Attacker.getFPlayer(), Attacker.getFNumOfTeams()-1);
        assertTrue((Defender.getFPlayer() == Attacker.getFPlayer()) && (Defender.getFNumOfTeams() == Attacker.getFNumOfTeams()-1));
    }

    @Test
    public void TestGetColour(){

    }
}