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

public class GameCreditsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for the Credits Screen
     */
    private PushButton mMainMenuButton;
    private Bitmap mCreditsBackground;
    private float mTimeToChange = 0;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param game Game to which this screen belongs
     */

    public GameCreditsScreen(Game game) {
        super("CreditsScreen", game);

        // Load in the bitmaps used on the credit screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");

        assetManager.loadAssets(
                "txt/assets/OptionsScreenAssets.JSON");

        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskMainMenuScreen.png");

        mCreditsBackground = assetManager.getBitmap("OptionScreenBackground");

        // Define the spacing that will be used to position the buttons
        int spacingX = (int) mDefaultLayerViewport.getWidth() / 4;
        int spacingY = (int) mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons

        mMainMenuButton = new PushButton(
                spacingX * 0.50f, spacingY * 8.5f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        mMainMenuButton.setPlaySounds(true, true);

    }
    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the credits screen
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

        mTimeToChange += elapsedTime.stepTime;
    }

    /**
     * Draw the credits screen
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


        Rect sourceRectBackg = new Rect(0, 0, mCreditsBackground.getWidth(), mCreditsBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mCreditsBackground, sourceRectBackg, destRectBackg, null);


        mMainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }
}

