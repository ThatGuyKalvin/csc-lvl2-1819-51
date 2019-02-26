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

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */

/**
 * Author: Daniel Nelis
 */
public class  SettingsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */
    private PushButton mVolumeScreenButton;
    private PushButton mMainMenuButton;
    private PushButton mMuteButton;
    private PushButton mUnmuteButton;
    private PushButton mVolumeUpButton;
    private PushButton mVolumeDownButton;
    private PushButton mReturnToPerformanceScreenButton;
    private Bitmap mOptionsBackground;
    private float mTimeToChange = 0;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public SettingsScreen(Game game) {
        super("SettingsScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("VolumeButton", "img/VolumeButton.png");
        assetManager.loadAndAddBitmap("VolumeButtonSelected", "img/VolumeButtonSelected.png");
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("BackArrowPS", "img/BackArrowPerformanceScreen.png");
        assetManager.loadAndAddBitmap("BackArrowSelectedPS", "img/BackArrowPerformanceScreen.png");
        assetManager.loadAndAddBitmap("risk_mute_button", "img/risk_mute_button.png");
        assetManager.loadAndAddBitmap("risk_mute_button_pressed", "img/risk_mute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_unmute_button", "img/risk_unmute_button.png");
        assetManager.loadAndAddBitmap("risk_unmute_button_pressed", "img/risk_unmute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button", "img/risk_volume_up_button.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button_pressed", "img/risk_volume_up_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button", "img/risk_volume_down_button.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button_pressed", "img/risk_volume_down_button_pressed.png");
        assetManager.loadAndAddBitmap("BackArrowPS", "img/BackArrowPerformanceScreen.png");
        assetManager.loadAndAddBitmap("BackArrowSelectedPS", "img/BackArrowPerformanceScreen.png");

        //Load Background
        assetManager.loadAssets(
                "txt/assets/OptionsScreenAssets.JSON");
        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskOptionScreen.png");
        mOptionsBackground = assetManager.getBitmap("OptionScreenBackground");

        // Background Music
        assetManager.loadAssets("txt/assets/RiskGameAssets.JSON");


        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons
        mVolumeScreenButton = new PushButton(
                spacingX * 4.50f, spacingY * 1.5f, spacingX, spacingY,
                "VolumeButton", "VolumeButtonSelected", this);
        mVolumeScreenButton.setPlaySounds(true, true);

        mMainMenuButton = new PushButton(
                spacingX * 0.50f, spacingY * 8.5f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        mMainMenuButton.setPlaySounds(true, true);

        mMuteButton = new PushButton(
                spacingX * 2.5f, spacingY * 8.5f, spacingX, spacingY,
                "risk_mute_button", "risk_mute_button_pressed", this);
        mMuteButton.setPlaySounds(true, true);

        mUnmuteButton = new PushButton(
                spacingX * 2.5f, spacingY * 10.5f, spacingX, spacingY,
                "risk_unmute_button", "risk_unmute_button_pressed", this);
        mUnmuteButton.setPlaySounds(true, true);

        mVolumeUpButton = new PushButton(
                spacingX * 2.5f, spacingY * 6.5f, spacingX, spacingY,
                "risk_volume_up_button", "risk_volume_up_button_pressed", this);
        mVolumeUpButton.setPlaySounds(true, true);

        mVolumeDownButton = new PushButton(
                spacingX * 2.5f, spacingY * 4.5f, spacingX, spacingY,
                "risk_volume_down_button", "risk_volume_down_button_pressed", this);
        mVolumeDownButton.setPlaySounds(true, true);

        mReturnToPerformanceScreenButton = new PushButton(
                spacingX * 0.75f, spacingY * 1.5f, spacingX, spacingY,
                "BackArrowPS", "BackArrowSelectedPS", this);
        mReturnToPerformanceScreenButton.setPlaySounds(true, true);


    }



    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

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
            mVolumeScreenButton.update(elapsedTime);
            mMainMenuButton.update(elapsedTime);
            mMuteButton.update(elapsedTime);
            mUnmuteButton.update(elapsedTime);
            mVolumeUpButton.update(elapsedTime);
            mVolumeDownButton.update(elapsedTime);
            mReturnToPerformanceScreenButton.update(elapsedTime);

            if (mMuteButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(0);
            else if (mUnmuteButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume());
            else if (mVolumeUpButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() + 0.1f);
            else if (mVolumeDownButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() - 0.5f);
           else if (mMainMenuButton.isPushTriggered())
               mGame.getScreenManager().removeScreen(this);
           else if (mReturnToPerformanceScreenButton.isPushTriggered())
               mGame.getScreenManager().addScreen(new PerformanceScreen("PerformanceScreen", mGame));
        }

        mTimeToChange += elapsedTime.stepTime;


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


        Rect sourceRectBackg = new Rect(0,0, mOptionsBackground.getWidth(), mOptionsBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mOptionsBackground, sourceRectBackg, destRectBackg, null);

        mMainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mMuteButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mUnmuteButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mVolumeUpButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mVolumeDownButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }
}
