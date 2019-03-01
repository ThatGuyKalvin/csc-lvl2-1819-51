package uk.ac.qub.eeecs.game;

import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;
import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.audio.AudioManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.RiskGame.Battle;
import uk.ac.qub.eeecs.game.RiskGame.DiceRollScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;
import uk.ac.qub.eeecs.game.RiskGame.Player;
import android.graphics.Rect;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for playing the 'games'
     */

    private PushButton mSpaceshipDemoButton;
    private PushButton mRulesButton;
    private PushButton mStartGameButton;
    private PushButton mDemosButton;
    private PushButton mSettingsButton;
    private PushButton mCreditsButton;
    private PushButton mPerformanceScreenButton;
    private Bitmap mMainMenuBackground;
    private float mTimeToChange = 0;

    private Rect currentRect;
    private Rect leftRect;
    private Rect rightRect;
    private Rect buttonRect;

    private ArrayList<Bitmap> Icon = new ArrayList<>();
    private ArrayList<Bitmap> Button = new ArrayList<>();

    private int currentCounter;
    private int leftCounter;
    private int rightCounter;

    public Battle battle;
    public Player google, apple;

    AssetManager assetManager = mGame.getAssetManager();


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);

        // Load in the bitmaps used on the main menu screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("SpaceDemoIcon", "img/SpaceDemoIcon.png");
        assetManager.loadAndAddBitmap("SpaceDemoIconSelected", "img/SpaceDemoIconSelected.png");
        assetManager.loadAndAddBitmap("PerformanceScreenIcon", "img/PerformanceScreenIcon.png");
        assetManager.loadAndAddBitmap("PerformanceScreenIconSelected", "img/PerformanceScreenIconSelected.png");
        assetManager.loadAndAddBitmap("DemosIcon", "img/DemosIcon.png");
        assetManager.loadAndAddBitmap("DemosIconSelected", "img/DemosIconSelected.png");
        assetManager.loadAndAddBitmap("startIcon", "img/RiskGameImages/risk_start_icon.png");
        assetManager.loadAndAddBitmap("rulesIcon", "img/RiskGameImages/risk_rules_icon.png");
        assetManager.loadAndAddBitmap("settingsIcon", "img/RiskGameImages/risk_settings_icon.png");
        assetManager.loadAndAddBitmap("creditsIcon", "img/RiskGameImages/risk_credit_icon.png");
        assetManager.loadAndAddBitmap("risk_start_game_button", "img/RiskGameImages/risk_start_game_button.png");
        assetManager.loadAndAddBitmap("risk_start_game_button_pressed", "img/RiskGameImages/risk_start_game_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_rules_button", "img/RiskGameImages/risk_rules_button.png");
        assetManager.loadAndAddBitmap("risk_rules_button_ pressed", "img/RiskGameImages/risk_rules_button_ pressed.png");
        assetManager.loadAndAddBitmap("risk_settings_button", "img/RiskGameImages/risk_settings_button.png");
        assetManager.loadAndAddBitmap("risk_settings_button_pressed", "img/RiskGameImages/risk_settings_button_pressed.png");
        assetManager.loadAndAddBitmap("risk_credits_button", "img/RiskGameImages/risk_credits_button.png");
        assetManager.loadAndAddBitmap("risk_credits_button_pressed", "img/RiskGameImages/risk_credits_button_pressed.png");
        assetManager.loadAssets(
                "txt/assets/RiskGameAssets.JSON");

        //Author: Daniel Nelis
        assetManager.loadAndAddBitmap("RiskMainMenuScreen", "img/RiskGameImages/RiskMainMenuScreen.png");

        mMainMenuBackground = assetManager.getBitmap("RiskMainMenuScreen");

        /*
        Author: Daniel Nelis (Format for Main Menu)
         */

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 4;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons
        mSpaceshipDemoButton = new PushButton(
                spacingX * 3.5f, spacingY * 5.2f, spacingX, spacingY,
                "SpaceDemoIcon", "SpaceDemoIconSelected",this);
        mSpaceshipDemoButton.setPlaySounds(true, true);
        /*mRulesButton = new PushButton(
                spacingX * 2.0f, spacingY * 8.5f, spacingX, spacingY,
                "risk_rules_button", "risk_rules_button_ pressed", this);
        mRulesButton.setPlaySounds(true, true);
        mStartGameButton = new PushButton(
                spacingX * 2.0f, spacingY * 10.5f, spacingX, spacingY,
                "risk_start_game_button", "risk_start_game_button_pressed", this);
        mStartGameButton.setPlaySounds(true, true);*/
        mDemosButton = new PushButton(
                spacingX * 3.5f, spacingY * 4.0f, spacingX/2, spacingY/2,
                "DemosIcon", "DemosIconSelected", this);
        mDemosButton.setPlaySounds(true, true);
//        mSettingsButton = new PushButton(
//                spacingX * 2.0f, spacingY * 6.5f, spacingX, spacingY,
//                "risk_settings_button", "risk_settings_button_pressed", this);
//        mSettingsButton.setPlaySounds(true, true);
//        mCreditsButton = new PushButton(
//                spacingX * 2.0f, spacingY * 4.5f, spacingX, spacingY,
//                "risk_credits_button", "risk_credits_button_pressed", this);
//        mCreditsButton.setPlaySounds(true, true);
        mPerformanceScreenButton = new PushButton(
                spacingX * 3.5f, spacingY * 2.0f, spacingX/2, spacingY/2,
                "PerformanceScreenIcon", "PerformanceScreenIconSelected", this);
        mPerformanceScreenButton.setPlaySounds(true, true);





        ////////////////////////////////////
        test();

        //@Aimee Millar

        //Setting the bitmap needed for the icons and buttons
        Bitmap startIcon = assetManager.getBitmap("startIcon");
        Bitmap rulesIcon = assetManager.getBitmap("rulesIcon");
        Bitmap settingsIcon = assetManager.getBitmap("settingsIcon");
        Bitmap creditsIcon = assetManager.getBitmap("creditsIcon");

        Bitmap startButton = assetManager.getBitmap("risk_start_game_button");
        Bitmap rulesButton = assetManager.getBitmap("risk_rules_button");
        Bitmap settingsButton = assetManager.getBitmap("risk_settings_button");
        Bitmap creditsButton = assetManager.getBitmap("risk_credits_button");

        //Adding the bitmaps to the appropriate array
        Icon.add(startIcon);
        Icon.add(rulesIcon);
        Icon.add(settingsIcon);
        Icon.add(creditsIcon);

        Button.add(startButton);
        Button.add(rulesButton);
        Button.add(settingsButton);
        Button.add(creditsButton);

        currentCounter = 0;
        rightCounter = currentCounter+1;
        leftCounter = Icon.size()-1;
    }


    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            // Update each button and transition if needed
            mSpaceshipDemoButton.update(elapsedTime);
            mDemosButton.update(elapsedTime);
            mPerformanceScreenButton.update(elapsedTime);


            if (mSpaceshipDemoButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new DiceRollScreen(mGame, battle, google, apple));
            else if (mDemosButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new DemoMenuScreen(mGame));
            else if (mPerformanceScreenButton.isPushTriggered())
                mGame.getScreenManager().addScreen(new PerformanceScreen("PerformanceScreen", mGame));
        }

        mTimeToChange += elapsedTime.stepTime;
        if (touchEvents.size() > 0) {
            TouchEvent touchEvent = touchEvents.get(0);

            //@Aimee Millar
            //rightRect is the rect which holds the right icon. This moves the counters forward one and forwards through the array
            if (rightRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                currentCounter++;
                rightCounter++;
                leftCounter++;

                if (currentCounter > Icon.size() - 1) {
                    currentCounter = 0;
                }
                if (leftCounter > Icon.size() - 1) {
                    leftCounter = 0;
                }
                if (rightCounter > Icon.size() - 1) {
                    rightCounter = 0;
                }
            }
            //leftRect is the rect which holds the left icon. This moves the counters back one and backwards through the array
            if (leftRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                currentCounter--;
                rightCounter--;
                leftCounter--;

                if (currentCounter < 0) {
                    currentCounter = Icon.size() - 1;
                }
                if (leftCounter < 0) {
                    leftCounter = Icon.size() - 1;
                }
                if (rightCounter < 0) {
                    rightCounter = Icon.size() - 1;
                }

            }
            //button rect is the rect below the current icon on the screen and is used for moving to other screens with the game
            if (buttonRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                if (currentCounter == 0) {
                    mGame.getScreenManager().addScreen(new RiskGameScreen(mGame));
                } else if (currentCounter == 1) {
                    mGame.getScreenManager().addScreen(new RiskRulesScreen(mGame));
                } else if (currentCounter == 2) {
                    mGame.getScreenManager().addScreen(new SettingsScreen(mGame));
                } else if (currentCounter == 3) {
                    mGame.getScreenManager().addScreen(new GameCreditsScreen(mGame));
                }
            }
        /*
        Author: Daniel Nelis
         */
            playBackgroundMusic();
            getGame().getAssetManager().getMusic("RiskBackgroundSound").setLopping(true);
        }

    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////


    /*
    Author: Daniel Nelis
    */
    private void playBackgroundMusic() {
        AudioManager audioManager = getGame().getAudioManager();
        if(!audioManager.isMusicPlaying())
            audioManager.playMusic(
                    getGame().getAssetManager().getMusic("RiskBackgroundSound"));
    }
    public void stopMusic(){
        AudioManager audioManager = getGame().getAudioManager();
        audioManager.stopMusic();
    }

    //@Aimee Millar
    //Method to set out the position and draw the icons and buttons
    public Rect setAndDrawRect(int x, int y, Rect rect, Bitmap rectBitmap, IGraphics2D graphics2D){
        if(rect == null){
            rect = new Rect(x,y,rectBitmap.getWidth()+x, rectBitmap.getHeight()+y);
        }
        graphics2D.drawBitmap(rectBitmap,null,rect,null);
        return rect;
    }
    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);
        int width = graphics2D.getSurfaceWidth();
        int height = graphics2D.getSurfaceHeight();


        Rect sourceRectBackg = new Rect(0,0, mMainMenuBackground.getWidth(), mMainMenuBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mMainMenuBackground, sourceRectBackg, destRectBackg, null);


        mSpaceshipDemoButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mDemosButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mPerformanceScreenButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


        leftRect = setAndDrawRect(660, 450, leftRect, Icon.get(leftCounter), graphics2D);
        rightRect = setAndDrawRect(1360, 450, rightRect, Icon.get(rightCounter), graphics2D);
        currentRect = setAndDrawRect(1020, 500, currentRect, Icon.get(currentCounter), graphics2D);
        buttonRect = setAndDrawRect(1110, 1000, buttonRect, Button.get(currentCounter), graphics2D);

    }

    public void test(){
        int attDice = 3;
        int defDice = 2;
        int TeamsAttacking = 5;
        int teamsDefending = 4;

        battle = new Battle(attDice,defDice, TeamsAttacking, teamsDefending);
        google = new Player("google", -01000016);
        apple = new Player("apple", -01000016);
    }
}

