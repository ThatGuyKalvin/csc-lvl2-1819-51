package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import uk.ac.qub.eeecs.gage.engine.AssetManager;

import static org.junit.Assert.assertTrue;

public class MenuScreenTest {


    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    /*
    Author: Daniel Nelis
     */

    @Test
    public void loadAndAddMusic_BackgroundMusic(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddMusic(
                "RiskBackgroundSound", "sound/RiskBackgroundSound.mp3");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful1() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "RiskMainMenuBackground", "img/RiskGameImages/RiskMainMenuBackground.png");
        assertTrue(success);

    }

    //Author: Daniel Nelis

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful2() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_start_game_button", "img/RiskGameImages/risk_start_game_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful3() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_start_game_button_pressed", "img/RiskGameImages/risk_start_game_button_pressed.png");
        assertTrue(success);

    }


    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful4() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_button", "img/RiskGameImages/risk_rules_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful5() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_button_pressed", "img/RiskGameImages/risk_rules_button_ pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful6() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_settings_button", "img/RiskGameImages/risk_settings_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful7() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_settings_button_pressed", "img/RiskGameImages/risk_settings_button_pressed.png");
        assertTrue(success);

    }
    //Aimee Millar Tests for icon bitmaps
    @Test
    public void loadAndAddBitmap_StartIcon() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "startIcon", "img/RiskGameImages/risk_settings_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_RulesIcon() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "rulesIcon", "img/RiskGameImages/risk_settings_button_pressed.png");
        assertTrue(success);

    }
    @Test
    public void loadAndAddBitmap_SettingsIcon() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "settingsIcon", "img/RiskGameImages/risk_settings_icon.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_CreditsIcon() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "creditsIcon", "img/RiskGameImages/risk_credit_icon.png");
        assertTrue(success);

    }
}