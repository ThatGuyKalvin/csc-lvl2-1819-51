package uk.ac.qub.eeecs.game.RiskGame;


import android.graphics.Color;
import android.graphics.Paint;
import java.util.List;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class RiskGameScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////



    //////Dice Roll//////////
    private int numOfDice =0;
    private int numOfDiceTemp =0;
    private boolean attShowRoll = true;
    private boolean attackersRoll=true;
    private boolean displayDiceOptions= false;
    //buttons
    private PushButton mNumOfDice1;
    private PushButton mNumOfDice2;
    private PushButton mNumOfDice3;
    private PushButton mSelectDiceNum;
    private PushButton mSelectDiceNum2;
    private PushButton mSelectDiceNum3;
    //end of dice items



    private final int MAX_AREAS = 10;
    private final int MAX_PLAYERS = 3;
    private Area[] mAreas = new Area[MAX_AREAS];
    private Player[] mPlayers = new Player[MAX_PLAYERS];
    private Paint textPaint = new Paint();
    //the num_of_dice will be set by the player
    private DiceRoll diceRoll = new DiceRoll(numOfDice);
    private DiceRoll diceRollDef = new DiceRoll(numOfDice);


    private PushButton mReturnToMenuButton;
    private PushButton mRollDice;
    private ScreenViewport mGameScreenViewport;
    private float mTimeToChange = 0;

    public RiskGameScreen(Game game) {
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



        // Generate Area objects
        createAreas();
        // Generate Player objects
        createPlayers();

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

        mReturnToMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);



        //draw the roll button, currently the attShowRoll isn't used
        //There will be a defShowRoll also for the first roll of each turn
        if(attShowRoll){mRollDice.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);}

        //setting the dice images to represent how many dice are being rolled
        switch (numOfDiceTemp){
            case 2: mSelectDiceNum2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            break;
            case 3: mSelectDiceNum3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            break;
            default: mSelectDiceNum.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            break;
        }

        //draws the dice buttons to the screen so the user can select the number of dice they wish to roll;
        if(displayDiceOptions){
            mNumOfDice1.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mNumOfDice2.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
            mNumOfDice3.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        }

        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 30.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setColor(mPlayers[0].getColour());
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw text displaying the name of this screen and some instructions
        String playersString = "Players: [ ";
        for(int i = 0; i < MAX_PLAYERS; i++) playersString += mPlayers[i].getName() + " ";
        graphics2D.drawText(playersString + "]",
                0.0f, lineHeight, textPaint);
        String areasString = "Areas: [ ";
        for(int i = 0; i < MAX_PLAYERS; i++) areasString += mAreas[i].getName() + " ";
        graphics2D.drawText(areasString + "]",
                0.0f, lineHeight + 40.0f, textPaint);
        graphics2D.drawText("Screen: [" + this.getName() + "]",
                0.0f, lineHeight + 80.0f, textPaint);


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

    private void createAreas() {
        for(int i = 0; i < MAX_AREAS; i++) {

            mAreas[i] = new Area();
            mAreas[i].setColour(i); // give each area a colour
            mAreas[i].setName("A" + i);
        }
    }

    private void createPlayers() {
        mPlayers[0] = new Player("Microsoft", Color.BLACK);
        mPlayers[1] = new Player("Google", Color.GREEN);
        mPlayers[2] = new Player("Apple", Color.RED);
    }

    public void buttonActions(){
        if(mSelectDiceNum.isPushTriggered()){
            displayDiceOptions = true;
        }

        if (mRollDice.isPushTriggered()) {
            if(attackersRoll){
                numOfDice = numOfDiceTemp;
                diceRoll = new DiceRoll(numOfDice);
            }
        }
    }

    public void numberOfDiceToRoll(){
        if (mNumOfDice1.isPushTriggered()) {
            displayDiceOptions = false;
            if(attackersRoll){numOfDiceTemp = 1;}
        }
        if (mNumOfDice2.isPushTriggered()) {
            if(attackersRoll){numOfDiceTemp = 2;}
            displayDiceOptions = false;
        }
        if (mNumOfDice3.isPushTriggered()) {
            if(attackersRoll){numOfDiceTemp = 3;}
            displayDiceOptions = false;
        }
    }

    public void DrawDiceButtons(){
        mRollDice = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.08f,
                mDefaultLayerViewport.getHeight() * 0.05f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "Roll1", "Roll2", this);

        mSelectDiceNum2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.30f,
                mDefaultLayerViewport.getHeight() * 0.05f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "ChooseDiceNumber2", "ChooseDiceNumber2", this);

        mSelectDiceNum3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.30f,
                mDefaultLayerViewport.getHeight() * 0.05f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "ChooseDiceNumber3", "ChooseDiceNumber3", this);

        mSelectDiceNum = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.30f,
                mDefaultLayerViewport.getHeight() * 0.05f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "ChooseDiceNumber1", "ChooseDiceNumber1", this);
        mSelectDiceNum.setPlaySounds(true, true);

        mNumOfDice1 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.3f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "ChooseDiceNumber1", "Dice1Selected", this);
        mNumOfDice1.setPlaySounds(true, true);

        mNumOfDice2 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.5f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "ChooseDiceNumber2", "Dice2Selected", this);
        mNumOfDice2.setPlaySounds(true, true);

        mNumOfDice3 = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.7f,
                mDefaultLayerViewport.getHeight() * 0.5f,
                mDefaultLayerViewport.getWidth() * 0.15f,
                mDefaultLayerViewport.getHeight() * 0.2f,
                "ChooseDiceNumber3", "Dice3Selected", this);
        mNumOfDice3.setPlaySounds(true, true);
    }
}
