package uk.ac.qub.eeecs.gage;

import android.graphics.Bitmap;
import android.graphics.Rect;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.content.Context;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import static org.junit.Assert.assertTrue;



/*
Author: Daniel Nelis Entire Class
 */


@RunWith(AndroidJUnit4.class)
public class RiskRulesScreenTests {




    private Context context;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void loadAndAddBitmap_Rules_Screen_Background(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "Rules_Rules_Black_Background",
                "img/RiskGameImages/Rules_Rules_Black_Background.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Speech_Bubble(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "speechBubble", "img/RiskGameImages/newSpeechBubble.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Main_Menu_Button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Main_Menu_Button_Pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "main_menu_button_pressed",
                "img/RiskGameImages/main_menu_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Rules_Button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "How_To_Play_Rule_Button",
                "img/RiskGameImages/How_To_Play_Rule_Button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Rules_Next_Button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_next_button",
                "img/RiskGameImages/risk_rules_next_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Rules_Next_Button_Pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_next_button_pressed",
                "img/RiskGameImages/risk_rules_next_button_pressed.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Rules_Prev_Button(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_prev_button",
                "img/RiskGameImages/risk_rules_prev_button.png");
        assertTrue(success);

    }

    @Test
    public void loadAndAddBitmap_Rules_Prev_Button_Pressed(){

        AssetManager assetManager = new AssetManager(context);
        boolean success = assetManager.loadAndAddBitmap(
                "risk_rules_prev_button_pressed",
                "img/RiskGameImages/risk_rules_prev_button_pressed.png");
        assertTrue(success);

    }
}
