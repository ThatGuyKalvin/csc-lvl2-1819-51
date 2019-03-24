package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;
import android.graphics.Paint;
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
 * Author: Daniel Nelis Entire Class
 */

public class LoadingAssets extends GameScreen {

    //Creates assetManager to load in assets
    AssetManager assetManager = mGame.getAssetManager();

    public LoadingAssets(String name, Game game)
    {
        super(name, game);
        loadAssets();

    }

    private void loadAssets()
    {
        loadRiskRulesScreenAssets();
        loadRiskMenuScreenAssets();
        loadRiskSettingsScreenAssets();
    }

    private void loadRiskRulesScreenAssets()
    {
        //RiskRulesScreenAssets
        assetManager.loadAndAddBitmap("speechBubble","img/RiskGameImages/newSpeechBubble.png");
        assetManager.loadAndAddBitmap("main_menu_button","img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed","img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("How_To_Play_Rule_Button","img/RiskGameImages/How_To_Play_Rule_Button.png");
        assetManager.loadAndAddMusic("risk_rules_next_button", "img/RiskGameImages/risk_rules_next_button.png");
        assetManager.loadAndAddMusic("risk_rules_next_button_pressed", "img/RiskGameImages/risk_rules_next_button_pressed.png");
        assetManager.loadAndAddMusic("risk_rules_prev_button", "img/RiskGameImages/risk_rules_prev_button.png");
        assetManager.loadAndAddMusic("risk_rules_prev_button_pressed", "img/RiskGameImages/risk_rules_prev_button_pressed.png");
        assetManager.loadAndAddMusic("Blue_Circle_Around_Rules_Button", "img/RiskGameImages/Blue_Circle_Around_Rules_Button.png");

    }

    private void loadRiskMenuScreenAssets()
    {
        //MenuScreen assets
    }


    private void loadRiskSettingsScreenAssets()
    {
        //
    }


    public void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {


    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {


    }
}
