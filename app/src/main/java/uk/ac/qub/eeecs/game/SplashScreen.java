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
import uk.ac.qub.eeecs.gage.ui.Bar;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.RiskGame.MenuScreen;

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
    //private LoadingAnimation load;


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
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets("txt/assets/SplashScreenAssets.JSON");

        splashBar();
        playBackgroundMusic();
        background = assetManager.getBitmap("SplashScreenBackground");
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    public void splashBar()
    {
        float width = mGame.getScreenWidth();
        float height = mGame.getScreenHeight();

        SplashBar = new Bar(0.0f, 0.0f,width * 1.0f, height * 1.0f,
                getGame().getAssetManager().getBitmap("splashScreenLoadingBar"),
                Bar.Orientation.Horizontal, 100, 0, 20.0f, this);

        SplashBar.forceValue(0);
    }

    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    getGame().getAssetManager().getMusic("SplashBackgroundMusic"));
    }
    public void stopMusic(){
        AudioManager audioManager = getGame().getAudioManager();
        audioManager.stopMusic();
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

        //load.update(elapsedTime);
        if(mTimeToChange == 3.0f){
            playBackgroundMusic();
        }

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            mTimeToChange = 5f;
            stopMusic();
        }

        mTimeToChange += elapsedTime.stepTime;
        if (mTimeToChange >= 5.0f) {
            mGame.getScreenManager().addScreen(new MenuScreen(mGame));
        }

        SplashBar.setValue(Math.round(
                SplashBar.getMaxValue() * (mTimeToChange / 2)));

        // Update the bar's displayed value
        SplashBar.update(elapsedTime);

    }

    /**
     * Draw the menu screen
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        graphics2D.clear(Color.BLACK);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();

        Rect sourceRectBackg = new Rect(0,0, background.getWidth(), background.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));

        SplashBar.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        graphics2D.drawBitmap(background, sourceRectBackg, destRectBackg, null);
        //load.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

    }
}
