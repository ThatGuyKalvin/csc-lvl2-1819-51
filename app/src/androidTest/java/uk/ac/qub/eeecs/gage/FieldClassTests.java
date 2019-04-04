package uk.ac.qub.eeecs.gage;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

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
    private Field field1 = new Field(4, "Tester", 4);



    @Before
    public void setUp() {
        Attacker.setPlayer(Player1);
        Defender.setPlayer(Player2);
    }

    @Test
    public void TestIncrement(){
        int previousNumOfTeams = Incrementer.getNumOfTeams();
        Incrementer.incrementNumOfTeams();
        assertEquals(previousNumOfTeams + 1, Incrementer.getNumOfTeams());
    }

    @Test
    public void TestHostileTakeover(){
        Attacker.incrementNumOfTeams();
        Defender.hostileTakeOver(Attacker.getPlayer(), Attacker.getNumOfTeams()-1);
        assertTrue((Defender.getPlayer() == Attacker.getPlayer()) && (Defender.getNumOfTeams() == Attacker.getNumOfTeams()-1));
    }

    @Test
    public void TestGetName()
    {
        assertEquals(field1.getName(), "Tester");
    }

    @Test
    public void TestGetNum()
    {
        assertEquals(field1.getNum(), 4);
    }

    @Test
    public void TestGetColour()
    {
        assertEquals(field1.getColour(), 4);
    }

}