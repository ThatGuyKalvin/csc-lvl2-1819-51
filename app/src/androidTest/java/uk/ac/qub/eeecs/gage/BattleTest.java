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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class BattleTest {

    private Context context;
    int attDice = 3;
    int defDice = 2;
    int TeamsAttacking = 5;
    int teamsDefending = 4;

    Battle battle = new Battle(attDice,defDice, TeamsAttacking, teamsDefending);

    @Before
    public void setUp() { context = InstrumentationRegistry.getTargetContext(); }


    @Test
    public void can_battle_success() {
        battle = new Battle(3 ,defDice, 4, teamsDefending);
        assertTrue(battle.canBattle());
    }

    @Test
    public void can_battle_fail() {
        battle = new Battle(3 ,defDice, 3, teamsDefending);
        assertFalse(battle.canBattle());
    }


    @Test
    public void DiceResultsAtt_returns_correct_number_of_results_success() {

        battle = new Battle(attDice,defDice, TeamsAttacking, teamsDefending);
        battle.newRoll();
        boolean success = false;

        if(attDice == battle.getDiceResultsAtt().length )
            success = true;


        assertTrue(success);
    }


    @Test
    public void diceReset_success() {
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

        battle = new Battle(attDice,defDice, TeamsAttacking, teamsDefending);
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