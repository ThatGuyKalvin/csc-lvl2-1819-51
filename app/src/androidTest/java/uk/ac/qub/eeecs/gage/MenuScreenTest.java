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


    @Test
    public void loadAndAddBitmap_ValidData_TestIsSuccessful1() {

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "RiskMainMenuBackground", "img/RiskGameImages/RiskMainMenuBackground.png");
        assertTrue(success);

    }

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

}