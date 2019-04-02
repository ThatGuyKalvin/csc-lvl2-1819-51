package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.io.FileIO;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.game.DemoGame;
import uk.ac.qub.eeecs.game.OptionsScreen;
import uk.ac.qub.eeecs.game.RiskGame.BlackHatHackers;
import uk.ac.qub.eeecs.game.RiskGame.RiskCreditsScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskSettingsScreen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

    /*
    Author: Daniel Nelis Entire Class
     */

@RunWith(AndroidJUnit4.class)
public class RiskCreditsScreenTests {

    BlackHatHackers game = new BlackHatHackers();
    Context appContext = InstrumentationRegistry.getTargetContext();
    AssetManager assetManager = new AssetManager(appContext);
    RiskCreditsScreen gameScreen;
    ElapsedTime elapsedTime = new ElapsedTime();


    @Before
    public void setUp(){
        game.mAssetManager = assetManager;
    }


    //Test for Constructor
    @Test
    public void testSettingsScreenConstructor() {
        gameScreen = new RiskCreditsScreen(game);
        assertEquals("RiskCreditsScreen", gameScreen.getName());
    }

    @Test
    public void loadAndAddBitmap_main_menu_button(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_main_menu_button_pressed(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_settings_button(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_settings_button", "img/RiskGameImages/risk_settings_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_settings_button_pressed(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_settings_button_pressed", "img/RiskGameImages/risk_settings_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_credit_names(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_credits_screen_names", "img/RiskGameImages/risk_credits_screen_names.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_risk_credit_screen_background(){

        AssetManager assetManager = new AssetManager(appContext);
        boolean success = assetManager.loadAndAddBitmap(
                "RiskMainMenuScreen", "img/RiskGameImages/RiskMainMenuScreen.png");
        assertTrue(success);

    }


}
