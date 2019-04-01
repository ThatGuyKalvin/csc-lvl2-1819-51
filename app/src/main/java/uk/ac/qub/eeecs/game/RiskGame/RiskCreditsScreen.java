package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;
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
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class RiskCreditsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for the Credits Screen
     */
    private PushButton mainMenuButton, riskSettingsButton, riskRulesButton;
    private Bitmap creditsBackground, creditsNames;
    private float timeToChange = 0;

    private int spacingX = 0;
    private int spacingY = 0;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param game Game to which this screen belongs
     */

    public RiskCreditsScreen(Game game) {
        super("CreditsScreen", game);

        // Load in the bitmaps used on the credit screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_settings_button", "img/RiskGameImages/risk_settings_button.png");
        assetManager.loadAndAddBitmap("risk_settings_button_pressed", "img/RiskGameImages/risk_settings_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_rules_button", "img/RiskGameImages/risk_rules_button.png");
        assetManager.loadAndAddBitmap("risk_rules_button_pressed", "img/RiskGameImages/risk_rules_button_pressed.png");

        assetManager.loadAssets(
                "txt/assets/OptionsScreenAssets.JSON");

        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskMainMenuScreen.png");
        creditsBackground = assetManager.getBitmap("OptionScreenBackground");
        assetManager.loadAndAddBitmap("risk_credits_screen_names", "img/RiskGameImages/risk_credits_screen_names.png");
        creditsNames = assetManager.getBitmap("risk_credits_screen_names");


        // Define the spacing that will be used to position the buttons
        int spacingX = (int) mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int) mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons

        mainMenuButton = new PushButton(
                spacingX * 2.50f, spacingY * 14.0f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        mainMenuButton.setPlaySounds(true, true);
        riskRulesButton = new PushButton(
                spacingX * 0.60f, spacingY * 7.7f, spacingX, spacingY,
                "risk_rules_button", "risk_rules_button_pressed", this);
        riskRulesButton.setPlaySounds(true, true);
        riskSettingsButton = new PushButton(
                spacingX * 2.50f, spacingY * 1.0f, spacingX, spacingY,
                "risk_settings_button", "risk_settings_button_pressed", this);
        riskSettingsButton.setPlaySounds(true, true);


    }
    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the credits screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && timeToChange > 0.5f) {

            // Update each button and transition if needed

            mainMenuButton.update(elapsedTime);
            riskSettingsButton.update(elapsedTime);
            riskRulesButton.update(elapsedTime);

            if (mainMenuButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new MenuScreen(mGame));
            else if (riskSettingsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new RiskSettingsScreen(mGame));
            else if (riskSettingsButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new RiskRulesScreen("Instructions",mGame));

        }

        timeToChange += elapsedTime.stepTime;
    }

    /**
     * Draw the credits screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
//        graphics2D.clear(Color.WHITE);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();



        Rect sourceRectBackg = new Rect(0, 0, creditsBackground.getWidth(), creditsBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(creditsBackground, sourceRectBackg, destRectBackg, null);

        Rect sourceRectBackg2 = new Rect(0, 0, creditsNames.getWidth(), creditsNames.getHeight());
        Rect destRectBackg2 = new Rect((int) (width * 0.295f), (int) (height * 0.20f), (int) (width * 0.7f), (int) (height * 0.8f));
        graphics2D.drawBitmap(creditsNames, sourceRectBackg2, destRectBackg2, null);


        mainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        riskSettingsButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        riskRulesButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }
}

