package uk.ac.qub.eeecs.game;

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
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class OptionsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */
    private PushButton mOptionsScreenButton;
    private PushButton mReturnToMenuButton;
    private PushButton mReturnToPerformanceScreenButton;
    private Bitmap Optionsbackground;
    private float mTimeToChange = 0;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public OptionsScreen(Game game) {
        super("OptionsScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("VolumeButton", "img/VolumeButton.png");
        assetManager.loadAndAddBitmap("VolumeButtonSelected", "img/VolumeButtonSelected.png");
        assetManager.loadAndAddBitmap("BackArrowMM", "img/BackArrowMainMenu.png");
        assetManager.loadAndAddBitmap("BackArrowMM", "img/BackArrowMainMenu.png");
        assetManager.loadAndAddBitmap("BackArrowPS", "img/BackArrowPerformanceScreen.png");
        assetManager.loadAndAddBitmap("BackArrowSelectedPS", "img/BackArrowPerformanceScreen.png");
        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/OptionScreenBackground.png");


        Optionsbackground = assetManager.getBitmap("OptionScreenBackground");

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;

        // Create the trigger buttons
        mOptionsScreenButton = new PushButton(
                spacingX * 4.50f, spacingY * 1.5f, spacingX, spacingY,
                "OptionsScreenButton", "OptionsScreenButtonSelected", this);
        mOptionsScreenButton.setPlaySounds(true, true);


        mReturnToMenuButton = new PushButton(
                spacingX * 0.75f, spacingY * 0.5f, spacingX, spacingY,
                "BackArrowMM", "BackArrowSelectedMM", this);
        mReturnToMenuButton.setPlaySounds(true, true);

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

            mOptionsScreenButton.update(elapsedTime);
            mReturnToMenuButton.update(elapsedTime);
            mReturnToPerformanceScreenButton.update(elapsedTime);

            if (mOptionsScreenButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new OptionsScreen(mGame));
            else if (mReturnToMenuButton.isPushTriggered())
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



        mOptionsScreenButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mReturnToMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mReturnToPerformanceScreenButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        Rect sourceRectBackg = new Rect(0,0, Optionsbackground.getWidth(), Optionsbackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(Optionsbackground, sourceRectBackg, destRectBackg, null);

    }
}
