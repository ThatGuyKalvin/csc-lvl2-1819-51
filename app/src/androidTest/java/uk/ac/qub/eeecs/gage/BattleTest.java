package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.RiskGame.Battle;
import uk.ac.qub.eeecs.game.RiskGame.DiceRoll;
import uk.ac.qub.eeecs.game.RiskGame.Field;
import uk.ac.qub.eeecs.game.RiskGame.Player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class BattleTest {


    private Context context;



    Player google = new Player("google", -01000016);
    Player apple = new Player("apple", -01000016);

    Field field1 = new Field(19, "Social Media",0xFF8B1D8F);
    Field field2 = new Field(20, "Research Labs",0xFF8F0081);

    public void setTeams(int attackers, int defenders){
        field1.setNumOfTeams(attackers);
        field2.setNumOfTeams(defenders);
    }

    Battle battle = new Battle(field1, field2);


    @Before
    public void setUp() { context = InstrumentationRegistry.getTargetContext(); }


    @Test
    public void can_battle_success() {
        setTeams(4, 10);
        battle = new Battle(field1, field2);
        assertTrue(battle.canBattle());
    }

    @Test
    public void can_battle_fail() {
        setTeams(2, 3);
        battle.setNumOfDiceAtt(3);
        assertFalse(battle.canBattle());
    }


    @Test
    public void DiceResultsAtt_returns_correct_number_of_results_success() {
        setTeams(5, 5);
        battle = new Battle(field1, field2);
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        battle.resetDice();
        battle.newRoll();
        boolean success = false;

        if(battle.getDiceResultsAtt().length == 3 )
            success = true;

        assertTrue(success);
    }

    public void DiceResultsAtt_returns_correct_number_of_results_fail() {
        setTeams(3, 5);
        battle = new Battle(field1, field2);
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        battle.resetDice();
        battle.newRoll();
        boolean success = false;

        if(battle.getDiceResultsAtt().length == 3 )
            success = true;

        assertFalse(success);
    }


    @Test
    public void diceReset_success() {
        battle.newRoll();
        battle.resetDice();
        for(int i =0; i < battle.getDiceResultsAtt().length; i++){
            if(battle.getDiceResultsAtt()[i] != 0)
                fail();
        }
        assertTrue(true);
    }

    @Test
    public void newRoll_success(){
        battle.newRoll();
        for(int i =0; i < battle.getDiceResultsAtt().length; i++){
            if(battle.getDiceResultsAtt()[i] == 0)
                fail();
        }
        for(int i =0; i < battle.getDiceResultsDef().length; i++){
            if(battle.getDiceResultsDef()[i] == 0)
                fail();
        }
        assertTrue(true);
    }

    @Test
    public void getTotal_success() {
        setTeams(10, 10);
        battle = new Battle(field1, field2);
        battle.newRoll();
        int total = 0;

        for(int i =0; i< battle.getDiceResultsAtt().length; i++){
            total += battle.getDiceResultsAtt()[i];
        }

        if(total == battle.getAttDiceTotal()){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
    }
}