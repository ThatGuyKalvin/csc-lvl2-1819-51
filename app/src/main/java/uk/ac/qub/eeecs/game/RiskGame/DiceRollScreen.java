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
import uk.ac.qub.eeecs.game.RiskGame.DiceRoll;
import uk.ac.qub.eeecs.game.RiskGame.Battle;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.RiskGame.Player;
import uk.ac.qub.eeecs.game.RiskGame.Area;

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

    private boolean showResults = false;
    private boolean showAnimation = true;

    //buttons
    private PushButton mAbortHack;
    private PushButton mRollDiceButton;

    private PushButton oneDice;
    private PushButton twoDice;
    private PushButton threeDice;

    private PushButton oneDiceSelected;
    private PushButton twoDiceSelected;
    private PushButton threeDiceSelected;

    //Animation
    private Rolling mDiceRolls1;
    private Rolling mDiceRolls2;
    private Rolling mDiceRolls3;
    private Rolling mDiceRolls4;
    private Rolling mDiceRolls5;

    //Result
    private DiceResult mDiceResult1;
    private DiceResult mDiceResult2;
    private DiceResult mDiceResult3;
    private DiceResult mDiceResult4;
    private DiceResult mDiceResult5;


    private ScreenViewport mGameScreenViewport;
    private Paint textPaint = new Paint();
    private float mTimeToChange = 0;

    public DiceRollScreen(Game game) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("AbortHack", "img/dice/AbortHack.png");
        assetManager.loadAndAddBitmap("AbortHackSelected", "img/dice/AbortHackSelected.png");
        assetManager.loadAndAddBitmap("Roll1", "img/dice/Roll1.png");
        assetManager.loadAndAddBitmap("Roll2", "img/dice/Roll2.png");
        assetManager.loadAndAddBitmap("Dice1Selected", "img/dice/1Selected.png");
        assetManager.loadAndAddBitmap("Dice2Selected", "img/dice/2Selected.png");
        assetManager.loadAndAddBitmap("Dice3Selected", "img/dice/3Selected.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber1", "img/dice/ChooseDiceNumber1.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber2", "img/dice/ChooseDiceNumber2.png");
        assetManager.loadAndAddBitmap("ChooseDiceNumber3", "img/dice/ChooseDiceNumber3.png");
        assetManager.loadAndAddBitmap("background","img/dice/DiceRollScreenBackGround.png" );
        assetManager.loadAndAddBitmap("Number1", "img/dice/Results/Number1.png");
        assetManager.loadAndAddBitmap("Number2", "img/dice/Results/Number2.png");
        assetManager.loadAndAddBitmap("Number3", "img/dice/Results/Number3.png");
        assetManager.loadAndAddBitmap("Number4", "img/dice/Results/Number4.png");
        assetManager.loadAndAddBitmap("Number5", "img/dice/Results/Number5.png");
        assetManager.loadAndAddBitmap("Number6", "img/dice/Results/Number6.png");
        assetManager.loadAndAddBitmap("Number0", "img/dice/Results/Number0.png");

        background = assetManager.getBitmap("background");


        //draws the dice hud.
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
        if(showAnimation){
            if(mRollDiceButton.isPushed()){
                mDiceRolls1.update(elapsedTime);
                mDiceRolls2.update(elapsedTime);
                mDiceRolls3.update(elapsedTime);
                mDiceRolls4.update(elapsedTime);
                mDiceRolls5.update(elapsedTime);
            }
        }


        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            mRollDiceButton.update(elapsedTime);
            oneDice.update(elapsedTime);
            twoDice.update(elapsedTime);
            threeDice.update(elapsedTime);
            oneDiceSelected.update(elapsedTime);
            twoDiceSelected.update(elapsedTime);
            threeDiceSelected.update(elapsedTime);

            mAbortHack.update(elapsedTime);
            if (mAbortHack.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

                

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

        //drawing the background to the screen.
        Rect sourceRect = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRect = new Rect((int) (screenWidth * 0.0f), (int) (screenHeight * 0.0f), (int) (screenWidth * 1.0f), (int) (screenHeight * 1.0f));
        graphics2D.drawBitmap(background, sourceRect, destRect, null);

        mAbortHack.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRollDiceButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        oneDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        twoDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        threeDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        if(showAnimation) {
            mDiceRolls1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls4.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceRolls5.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        if(showResults){
            mDiceResult1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult4.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mDiceResult5.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        //Depending on what the user has selected,
        //the number of dice that has been selected is drawn over the other push-button.
        switch (numOfDice){
            case 1: oneDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 2: twoDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
            case 3: threeDiceSelected.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
                break;
        }

        //Details to be shown on the screen
        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 20.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(-01000016);

    }

    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    public void Roll(){
        //add in some more validation

        if(mRollDiceButton.isPushTriggered()){
            showAnimation = false;
            showResults = true;
        }
    }

    public void createCalculateAnimation(){
        float width = mDefaultLayerViewport.getWidth();
        float height = mDefaultLayerViewport.getHeight();

        //The attackers dice
        mDiceRolls1 = new Rolling(width * 0.03f,height * 0.50f, this );
        mDiceRolls2 = new Rolling(width * 0.08f,height * 0.50f, this );
        mDiceRolls3 = new Rolling(width * 0.13f,height * 0.50f, this );

        //the defenders Dice
        mDiceRolls4 = new Rolling(width * 0.9f,height * 0.50f, this );
        mDiceRolls5 = new Rolling(width * 0.95f,height * 0.50f, this );
    }

    public void results(){
        float width = mDefaultLayerViewport.getWidth();
        float height = mDefaultLayerViewport.getHeight();
        mDiceResult1 = new DiceResult(width * 0.03f, height * 0.50f, this, 4);
        mDiceResult2 = new DiceResult(width * 0.08f, height * 0.50f, this, 2);
        mDiceResult3 = new DiceResult(width * 0.13f, height * 0.50f, this, 3);


        mDiceResult4 = new DiceResult(width * 0.9f, height * 0.50f, this, 1);
        mDiceResult5 = new DiceResult(width * 0.95f, height * 0.50f, this, 5);
    }



    //Sets the number of dice to be rolled based on the
    //push button that is pressed.
    public void numberOfDiceToRoll(){

        if (oneDice.isPushTriggered()) {
            numOfDice = 1;
        }
        if (twoDice.isPushTriggered()) {
            numOfDice = 2;
        }
        if (threeDice.isPushTriggered()) {
            numOfDice = 3;
        }
    }

    //just drawing all the buttons.
    public void DrawDiceButtons(){
        //The Roll Button
        mRollDiceButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.65f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);


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

        twoDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber2", "Dice2Selected", this);

//////////////////////////////////////////////////////////////////////////////////////////////////

        //The image with 3 dice
        threeDiceSelected = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice3Selected", "ChooseDiceNumber3", this);

        threeDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber3", "Dice3Selected", this);

        mAbortHack = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.9f,
                mDefaultLayerViewport.getHeight() * 0.11f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "AbortHack", "AbortHackSelected", this);
        mAbortHack.setPlaySounds(true, true);

    }


}
