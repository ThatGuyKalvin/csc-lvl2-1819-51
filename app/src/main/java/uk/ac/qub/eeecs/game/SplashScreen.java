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
import uk.ac.qub.eeecs.gage.ui.Bar;
import uk.ac.qub.eeecs.gage.world.GameScreen;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class SplashScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    private float mTimeToChange = 0;
    private Bitmap background;
    private Bar SplashBar;


    // //////////////////////////////
    // /////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public SplashScreen(Game game) {
        super("SplashScreen", game);


        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();

        assetManager.loadAssets(
                "txt/assets/SplashScreenAssets.JSON");

        assetManager.loadAndAddBitmap("SplashScreenBackground", "img/splashScreen/splashScreenBackground.png");

        background = assetManager.getBitmap("SplashScreenBackground");

        splashBar();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public void splashBar()
    {
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("splashScreenLoadingBar","img/splashScreen/splashScreenLoadingBar.png");

        SplashBar = new Bar(360.0f, 163.0f,300.0f, 40.0f,
                getGame().getAssetManager().getBitmap("splashScreenLoadingBar"),
                Bar.Orientation.Horizontal, 100, 0, 35.0f, this);

        SplashBar.forceValue(0);
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
        if (touchEvents.size() > 0) {
            mTimeToChange = 5f;
        }

        mTimeToChange += elapsedTime.stepTime;
        if (mTimeToChange >= 5.0f) {
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }

        SplashBar.setValue(Math.round(
                SplashBar.getMaxValue() * (mTimeToChange / 5)));

        // Update the bar's displayed value
        SplashBar.update(elapsedTime);

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

        SplashBar.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        //drawing the background to the screen.
        Rect sourceRectBackg = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(background, sourceRectBackg, destRectBackg, null);

    }
}
