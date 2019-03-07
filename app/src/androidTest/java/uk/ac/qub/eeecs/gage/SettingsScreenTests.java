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

    /*
    Author: Daniel Nelis Entire Class
     */

@RunWith(AndroidJUnit4.class)
public class SettingsScreenTests {

    private Context context;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void loadAndAddBitmap_main_menu_button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_main_menu_button_pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_mute_button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_mute_button", "img/RiskGameImages/risk_mute_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_mute_button_pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_mute_button_pressed", "img/RiskGameImages/risk_mute_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_unmute_button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_unmute_button", "img/RiskGameImages/risk_unmute_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_unmute_button_pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_unmute_button_pressed", "img/RiskGameImages/risk_unmute_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_volume_up_button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_volume_up_button", "img/RiskGameImages/risk_volume_up_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_volume_up_button_pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_volume_up_button_pressed", "img/RiskGameImages/risk_volume_up_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_volume_down_button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_volume_down_button", "img/RiskGameImages/risk_volume_down_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_volume_down_button_pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_volume_down_button_pressed", "img/RiskGameImages/risk_volume_down_button_pressed.png");
        assertTrue(success);

    }



}
