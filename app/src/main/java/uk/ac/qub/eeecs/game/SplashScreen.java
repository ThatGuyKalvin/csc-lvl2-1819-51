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
    private Bitmap LoadingSymbol;
    private Bitmap background;


    // /////////////////////////////////////////////////////////////////////////
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
        assetManager.loadAndAddBitmap("Loading", "img/splashScreen/Loading.png");



        LoadingSymbol = assetManager.getBitmap("Loading");
        background = assetManager.getBitmap("SplashScreenBackground");

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;
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
        if (touchEvents.size() > 0) {
            mTimeToChange = 5f;
        }

        mTimeToChange += elapsedTime.stepTime;
        if (mTimeToChange >= 5.0f) {
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }

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
        graphics2D.clear(Color.CYAN);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();


        //drawing the background to the screen.
        Rect sourceRectBackg = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(background, sourceRectBackg, destRectBackg, null);

        //drawing the "loading" image to the screen.
        Rect sourceRect = new Rect(0, 0 , LoadingSymbol.getWidth(), LoadingSymbol.getHeight());
        Rect destRect = new Rect((int) (width * 0.30f), (int) (height * 0.30f), (int) (width * 0.65f), (int) (height * 0.65f));
        graphics2D.drawBitmap(LoadingSymbol, sourceRect, destRect, null);

    }
}
