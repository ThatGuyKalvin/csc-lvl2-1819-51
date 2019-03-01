package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Rect;

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
import uk.ac.qub.eeecs.game.RiskGame.Battle;
import uk.ac.qub.eeecs.game.RiskGame.DiceRollScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;
import uk.ac.qub.eeecs.game.RiskGame.Player;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */

    private PushButton mSpaceshipDemoButton;
    private PushButton mRulesButton;
    private PushButton mStartGameButton;
    private PushButton mDemosButton;
    private PushButton mSettingsButton;
    private PushButton mCreditsButton;
    private PushButton mPerformanceScreenButton;
    private Bitmap mMainMenuBackground;
    private float mTimeToChange = 0;

    public Battle battle;
    public Player google, apple;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SpaceDemoIcon", "img/SpaceDemoIcon.png");
        assetManager.loadAndAddBitmap("SpaceDemoIconSelected", "img/SpaceDemoIconSelected.png");
        assetManager.loadAndAddBitmap("risk_start_game_button", "img/RiskGameImages/risk_start_game_button.png");
        assetManager.loadAndAddBitmap("risk_start_game_button_pressed", "img/RiskGameImages/risk_start_game_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_rules_button", "img/RiskGameImages/risk_rules_button.png");
        assetManager.loadAndAddBitmap("risk_rules_button_ pressed", "img/RiskGameImages/risk_rules_button_ pressed.png");
        assetManager.loadAndAddBitmap("risk_settings_button", "img/RiskGameImages/risk_settings_button.png");
        assetManager.loadAndAddBitmap("risk_settings_button_pressed", "img/RiskGameImages/risk_settings_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_credits_button", "img/RiskGameImages/risk_credits_button.png");
        assetManager.loadAndAddBitmap("risk_credits_button_pressed", "img/RiskGameImages/risk_credits_button_pressed.png");
        assetManager.loadAndAddBitmap("PerformanceScreenIcon", "img/PerformanceScreenIcon.png");
        assetManager.loadAndAddBitmap("PerformanceScreenIconSelected", "img/PerformanceScreenIconSelected.png");
        assetManager.loadAndAddBitmap("DemosIcon", "img/DemosIcon.png");
        assetManager.loadAndAddBitmap("DemosIconSelected", "img/DemosIconSelected.png");

        assetManager.loadAssets(
                "txt/assets/RiskGameAssets.JSON");

        //Author: Daniel Nelis
        assetManager.loadAndAddBitmap("RiskMainMenuScreen", "img/RiskGameImages/RiskMainMenuScreen.png");

        mMainMenuBackground = assetManager.getBitmap("RiskMainMenuScreen");


        /*
        Author: Daniel Nelis (Format for Main Menu)
         */

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons
        mSpaceshipDemoButton = new PushButton(
                spacingX * 3.5f, spacingY * 5.2f, spacingX, spacingY,
                "SpaceDemoIcon", "SpaceDemoIconSelected",this);
        mSpaceshipDemoButton.setPlaySounds(true, true);
        mRulesButton = new PushButton(
                spacingX * 2.50f, spacingY * 8.5f, spacingX, spacingY,
                "risk_rules_button", "risk_rules_button_ pressed", this);
        mRulesButton.setPlaySounds(true, true);
        mStartGameButton = new PushButton(
                spacingX * 2.50f, spacingY * 10.5f, spacingX, spacingY,
                "risk_start_game_button", "risk_start_game_button_pressed", this);
        mStartGameButton.setPlaySounds(true, true);
        mDemosButton = new PushButton(
                spacingX * 3.5f, spacingY * 4.0f, spacingX/2, spacingY/2,
                "DemosIcon", "DemosIconSelected", this);
        mDemosButton.setPlaySounds(true, true);
        mSettingsButton = new PushButton(
                spacingX * 2.50f, spacingY * 6.5f, spacingX, spacingY,
                "risk_settings_button", "risk_settings_button_pressed", this);
        mSettingsButton.setPlaySounds(true, true);
        mCreditsButton = new PushButton(
                spacingX * 2.50f, spacingY * 4.5f, spacingX, spacingY,
                "risk_credits_button", "risk_credits_button_pressed", this);
        mCreditsButton.setPlaySounds(true, true);
        mPerformanceScreenButton = new PushButton(
                spacingX * 2.50f, spacingY * 2.0f, spacingX/2, spacingY/2,
                "PerformanceScreenIcon", "PerformanceScreenIconSelected", this);
        mPerformanceScreenButton.setPlaySounds(true, true);





        ////////////////////////////////////
        test();
    }


    /**
     * Update the menu screen
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
            mSpaceshipDemoButton.update(elapsedTime);
            mStartGameButton.update(elapsedTime);
            mDemosButton.update(elapsedTime);
            mRulesButton.update(elapsedTime);
            mSettingsButton.update(elapsedTime);
            mCreditsButton.update(elapsedTime);
            mPerformanceScreenButton.update(elapsedTime);


            if (mSpaceshipDemoButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new DiceRollScreen(mGame, battle, google, apple));
            else if (mStartGameButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new RiskGameScreen(mGame));
            else if (mRulesButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new RiskRulesScreen(mGame));
            else if (mDemosButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new DemoMenuScreen(mGame));
            else if (mSettingsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
            else if (mCreditsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new GameCreditsScreen(mGame));
            else if (mPerformanceScreenButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new PerformanceScreen("PerformanceScreen", mGame));
        }

        mTimeToChange += elapsedTime.stepTime;

        /*
        Author: Daniel Nelis
         */
        playBackgroundMusic();
        getGame().getAssetManager().getMusic("RiskBackgroundSound").setLopping(true);
    }



    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////


    /*
    Author: Daniel Nelis
    */
    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    getGame().getAssetManager().getMusic("RiskBackgroundSound"));
    }
    public void stopMusic(){
        AudioManager audioManager = getGame().getAudioManager();
        audioManager.stopMusic();
    }

    /**
     * Draw the menu screen
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


        Rect sourceRectBackg = new Rect(0,0, mMainMenuBackground.getWidth(), mMainMenuBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mMainMenuBackground, sourceRectBackg, destRectBackg, null);


        mSpaceshipDemoButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mRulesButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mStartGameButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mDemosButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mSettingsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mCreditsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mPerformanceScreenButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }

    public void test(){
        int attDice = 3;
        int defDice = 2;
        int TeamsAttacking = 5;
        int teamsDefending = 4;

        battle = new Battle(attDice,defDice, TeamsAttacking, teamsDefending);
        google = new Player("google", -01000016);
        apple = new Player("apple", -01000016);
    }
}
