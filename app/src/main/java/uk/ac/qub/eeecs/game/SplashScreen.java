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
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.BoardDemoGame.BoardDemoScreen;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;
import uk.ac.qub.eeecs.game.platformDemo.PlatformDemoScreen;
import uk.ac.qub.eeecs.game.spaceDemo.SpaceshipDemoScreen;

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
    private Bar SplashBar;
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
                "txt/assets/AssetDemoScreenAssets.JSON");

        assetManager.loadAndAddBitmap("Loading", "img/Loading.png");

        LoadingSymbol = assetManager.getBitmap("Loading");

        splashBar();
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////
        public void splashBar()
        {
            AssetManager assetManager = mGame.getAssetManager();
            assetManager.loadAndAddBitmap("","");

            SplashBar = new Bar(30.0f, 30.0f, 30.0f, 30.0f, 
                    getGame().getAssetManager().getBitmap(""), 
                    Bar.Orientation.Horizontal, 100, 0, 1.0f, this);
            
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
        graphics2D.clear(Color.CYAN);

        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();

        Rect sourceRect = new Rect(0, 0 , LoadingSymbol.getWidth(), LoadingSymbol.getHeight());
        Rect destRect = new Rect((int) (width * 0.30f), (int) (height * 0.30f), (int) (width * 0.65f), (int) (height * 0.65f));

        graphics2D.drawBitmap(LoadingSymbol, sourceRect, destRect, null);

        SplashBar.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
