package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;


    /*
    Author: Daniel Nelis Entire Class
     */

public class RiskCreditsScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /*
    Providing a list to hold the buttons to provide a convenient
    way of updating and drawing each button
     */

    private List<PushButton> mButtons = new ArrayList<>();


    /*
    Providing a list that will store the list of game screens that
    each button will trigger
     */

    private Map<PushButton, String> mButtonTriggers = new HashMap<>();


    private Bitmap creditsBackground, creditsNames;
    private float timeToChange = 0;


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param game Game to which this screen belongs
     */

    public RiskCreditsScreen(Game game) {
        super("CreditsScreen", game);

        /*
        Loading in the set of bitmaps used for the buttons on the demo Screen
         */

        mGame.getAssetManager().loadAssets("txt/assets/RiskGameAssets.JSON");

        /*
        Constructing the Buttons within RiskCreditsScreen
         */
        constructButtons("txt/assets/RiskCreditsScreenButtonLayout.JSON", mButtons);

        //Ensuring the click sounds are played for all the created buttons
        for (PushButton button : mButtons)
            button.setPlaySounds(true, true);


        // Load in the bitmaps used on the credit screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets(
                "txt/assets/OptionsScreenAssets.JSON");

        assetManager.loadAndAddBitmap("OptionScreenBackground", "img/RiskGameImages/RiskMainMenuScreen.png");
        creditsBackground = assetManager.getBitmap("OptionScreenBackground");
        assetManager.loadAndAddBitmap("risk_credits_screen_names", "img/RiskGameImages/risk_credits_screen_names.png");
        creditsNames = assetManager.getBitmap("risk_credits_screen_names");
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    private void constructButtons(String buttonsToConstructJSONFile, List<PushButton> buttons) {

        // Attempting to load in the JSON asset details
        String loadedJSON;
        try {
            loadedJSON = mGame.getFileIO().loadJSON(buttonsToConstructJSONFile);
        } catch (IOException e) {
            throw  new RuntimeException(
                    "CreditsScreen.constructButtons: Cannot load JSON [" + buttonsToConstructJSONFile + "]");
        }


        try {
            JSONObject settings = new JSONObject(loadedJSON);
            JSONArray buttonDetails = settings.getJSONArray("pushButtons");


            //Below will store the game layer width and height
            float layerWidth = mDefaultLayerViewport.getWidth();
            float layerHeight = mDefaultLayerViewport.getHeight();

            //Constructing each button
            for(int idx = 0; idx < buttonDetails.length(); idx++) {
                float x = (float)buttonDetails.getJSONObject(idx).getDouble("x");
                float y = (float)buttonDetails.getJSONObject(idx).getDouble("y");
                float width = (float)buttonDetails.getJSONObject(idx).getDouble("width");
                float height = (float)buttonDetails.getJSONObject(idx).getDouble("height");

                String defaultBitmap = buttonDetails.getJSONObject(idx).getString("defaultBitmap");
                String pushBitmap = buttonDetails.getJSONObject(idx).getString("pushBitmap");
                String triggeredGameScreen = buttonDetails.getJSONObject(idx).getString("triggeredGameScreen");

                PushButton button = new PushButton(x * layerWidth, y * layerHeight,
                        width * layerWidth, height * layerHeight,
                        defaultBitmap, pushBitmap, this);
                buttons.add(button);
                mButtonTriggers.put(button, triggeredGameScreen);
            }
        } catch (JSONException | IllegalArgumentException e) {
            throw  new RuntimeException(
                    "CreditsScreen.constructButtons: JSON parsing error [" + e.getMessage() + "]");
        }
    }


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

            // Update each button
            for (PushButton button : mButtons) {
                button.update(elapsedTime);
                if(button.isPushTriggered()) {
                    addScreen(mButtonTriggers.get(button));
                }
            }
        }

        timeToChange += elapsedTime.stepTime;
    }

    private void addScreen(String gameScreenToAdd) {

        try {
            GameScreen gameScreen =
                    (GameScreen) Class.forName("uk.ac.qub.eeecs.game.RiskGame." + gameScreenToAdd)
                            .getConstructor(Game.class).newInstance(mGame);
            mGame.getScreenManager().addScreen(gameScreen);

        } catch( ClassNotFoundException | NoSuchMethodException
                | InstantiationException | IllegalAccessException | InvocationTargetException e ) {
            throw new RuntimeException(
                    "RiskCreditsScreen.addScreen: Error creating [" + gameScreenToAdd + " " + e.getMessage() + "]");
        }
    }

    /**
     * Draw the credits screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        //Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();



        Rect sourceRectBackg = new Rect(0, 0, creditsBackground.getWidth(), creditsBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(creditsBackground, sourceRectBackg, destRectBackg, null);

        Rect sourceRectBackg2 = new Rect(0, 0, creditsNames.getWidth(), creditsNames.getHeight());
        Rect destRectBackg2 = new Rect((int) (width * 0.295f), (int) (height * 0.20f), (int) (width * 0.7f), (int) (height * 0.82f));
        graphics2D.drawBitmap(creditsNames, sourceRectBackg2, destRectBackg2, null);


        // Draw each button
        for (PushButton button : mButtons)
            button.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


    }
}

