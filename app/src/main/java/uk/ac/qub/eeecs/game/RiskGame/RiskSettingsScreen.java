package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;
import android.graphics.Color;
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

/**
 * Author: Daniel Nelis Entire Class
 */
public class RiskSettingsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    private PushButton MainMenuButton;
    private PushButton MuteButton;
    private PushButton UnmuteButton;
    private PushButton VolumeUpButton;
    private PushButton VolumeDownButton;
    private Bitmap RiskSettingsBackground;
    private float mTimeToChange = 0;


    public RiskSettingsScreen(Game game) {
        super("RiskSettingsScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_mute_button", "img/risk_mute_button.png");
        assetManager.loadAndAddBitmap("risk_mute_button_pressed", "img/risk_mute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_unmute_button", "img/risk_unmute_button.png");
        assetManager.loadAndAddBitmap("risk_unmute_button_pressed", "img/risk_unmute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button", "img/risk_volume_up_button.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button_pressed", "img/risk_volume_up_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button", "img/risk_volume_down_button.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button_pressed", "img/risk_volume_down_button_pressed.png");

        //Loading Bitmaps
        assetManager.loadAssets("txt/assets/OptionsScreenAssets.JSON");
        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskOptionScreen.png");
        RiskSettingsBackground = assetManager.getBitmap("OptionScreenBackground");

        // Background Music
        assetManager.loadAssets("txt/assets/RiskGameAssets.JSON");


        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons
        MainMenuButton = new PushButton(
                spacingX * 0.50f, spacingY * 8.5f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        MainMenuButton.setPlaySounds(true, true);

        MuteButton = new PushButton(
                spacingX * 2.5f, spacingY * 8.5f, spacingX, spacingY,
                "risk_mute_button", "risk_mute_button_pressed", this);
        MuteButton.setPlaySounds(true, true);

        UnmuteButton = new PushButton(
                spacingX * 2.5f, spacingY * 10.5f, spacingX, spacingY,
                "risk_unmute_button", "risk_unmute_button_pressed", this);
        UnmuteButton.setPlaySounds(true, true);

        VolumeUpButton = new PushButton(
                spacingX * 2.5f, spacingY * 6.5f, spacingX, spacingY,
                "risk_volume_up_button", "risk_volume_up_button_pressed", this);
        VolumeUpButton.setPlaySounds(true, true);

        VolumeDownButton = new PushButton(
                spacingX * 2.5f, spacingY * 4.5f, spacingX, spacingY,
                "risk_volume_down_button", "risk_volume_down_button_pressed", this);
        VolumeDownButton.setPlaySounds(true, true);


    }



    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            // Update each button and transition if needed
            MainMenuButton.update(elapsedTime);
            MuteButton.update(elapsedTime);
            UnmuteButton.update(elapsedTime);
            VolumeUpButton.update(elapsedTime);
            VolumeDownButton.update(elapsedTime);

            if (MuteButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(0);
            else if (UnmuteButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume());
            else if (VolumeUpButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() + 0.1f);
            else if (VolumeDownButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() - 0.5f);
           else if (MainMenuButton.isPushTriggered())
               mGame.getScreenManager().removeScreen(this);
        }

        mTimeToChange += elapsedTime.stepTime;


    }


   
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();


        Rect sourceRectBackg = new Rect(0,0, RiskSettingsBackground.getWidth(), RiskSettingsBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(RiskSettingsBackground, sourceRectBackg, destRectBackg, null);

        MainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        MuteButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        UnmuteButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        VolumeUpButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        VolumeDownButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }
}
