package uk.ac.qub.eeecs.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.preference.PreferenceManager;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class RiskRulesScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties

    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for the Risk Rules Screen
     */

    private Bitmap mRiskRulesBackground;
    private PushButton mMainMenuButton;
    public PushButton mHowToPlayRiskButton;
    private PushButton mRiskRulesPrevButton;
    private PushButton mRiskRulesNextButton;
    public Rect speechBubbleBackground = new Rect();
    public static boolean alreadyLoaded = false;

    private float mTimeToChange = 0;

    //managing music
    public Context myContext = mGame.getActivity();
    public SharedPreferences getPreferences = PreferenceManager.getDefaultSharedPreferences(myContext);


    public int riskImageCounter = 0;

    public boolean rulesOfGamePushed = false;
    public boolean rulesBurronPressed = false;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param game Game to which this screen belongs
     */

    public RiskRulesScreen(Game game) {
        super("RiskRulesScreen", game);


        // Load in the bitmaps used on the risk rules screen
        if(alreadyLoaded == false) {
            AssetManager assetManager = mGame.getAssetManager();
            assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
            assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
            assetManager.loadAndAddBitmap("risk_rules_next_button", "img/RiskGameImages/risk_rules_next_button.png");
            assetManager.loadAndAddBitmap("risk_rules_next_button_pressed", "img/RiskGameImages/risk_rules_next_button_pressed.png");
            assetManager.loadAndAddBitmap("risk_rules_prev_button", "img/RiskGameImages/risk_rules_prev_button.png");
            assetManager.loadAndAddBitmap("risk_rules_prev_button_pressed", "img/RiskGameImages/risk_rules_prev_button_pressed.png");


            //Laoding Bitmaps
            assetManager.loadAssets(
                    "txt/assets/OptionsScreenAssets.JSON");
            assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskMainMenuScreen.png");
            mRiskRulesBackground = assetManager.getBitmap("OptionScreenBackground");

        }

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 7;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons

        mMainMenuButton = new PushButton(
                spacingX * 0.50f, spacingY * 8.5f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        mMainMenuButton.setPlaySounds(true, true);

        mRiskRulesPrevButton = new PushButton(
                spacingX * 3.0f, spacingY * 4.7f, spacingX, spacingY,
                "risk_rules_prev_button", "risk_rules_prev_button_pressed", this);
        mRiskRulesPrevButton.setPlaySounds(true, true);

        mRiskRulesNextButton = new PushButton(
                spacingX * 4.0f, spacingY * 4.7f, spacingX, spacingY,
                "risk_rules_next_button", "risk_rules_next_button_pressed", this);
        mRiskRulesNextButton.setPlaySounds(true, true);

    }

    public void stopMusic(){
        AudioManager audioManager = getGame().getAudioManager();
        audioManager.stopMusic();
    }

    public void changeToScreen(GameScreen screen){
        //Stops the song from playing on the next screen
        if (getPreferences.getBoolean("Music", true)){
            stopMusic();
        }
        alreadyLoaded = true;
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);

    }

    // This method draws the speechBubble bitmap
    public void drawSpeechBubble(IGraphics2D graphics2D){
        graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("RiskRulesSpeechBubble"), null, speechBubbleBackground,null);
    }

    //This method changes the dimensions of the rectangle thatt the speechBubble is drawn to based off which section/button you press
    public void drawSpeechBubbleRect(int divideTop, int divideLeft, int divideBottom, int divideRight)
    {
        speechBubbleBackground.top = mGame.getScreenHeight()*100/divideTop;
        speechBubbleBackground.left = mGame.getScreenHeight()*100/divideLeft;
        speechBubbleBackground.bottom = mGame.getScreenHeight();
        if(divideRight == 0)
        {
            speechBubbleBackground.right = mGame.getScreenWidth();
        }
        else
        {
            speechBubbleBackground.right = mGame.getScreenWidth()*100/divideRight;
        }

    }






    /**
     * Update the risk rules screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

            // Process any touch events occurring since the update
            Input input = mGame.getInput();

            List<TouchEvent> touchEvents = input.getTouchEvents();
            if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            // Update each button and transition if needed

            mMainMenuButton.update(elapsedTime);

            if (mMainMenuButton.isPushTriggered())
            mGame.getScreenManager().removeScreen(this);
            }

        mRiskRulesPrevButton.update(elapsedTime);


        mRiskRulesNextButton.update(elapsedTime);


        mTimeToChange += elapsedTime.stepTime;
    }
    /**
     * Draw the risk rules screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();


        Rect sourceRectBackg = new Rect(0,0, mRiskRulesBackground.getWidth(), mRiskRulesBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mRiskRulesBackground, sourceRectBackg, destRectBackg, null);


        mMainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRiskRulesPrevButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRiskRulesNextButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }
}