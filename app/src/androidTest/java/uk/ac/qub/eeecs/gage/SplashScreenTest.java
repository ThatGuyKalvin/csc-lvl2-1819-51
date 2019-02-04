package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SplashScreenTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void loadAndAddBitmap_background_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "background", "img/splashScreen/Background2.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_loadingAnimation_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Loading", "img/splashScreen/LoadingAnimation.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_LoadingBar_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "LoadingBar", "img/splashScreen/splashScreenLoadingBar2.png");
        assertTrue(success);
    }

    @Test
    public void loadAndAddBitmap_blinking_Success() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "blinking", "img/splashScreen/BlinkAnimation.png");
        assertTrue(success);
    }

}
