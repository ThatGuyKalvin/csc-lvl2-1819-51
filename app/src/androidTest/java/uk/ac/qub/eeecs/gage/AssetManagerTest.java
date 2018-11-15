package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.game.DemoGame;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AssetManagerTest {

    private Context context;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "OptionScreenBackground", "img/OptionScreenBackground.png");
        assertTrue(success);

    }


    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful2(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "OptionsScreenButton", "img/OptionsScreenButton.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful3(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "VolumeButton", "img/VolumeButton.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful4(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "BackButton", "img/BackArrow.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful5(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "BackButtonSelected", "img/BackArrowSelected.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful6(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "TexturedRectangle", "img/TexturedRectangle.png");
        assertTrue(success);

    }
    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful7(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "PerformanceScreenIcon", "img/PerformanceScreenIcon.png");
        assertTrue(success);

    }
    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful8(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "PerformanceScreenIconSelected", "img/PerformanceScreenIconSelected.png");
        assertTrue(success);

    }


//    @Test
//    public void loadAndAddBitmap_InvalidData_TestError(){
//
//        AssetManager assetManager = new AssetManager(context);
//        boolean success = assetManager.loadAndAddBitmap(
//                "OptionScreenBackground", "img/OptionScreenBackground2018.png");
//        assertFalse(success);
//    }
}
