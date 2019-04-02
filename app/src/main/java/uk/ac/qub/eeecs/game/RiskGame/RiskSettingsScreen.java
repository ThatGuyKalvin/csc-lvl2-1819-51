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

    private PushButton MainMenuButton, volumeOffButton, volumeOnButton, volumeUpButton, volumeDownButton;
    private Bitmap RiskSettingsBackground;
    private float mTimeToChange = 0;


    public RiskSettingsScreen(Game game) {
        super("RiskSettingsScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_mute_button", "img/RiskGameImages/risk_mute_button.png");
        assetManager.loadAndAddBitmap("risk_mute_button_pressed", "img/RiskGameImages/risk_mute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_unmute_button", "img/RiskGameImages/risk_unmute_button.png");
        assetManager.loadAndAddBitmap("risk_unmute_button_pressed", "img/RiskGameImages/risk_unmute_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button", "img/RiskGameImages/risk_volume_up_button.png");
        assetManager.loadAndAddBitmap("risk_volume_up_button_pressed", "img/RiskGameImages/risk_volume_up_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button", "img/RiskGameImages/risk_volume_down_button.png");
        assetManager.loadAndAddBitmap("risk_volume_down_button_pressed", "img/RiskGameImages/risk_volume_down_button_pressed.png");

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

        volumeOffButton = new PushButton(
                spacingX * 2.5f, spacingY * 8.5f, spacingX, spacingY,
                "risk_mute_button", "risk_mute_button_pressed", this);
        volumeOffButton.setPlaySounds(true, true);

        volumeOnButton = new PushButton(
                spacingX * 2.5f, spacingY * 10.5f, spacingX, spacingY,
                "risk_unmute_button", "risk_unmute_button_pressed", this);
        volumeOnButton.setPlaySounds(true, true);

        volumeUpButton = new PushButton(
                spacingX * 2.5f, spacingY * 6.5f, spacingX, spacingY,
                "risk_volume_up_button", "risk_volume_up_button_pressed", this);
        volumeUpButton.setPlaySounds(true, true);

        volumeDownButton = new PushButton(
                spacingX * 2.5f, spacingY * 4.5f, spacingX, spacingY,
                "risk_volume_down_button", "risk_volume_down_button_pressed", this);
        volumeDownButton.setPlaySounds(true, true);


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
            volumeOffButton.update(elapsedTime);
            volumeOnButton.update(elapsedTime);
            volumeUpButton.update(elapsedTime);
            volumeDownButton.update(elapsedTime);

            if (volumeOffButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(0);
            else if (volumeOnButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume());
            else if (volumeUpButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() + 0.1f);
            else if (volumeDownButton.isPushTriggered())
                mGame.getAssetManager().getMusic("RiskBackgroundSound").setVolume(mGame.getAudioManager().getMusicVolume() - 0.5f);
           else if (MainMenuButton.isPushTriggered())
               mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }
        mTimeToChange += elapsedTime.stepTime;
    }


    public PushButton getVolumeOffButton() { return volumeOffButton; }
    public PushButton setVolumeOffButton(PushButton button) { return this.volumeOffButton = button; }
    public PushButton getVolumeOnButton() { return volumeOnButton; }
    public PushButton setVolumeOnButton(PushButton button) { return this.volumeOnButton = button; }
    public PushButton getVolumeUpButton() { return volumeUpButton; }
    public PushButton setVolumeUpButton(PushButton button) { return this.volumeUpButton = button; }
    public PushButton getVolumeDownButton() { return volumeDownButton; }
    public PushButton setVolumeDownButton(PushButton button) { return this.volumeDownButton = button; }



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
        volumeOffButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        volumeOnButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        volumeUpButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        volumeDownButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
