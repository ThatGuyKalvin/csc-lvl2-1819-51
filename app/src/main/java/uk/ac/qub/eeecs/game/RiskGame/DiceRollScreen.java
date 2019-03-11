package uk.ac.qub.eeecs.game.RiskGame;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.List;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

public class DiceRollScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    private Bitmap background;
    private int numOfDice = 0;

    private boolean showResults = false;
    private boolean showAnimation = true;

    //buttons
    private PushButton mAbortHack;
    private PushButton mRollDiceButton;
    private PushButton mFastBattle;

    private PushButton oneDice;
    private PushButton twoDice;
    private PushButton threeDice;

    private PushButton oneDiceSelected;
    private PushButton twoDiceSelected;
    private PushButton threeDiceSelected;

    private PushButton twoDiceNull;
    private PushButton threeDiceNull;
    private PushButton twoDiceSelectedNull;
    private PushButton threeDiceSelectedNull;


    //Animation
    private Rolling mDiceRolls1;
    private Rolling mDiceRolls2;
    private Rolling mDiceRolls3;
    private Rolling mDiceRolls4;
    private Rolling mDiceRolls5;

    //private WinnerAnimation winnerAnimation;

    //Result sprites
    private DiceResult mDiceResult1;
    private DiceResult mDiceResult2;
    private DiceResult mDiceResult3;
    private DiceResult mDiceResult4;
    private DiceResult mDiceResult5;

    //properties
    Battle battle;
    Player attacker;
    Player defender;

    private ScreenViewport mGameScreenViewport;
    private Paint textPaint = new Paint();
    private float mTimeToChange = 0;

    public DiceRollScreen(Game game, Battle bat, Player att, Player def) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets("txt/assets/DiceRollScreenAssets.JSON");

        battle = bat;
        attacker = att;
        defender = def;
        background = assetManager.getBitmap("RiskRulesScreenBackground");

        battle.resetDice();
        DrawDiceButtons();
        createCalculateAnimation();

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        mGameScreenViewport = new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update

        // This is the process for the animation when the roll button is pressed
        if (showAnimation) {
            if (mRollDiceButton.isPushed()) {
                mDiceRolls1.update(elapsedTime);
                mDiceRolls2.update(elapsedTime);
                mDiceRolls3.update(elapsedTime);
                mDiceRolls4.update(elapsedTime);
                mDiceRolls5.update(elapsedTime);
            }
        }

        if (battle.noArmies())
            mGame.getScreenManager().removeScreen(this);

        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            mRollDiceButton.update(elapsedTime);
            mFastBattle.update(elapsedTime);
            oneDice.update(elapsedTime);
            twoDice.update(elapsedTime);
            threeDice.update(elapsedTime);
            oneDiceSelected.update(elapsedTime);
            twoDiceSelected.update(elapsedTime);
            threeDiceSelected.update(elapsedTime);
            twoDiceSelectedNull.update(elapsedTime);
            threeDiceSelectedNull.update(elapsedTime);

            mAbortHack.update(elapsedTime);
            if (mAbortHack.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

            if(mFastBattle.isPushTriggered())
                battle.fastBattle();

            //sets the number of dice that the user will roll
            numberOfDiceToRoll();
            Roll();
            results();
        }

        mTimeToChange += elapsedTime.stepTime;
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLACK);

        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        //drawing the RiskRulesScreenBackground to the screen.
        Rect sourceRect = new Rect(0, 0, background.getWidth(), background.getHeight());
        Rect destRect = new Rect((int) (screenWidth * 0.0f), (int) (screenHeight * 0.0f), (int) (screenWidth * 1.0f), (int) (screenHeight * 1.0f));
        graphics2D.drawBitmap(background, sourceRect, destRect, null);

        mAbortHack.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRollDiceButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mFastBattle.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        oneDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        twoDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        threeDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


        if (showAnimation) {
            mDiceRolls1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls4.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls5.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        if (showResults) {
            mDiceResult1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult4.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult5.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Depending on what the user has selected,
        //the number of dice that has been selected is drawn over the other push-button.
        switch (numOfDice) {
            case 1:
                oneDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 2:
                if(battle.canBattle())
                    twoDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 3:
                if(battle.canBattle())
                    threeDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
        }

        //draws the grayed out version of the dice to show that they wont roll.
        switch (battle.getNumOfAttTeams()){
            case 3 :
                threeDiceSelectedNull.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 2 :
                threeDiceSelectedNull.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                twoDiceSelectedNull.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
        }


        //Details to be shown on the screen
        float lineHeight = screenHeight / 20.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(-01000016);

        graphics2D.drawText(attacker.getName(), screenWidth * 0.08f, lineHeight + 100.0f, textPaint);
        graphics2D.drawText(defender.getName(), screenWidth * 0.78f, lineHeight + 100.0f, textPaint);

        graphics2D.drawText("Teams: " + battle.getNumOfAttTeams(), screenWidth * 0.08f, lineHeight + 300.0f, textPaint);
        graphics2D.drawText("Teams: " + battle.getNumOfDefTeams(), screenWidth * 0.78f, lineHeight + 300.0f, textPaint);

        //This will be the total for the attackers
        graphics2D.drawText("Total: " + battle.getAttDiceTotal(),
                screenWidth * 0.1f, lineHeight + 650.0f, textPaint);

        //This will be the total for the defenders
        graphics2D.drawText("Total: " + battle.getDefDiceTotal(),
                screenWidth * 0.81f, lineHeight + 650.0f, textPaint);

    }


    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    public void Roll() {

        if (mRollDiceButton.isPushTriggered()) {
            showAnimation = false;
            showResults = true;
            battle.singleBattle();
        }else{
            showAnimation = true;
            showResults = false;
        }
    }

    public void createCalculateAnimation() {
        float width = mDefaultLayerViewport.getWidth();
        float height = mDefaultLayerViewport.getHeight();

        //The attackers dice
        mDiceRolls1 = new Rolling(width * 0.03f, height * 0.50f, this);
        mDiceRolls2 = new Rolling(width * 0.08f, height * 0.50f, this);
        mDiceRolls3 = new Rolling(width * 0.13f, height * 0.50f, this);

        //the defenders Dice
        mDiceRolls4 = new Rolling(width * 0.9f, height * 0.50f, this);
        mDiceRolls5 = new Rolling(width * 0.95f, height * 0.50f, this);
    }

    public void results() {
        float width = mDefaultLayerViewport.getWidth();
        float height = mDefaultLayerViewport.getHeight();

        //the attackers results
        mDiceResult1 = new DiceResult(width * 0.03f, height * 0.50f, this, 0);
        mDiceResult2 = new DiceResult(width * 0.08f, height * 0.50f, this, 0);
        mDiceResult3 = new DiceResult(width * 0.13f, height * 0.50f, this, 0);
        mDiceResult4 = new DiceResult(width * 0.90f, height * 0.50f, this, 0);
        mDiceResult5 = new DiceResult(width * 0.95f, height * 0.50f, this, 0);

        for (int i = 0; i < battle.getDiceResultsAtt().length; i++) {
            switch (i) {
                case 0:
                    mDiceResult1 = new DiceResult(width * 0.03f, height * 0.50f, this, battle.getDiceResultsAtt()[i]);
                    break;
                case 1:
                    mDiceResult2 = new DiceResult(width * 0.08f, height * 0.50f, this, battle.getDiceResultsAtt()[i]);
                    break;
                case 2:
                    mDiceResult3 = new DiceResult(width * 0.13f, height * 0.50f, this, battle.getDiceResultsAtt()[i]);
                    break;
            }
        }

        //the defenders results
        for (int i = 0; i < battle.getDiceResultsDef().length; i++) {
            switch (i) {
                case 0:
                    mDiceResult4 = new DiceResult(width * 0.9f, height * 0.50f, this, battle.getDiceResultsDef()[i]);
                    break;
                case 1:
                    mDiceResult5 = new DiceResult(width * 0.95f, height * 0.50f, this, battle.getDiceResultsDef()[i]);
                    break;
            }
        }
    }

    //Sets the number of dice to be rolled based on the
    //push button that is pressed.
    //some more validation may be required.
    public void numberOfDiceToRoll() {

        if (oneDice.isPushTriggered()) {
            numOfDice = 1;
            battle.setNumOfDiceAtt(1);
            battle.resetDice();
        }
        if (twoDice.isPushTriggered()) {
            numOfDice = 2;
            battle.setNumOfDiceAtt(2);
            battle.resetDice();
        }
        if (threeDice.isPushTriggered()) {
            numOfDice = 3;
            battle.setNumOfDiceAtt(3);
            battle.resetDice();
        }
    }

    //just drawing all the buttons.
    public void DrawDiceButtons() {
        //The Roll Button
        mRollDiceButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.65f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);

        mFastBattle = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.49f,
                mDefaultLayerViewport.getHeight() * 0.4f,
                mDefaultLayerViewport.getWidth() * 0.2f,
                mDefaultLayerViewport.getHeight() * 0.267f,
                "FastBattle", "FastBattleSelected", this);


//////////////////////////////////////////////////////////////////////////////////////////////////
        //The images with a dice
        oneDiceSelected = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.037f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice1Selected", "ChooseDiceNumber1", this);

        oneDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.037f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber1", "Dice1Selected", this);

//////////////////////////////////////////////////////////////////////////////////////////////////

        //The image with two Dice
        twoDiceSelected = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice2Selected", "ChooseDiceNumber2", this);

        twoDiceSelectedNull = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice2SelectedNull", "ChooseDiceNumber2Null", this);

        twoDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber2", "Dice2Selected", this);

        twoDiceNull = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber2Null", "Dice2SelectedNull", this);


//////////////////////////////////////////////////////////////////////////////////////////////////

        //The image with 3 dice
        threeDiceSelected = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice3Selected", "ChooseDiceNumber3", this);

        threeDiceSelectedNull = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice3SelectedNull", "ChooseDiceNumber3Null", this);

        threeDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber3", "Dice3Selected", this);

        threeDiceNull = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber3Null", "Dice3SelectedNull", this);


//////////////////////////////////////////////////////////////////////////////////////////////////
        mAbortHack = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.9f,
                mDefaultLayerViewport.getHeight() * 0.11f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "AbortHack", "AbortHackSelected", this);
        mAbortHack.setPlaySounds(true, true);

    }
}
