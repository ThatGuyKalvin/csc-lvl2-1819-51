package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

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
import uk.ac.qub.eeecs.game.PerformanceScreen;
import uk.ac.qub.eeecs.game.miscDemos.DemoMenuScreen;

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

        ////////////////////////////////////
        test();

        //@Aimee Millar
        //Declaring the bitmaps needed for the icons and buttons
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

        mTimeToChange += elapsedTime.stepTime;
        if (touchEvents.size() > 0) {
            TouchEvent touchEvent = touchEvents.get(0);

            //@Aimee Millar
            //rightRect is the rect which holds the right icon. This moves the counters forward one and forwards through the array
            if (rightRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                currentCounter++;
                if (currentCounter > Icon.size() - 1) {
                    currentCounter = 0;
                }

                switch(currentCounter){

                    case 0:
                        rightCounter = 1;
                        leftCounter = 3;
                        break;
                    case 1:
                        rightCounter = 2;
                        leftCounter = 0;
                        break;
                    case 2:
                        rightCounter = 3;
                        leftCounter = 1;
                        break;
                    case 3:
                        rightCounter = 0;
                        leftCounter = 2;
                        break;
                }

            }
            //leftRect is the rect which holds the left icon. This moves the counters back one and backwards through the array
            if (leftRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                currentCounter--;

                if (currentCounter < 0) {
                    currentCounter = Icon.size() - 1;
                }

                switch(currentCounter){

                    case 0:
                        rightCounter = 1;
                        leftCounter = 3;
                        break;
                    case 1:
                        rightCounter = 2;
                        leftCounter = 0;
                        break;
                    case 2:
                        rightCounter = 3;
                        leftCounter = 1;
                        break;
                    case 3:
                        rightCounter = 0;
                        leftCounter = 2;
                        break;
                }

            }
            //Displays the button correlating to the corresponding icon
            if (buttonRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                if (currentCounter == 0) {
                    mGame.getScreenManager().addScreen(new RiskGameScreen(mGame));
                } else if (currentCounter == 1){
                    mGame.getScreenManager().addScreen(new RiskRulesScreen("Instructions", mGame));
                } else if(currentCounter == 2) {
                    mGame.getScreenManager().addScreen(new RiskSettingsScreen(mGame));
                } else if(currentCounter == 3) {
                    mGame.getScreenManager().addScreen(new RiskCreditsScreen(mGame));
                }
            }
        }
        /*
        Author: Daniel Nelis
         */
        playBackgroundMusic();
        getGame().getAssetManager().getMusic("RiskBackgroundSound").setLopping(true);
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
    //Method to draw the rects that hold the icons and buttons
    public Rect drawingRect(float x, float y, Rect rect, Bitmap rectBitmap, IGraphics2D graphics2D){

        if(rect == null) {
            RectF rectF = new RectF(graphics2D.getSurfaceWidth() * x, graphics2D.getSurfaceHeight() * y, rectBitmap.getWidth() + graphics2D.getSurfaceWidth() * x,
                    rectBitmap.getHeight() + graphics2D.getSurfaceHeight() * y);
            rect = new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));

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

        //Author Dan Nelis
        Rect sourceRectBackg = new Rect(0,0, mMainMenuBackground.getWidth(), mMainMenuBackground.getHeight());
        Rect destRectBackg = new Rect((int) (width * 0.0f), (int) (height * 0.0f), (int) (width * 1.0f), (int) (height * 1.0f));
        graphics2D.drawBitmap(mMainMenuBackground, sourceRectBackg, destRectBackg, null);

        //Author Aimee Millar
        leftRect = drawingRect(0.305f, 0.31f, leftRect, Icon.get(leftCounter), graphics2D);
        rightRect = drawingRect(0.49f, 0.31f, rightRect, Icon.get(rightCounter), graphics2D);
        currentRect = drawingRect(0.40f, 0.35f, currentRect, Icon.get(currentCounter), graphics2D);
        buttonRect = drawingRect(0.435f, 0.71f, buttonRect, Button.get(currentCounter), graphics2D);

    }

    public void test(){
        Player google = new Player("Google", -01000016);
        Player apple = new Player("Apple", -01000016);

        Field field1 = new Field(19, "Social Media",0xFF8B1D8F);
        Field field2 = new Field(20, "Research Labs",0xFF8F0081);
        Field field23 = new Field(21, "Research Labs2",0xFF8F0081);
        field1.setPlayer(google);
        field2.setPlayer(apple);
        field1.setNumOfTeams(5);
        field2.setNumOfTeams(5);
        ArrayList<Field> connected = new ArrayList<>();
        connected.add(field2);
        field1.addConnectedFields(connected);
        battle = new Battle(field1, field2);
    }
}
