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
    private PushButton mAbortHack;
    private PushButton mRollDiceButton;
    private PushButton mRollDice;
    //end of dice items

    private Paint textPaint = new Paint();
    //the num_of_dice will be set by the player
    private DiceRoll diceRoll = new DiceRoll(numOfDice);

    //extra detail
    private String attackersName = "";
    private int attackersColour = -01000016;

    private String defendersName = "";
    private int defendersColour = -01000016;


    private ScreenViewport mGameScreenViewport;
    private float mTimeToChange = 0;

    public DiceRollScreen(Game game) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("AbortHack", "img/dice/AbortHack.png");
        assetManager.loadAndAddBitmap("AbortHackSelected", "img/dice/AbortHackSelected.png");
        assetManager.loadAndAddBitmap("Roll1", "img/dice/Roll1.png");
        assetManager.loadAndAddBitmap("Roll2", "img/dice/Roll2.png");

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

        //The background
        background = assetManager.getBitmap("background");

        mAbortHack = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.9f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "AbortHack", "AbortHackSelected", this);
        mAbortHack.setPlaySounds(true, true);
        mRollDiceButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.65f,
                mDefaultLayerViewport.getHeight() * 0.1f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);

        //draws the dice hud.
        DrawDiceButtons();

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        mGameScreenViewport =
                new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);

        /////////////// Battle Class Test ///////////////////////////////
        //philip testing

        //Player attacker = new Player("Google", -01000016 );
       // Player defender = new Player("Apple", -01006616 );
        //Area area1 = new Area();
        //Area area2 = new Area();

        //Battle battle = new Battle(attacker, defender, area1, area2);
        //attackersName = battle.attackersName();

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

            mAbortHack.update(elapsedTime);
            if (mAbortHack.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);
            if (mRollDiceButton.isPushTriggered())
                


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
        graphics2D.clear(Color.BLACK);


        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        //drawing the background to the screen.
        Rect sourceRect = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRect = new Rect((int) (screenWidth * 0.0f), (int) (screenHeight * 0.0f), (int) (screenWidth * 1.0f), (int) (screenHeight * 1.0f));
        graphics2D.drawBitmap(background, sourceRect, destRect, null);

        mAbortHack.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRollDiceButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


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

        //Details to be shown on the screen
        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 20.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(-01000016);


        String diceString = "Results: [ ";
        for (int i = 0; i < numOfDice; i++) diceString += diceRoll.getDiceResults()[i] + ", ";
        graphics2D.drawText(diceString + "]",
                screenWidth * 0.45f, lineHeight + 250.0f, textPaint);

        graphics2D.drawText("Total: [ " + diceRoll.getTotal() + " ]", screenWidth * 0.45f, lineHeight + 200.0f, textPaint);

        //graphics2D.drawText("Attackers Name", screenWidth * 0.05f, lineHeight + 140.0f, textPaint);
        //graphics2D.drawText("Defend Name", screenWidth * 0.75f, lineHeight + 140.0f, textPaint);

        //graphics2D.drawText("Attack Colour", 0.0f, lineHeight + 300.0f, textPaint);
        //graphics2D.drawText("Defend Colour", 0.0f, lineHeight + 350.0f, textPaint);

        //graphics2D.drawText("Teams remaining: ", 0.0f, lineHeight + 400.0f, textPaint);
        //graphics2D.drawText("Teams remaining: ", 0.0f, lineHeight + 450.0f, textPaint);



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
                mDefaultLayerViewport.getWidth() * 0.5f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);


//////////////////////////////////////////////////////////////////////////////////////////////////
        //The images with a dice
        mSelectDiceNum = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.037f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice1Selected", "ChooseDiceNumber1", this);

        mNumOfDice1 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.037f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber1", "Dice1Selected", this);

//////////////////////////////////////////////////////////////////////////////////////////////////

        //The image with two Dice
        mSelectDiceNum2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice2Selected", "ChooseDiceNumber2", this);

        mNumOfDice2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.205f,
                mDefaultLayerViewport.getHeight() * 0.115f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber2", "Dice2Selected", this);

//////////////////////////////////////////////////////////////////////////////////////////////////

        //The image with 3 dice
        mSelectDiceNum3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "Dice3Selected", "ChooseDiceNumber3", this);

        mNumOfDice3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.385f,
                mDefaultLayerViewport.getHeight() * 0.118f,
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.1066f,
                "ChooseDiceNumber3", "Dice3Selected", this);

    }

}
