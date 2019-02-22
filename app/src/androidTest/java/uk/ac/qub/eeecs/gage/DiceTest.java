package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.game.RiskGame.DiceRoll;


import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DiceTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void loadAndAddBitmap_Dice1Selected_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Dice1Selected", "img/dice/1Selected.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Dice2Selected_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Dice2Selected", "img/dice/2Selected.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Dice3Selected_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Dice3Selected", "img/dice/3Selected.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_ChooseDiceNumber1_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "ChooseDiceNumber1", "img/dice/ChooseDiceNumber1.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ChooseDiceNumber2_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "ChooseDiceNumber2", "img/dice/ChooseDiceNumber2.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ChooseDiceNumber3_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "ChooseDiceNumber3", "img/dice/ChooseDiceNumber3.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Roll1_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Roll1", "img/dice/Roll1.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Roll2_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Roll2", "img/dice/Roll2.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Background_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Background", "img/dice/DiceRollScreenBackGround.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_abortHack_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "AbortHack", "img/dice/AbortHack.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_abortHackSelected_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "AbortHackSelected", "img/dice/AbortHackSelected.png");
        assertTrue(success);
    }

    @Test
    public void MaxNumber_Success() {

        DiceRoll diceRoll = new DiceRoll(100);
        int max = 1;
        for (int i = 0; i < diceRoll.getDiceResults().length; i++) {
            if (diceRoll.getDiceResults()[i] > max) {
                max = diceRoll.getDiceResults()[i];
            }
        }
        if (max <= 6 ) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    //I wasn't too sure how to test randomness
    //but if each number occurs I assume it is okay.
    @Test
    public void NumbersAreRandom_Success(){
        int count[] = new int[6];

        DiceRoll diceRoll = new DiceRoll(1000);
        for (int i = 0; i < diceRoll.getDiceResults().length; i++){
            switch (diceRoll.getDiceResults()[i]){
                case 1 : count[0]++; break;
                case 2 : count[1]++; break;
                case 3 : count[2]++; break;
                case 4 : count[3]++; break;
                case 5 : count[4]++; break;
                case 6 : count[5]++; break;
            }
        }

        for(int i=0; i<count.length; i++){
            if(count[i] == 0){
                assertTrue(false);
            }else{
                assertTrue(true);
            }
        }
    }

    //animation and result sprites
    @Test
    public void loadAndAddBitmap_Number0_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number0", "img/dice/Results/Number0.png");
        assertTrue(success);
    }
    @Test
    public void loadAndAddBitmap_Number1_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number1", "img/dice/Results/Number1.png");
        assertTrue(success);
    }
    @Test
    public void loadAndAddBitmap_Number2_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number2", "img/dice/Results/Number2.png");
        assertTrue(success);
    }
    @Test
    public void loadAndAddBitmap_Number3_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number3", "img/dice/Results/Number3.png");
        assertTrue(success);
    }
    @Test
    public void loadAndAddBitmap_Number4_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number4", "img/dice/Results/Number4.png");
        assertTrue(success);
    }
    @Test
    public void loadAndAddBitmap_Number5_Success() {
        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number5", "img/dice/Results/Number5.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_Number6_Success() {
        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Number6", "img/dice/Results/Number6.png");
        assertTrue(success);
    }
}
