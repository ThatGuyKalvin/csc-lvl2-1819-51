package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;

import uk.ac.qub.eeecs.gage.engine.AssetManager;

import uk.ac.qub.eeecs.game.RiskGame.Field;
import uk.ac.qub.eeecs.game.RiskGame.Player;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class FieldClassTests {


    private Context context;
    private Field Incrementer = new Field(50, 50, 50, 50, null, null, 1, "AI", new Player("Test", 1), 5);
    private Field Attacker = new Field(50, 50, 50, 50, null, null, 2, "AI", new Player("Test", 1), 5);
    private Field Defender = new Field(50, 50, 50, 50, null, null, 3, "AI", new Player("Test", 1), 5);


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void TestIncrement(){
        int previousNumOfTeams = Incrementer.getFNumOfTeams();
        Incrementer.incrementNumOfTeams();
        assertEquals(previousNumOfTeams + 1, Incrementer.getFNumOfTeams());
    }

    @Test
    public void TestHostileTakeover(){

    }


}