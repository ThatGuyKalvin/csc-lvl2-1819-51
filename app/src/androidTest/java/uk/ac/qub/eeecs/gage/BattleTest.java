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

    private Field field1 = new Field(19, "Social Media",0xFF8B1D8F);
    private Field field2 = new Field(20, "Research Labs",0xFF8F0081);
    private Battle battle = new Battle(field1, field2);

    private void setTeams(int attackers, int defenders){
        field1.setNumOfTeams(attackers);
        field2.setNumOfTeams(defenders);
    }

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

    @Test
    public void fastBattle_Success(){
        boolean success = false;
        setTeams(10, 10);
        battle = new Battle(field1, field2);
        battle.newRoll();

        battle.fastBattle();
        if(field1.getFNumOfTeams() == 1 || field2.getFNumOfTeams() == 0){
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void attackerWin_Success(){
        boolean success = false;
        //virtually impossible for the defenders to win.
        setTeams(1000, 1);
        battle = new Battle(field1, field2);
        battle.newRoll();
        battle.fastBattle();
        if(battle.attackersWin() == true){
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void attackerWin_Fail(){
        boolean success = true;
        //virtually impossible for the attackers to win.
        setTeams(1, 1000);
        battle = new Battle(field1, field2);
        battle.newRoll();
        battle.fastBattle();
        if(battle.attackersWin() == false){
            success = false;
        }
        assertFalse(success);
    }

    @Test
    public void dice_auto_set_success(){
        boolean success = true;

        setTeams(2, 1);
        battle = new Battle(field1, field2);
        battle.newRoll();
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        int length  = battle.getDiceResultsAtt().length;
        if(length != 1)
            success = false;
        if(battle.getDiceResultsDef().length != 1)
            success = false;

        setTeams(2, 2);
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        battle.newRoll();
        length  = battle.getDiceResultsAtt().length;
        if(length != 1)
            success = false;
        if(battle.getDiceResultsDef().length != 1)
            success = false;


        //it fails from here for some reason.
        setTeams(3, 3);
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        battle.newRoll();
        length  = battle.getDiceResultsAtt().length;
        if(length != 2)
            success = false;
        if(battle.getDiceResultsDef().length != 2)
            success = false;

        setTeams(4, 4);
        battle = new Battle(field1, field2);
        battle.autoSetNumOfDiceAtt();
        battle.setNumOfDiceDef();
        battle.newRoll();
        length  = battle.getDiceResultsAtt().length;
        if(length != 3)
            success = false;
        if(battle.getDiceResultsDef().length != 2)
            success = false;

        assertTrue(success);
    }




}