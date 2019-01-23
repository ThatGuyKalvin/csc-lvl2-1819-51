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
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class DiceRollScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    private Bitmap background;

    //////Dice Roll//////////
    private int numOfDice =0;
    private int numOfDiceTemp =0;
    private boolean attShowRoll = true;
    private boolean attackersRoll=true;
    //buttons
    private PushButton mNumOfDice1;
    private PushButton mNumOfDice2;
    private PushButton mNumOfDice3;
    private PushButton mSelectDiceNum;
    private PushButton mSelectDiceNum2;
    private PushButton mSelectDiceNum3;
    //end of dice items

    private Paint textPaint = new Paint();
    //the num_of_dice will be set by the player
    private DiceRoll diceRoll = new DiceRoll(numOfDice);
    private DiceRoll diceRollDef = new DiceRoll(numOfDice);


    private PushButton mReturnToMenuButton;
    private PushButton mRollDice;
    private ScreenViewport mGameScreenViewport;
    private float mTimeToChange = 0;

    public DiceRollScreen(Game game) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        assetManager.loadAndAddBitmap("SpaceDemoIcon", "img/SpaceDemoIcon.png");

        //dice assets
        assetManager.loadAndAddBitmap("Roll1", "img/dice/Roll1.png");
        assetManager.loadAndAddBitmap("Roll2", "img/dice/Roll2.png");
        assetManager.loadAndAddBitmap("Dice1Selected", "img/dice/1Selected.png");
        assetManager.loadAndAddBitmap("Dice2Selected", "img/dice/2Selected.png");
        assetManager.loadAndAddBitmap("Dice3Selected", "img/dice/3Selected.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber1", "img/dice/ChooseDiceNumber1.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber2", "img/dice/ChooseDiceNumber2.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber3", "img/dice/ChooseDiceNumber3.png");
        assetManager.loadAndAddBitmap("background","img/dice/DiceRollScreenBackGround.png" );

        //The backGround
        background = assetManager.getBitmap("background");


        mReturnToMenuButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.80f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "BackArrow", "BackArrowSelected", this);
        mReturnToMenuButton.setPlaySounds(true, true);

        //draws the dice hud.
        DrawDiceButtons();

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        mGameScreenViewport =
                new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);


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
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            mRollDice.update(elapsedTime);
            mNumOfDice1.update(elapsedTime);
            mNumOfDice2.update(elapsedTime);
            mNumOfDice3.update(elapsedTime);
            mSelectDiceNum.update(elapsedTime);
            mSelectDiceNum2.update(elapsedTime);
            mSelectDiceNum3.update(elapsedTime);

            mReturnToMenuButton.update(elapsedTime);
            if (mReturnToMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

            //dice functions
            buttonActions();
            //sets the number of dice that the user will roll
            numberOfDiceToRoll();


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
        graphics2D.clear(Color.WHITE);


        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        //drawing the background to the screen.
        Rect sourceRect = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRect = new Rect((int) (screenWidth * 0.0f), (int) (screenHeight * 0.0f), (int) (screenWidth * 1.0f), (int) (screenHeight * 1.0f));
        graphics2D.drawBitmap(background, sourceRect, destRect, null);

        mReturnToMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


        //draw the roll button, currently the attShowRoll isn't used
        //There will be a defShowRoll also for the first roll of each turn
        if(attShowRoll){mRollDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);}

        mNumOfDice1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mNumOfDice2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mNumOfDice3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        //Depending on what the user has selected,
        //the number of dice that has been selected is drawn over the other push-button.
        switch (numOfDiceTemp){
            case 2: mSelectDiceNum2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 3: mSelectDiceNum3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            default: mSelectDiceNum.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
        }




        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 30.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setTextAlign(Paint.Align.LEFT);

        String diceString = "Dice Results: [ ";
        for (int i = 0; i < numOfDice; i++) diceString += diceRoll.getDiceResults()[i] + " ";
        graphics2D.drawText(diceString + "]",
                0.0f, lineHeight + 120.0f, textPaint);

        graphics2D.drawText("Total: [ " + diceRoll.getTotal() + " ]",
                0.0f, lineHeight + 160.0f, textPaint);



    }
    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    //The roll the dice function
    public void buttonActions(){

        if (mRollDice.isPushTriggered()) {
            if(attackersRoll){
                numOfDice = numOfDiceTemp;
                diceRoll = new DiceRoll(numOfDice);
            }
        }
    }


    //Sets the number of dice to be rolled based on the
    //push button that is pressed.
    public void numberOfDiceToRoll(){

        if (mNumOfDice1.isPushTriggered()) {
            if(attackersRoll){numOfDiceTemp = 1;}
        }
        if (mNumOfDice2.isPushTriggered()) {
            if(attackersRoll){numOfDiceTemp = 2;}
        }
        if (mNumOfDice3.isPushTriggered()) {
            if(attackersRoll){numOfDiceTemp = 3;}
        }
    }

    //just drawing all the buttons.
    public void DrawDiceButtons(){

        //The Roll Button
        mRollDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);


        //These are the dice with no yellow outline and can be selected.
        //The image with a dice
        mSelectDiceNum = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.62f,
                mDefaultLayerViewport.getHeight() * 0.08f,
                mDefaultLayerViewport.getWidth() * 0.12f,
                mDefaultLayerViewport.getHeight() * 0.17f,
                "Dice1Selected", "ChooseDiceNumber1", this);
        mSelectDiceNum.setPlaySounds(true, true);

        //The image with two Dice
        mSelectDiceNum2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.75f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Dice2Selected", "ChooseDiceNumber2", this);

        //The image with 3 dice
        mSelectDiceNum3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.9f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Dice3Selected", "ChooseDiceNumber3", this);


        //The buttons that have the yellow out line to show which one has been selected.
        mNumOfDice1 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.62f,
                mDefaultLayerViewport.getHeight() * 0.08f,
                mDefaultLayerViewport.getWidth() * 0.12f,
                mDefaultLayerViewport.getHeight() * 0.17f,
                "ChooseDiceNumber1", "Dice1Selected", this);
        mNumOfDice1.setPlaySounds(true, true);

        mNumOfDice2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.75f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "ChooseDiceNumber2", "Dice2Selected", this);
        mNumOfDice2.setPlaySounds(true, true);


        mNumOfDice3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.9f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "ChooseDiceNumber3", "Dice3Selected", this);
        mNumOfDice3.setPlaySounds(true, true);
    }
}