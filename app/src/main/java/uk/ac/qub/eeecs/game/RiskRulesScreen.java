package uk.ac.qub.eeecs.game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;
import java.util.Objects;

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

public class RiskRulesScreen extends GameScreen
{
    //Create asset manager
    AssetManager assetManager = mGame.getAssetManager();
    //Background for the RiskRulesScreen
    public Rect RiskRulesScreenBackground = new Rect();

    public Rect BlueRoundRectangle = new Rect();
    public Rect BlueCircle = new Rect();
    public Rect gameImage = new Rect();
    public static boolean alreadyLoaded = false;
    public PushButton mHowToPlayTheRules, mBackToMainMenuButton, nextPageButton, prevPageButton;



    // Define the spacing that will be used to position the buttons
    public int spacingX = 0;
    public int spacingY = 0;

    //Used for cycling through the pages for The Rules
    public int rulePageCounter = 0;


    public boolean rulesOfGamePushed = false;
    public boolean prevNextButtonPressed = false;

    public RiskRulesScreen(String name, Game game)
    {
        super(name,game);


        if(alreadyLoaded == false) {
            assetManager.loadAssets("txt/assets/RiskRulesScreenAssets.JSON");
            assetManager.loadAndAddBitmap("Rules_Rules_Black_Background", "img/RiskGameImages/Rules_Rules_Black_Background.png");
            assetManager.loadAndAddBitmap("speechBubble", "img/RiskGameImages/newSpeechBubble.png");
            assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
            assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
            assetManager.loadAndAddBitmap("How_To_Play_Rule_Button", "img/RiskGameImages/How_To_Play_Rule_Button.png");
            assetManager.loadAndAddBitmap("risk_rules_next_button", "img/RiskGameImages/risk_rules_next_button.png");
            assetManager.loadAndAddBitmap("risk_rules_next_button_pressed", "img/RiskGameImages/risk_rules_next_button_pressed.png");
            assetManager.loadAndAddBitmap("risk_rules_prev_button", "img/RiskGameImages/risk_rules_prev_button.png");
            assetManager.loadAndAddBitmap("risk_rules_prev_button_pressed", "img/RiskGameImages/risk_rules_prev_button_pressed.png");
            assetManager.loadAndAddBitmap("Blue_Circle_Around_Rules_Button", "img/RiskGameImages/Blue_Circle_Around_Rules_Button.png");
            assetManager.loadAndAddBitmap("Rules_Dice_Roll", "img/RiskGameImages/Rules_Dice_Roll.png");
            assetManager.loadAndAddBitmap("Rules_Deploy_Armies", "img/RiskGameImages/Rules_Deploy_Armies.png");
            assetManager.loadAndAddBitmap("Rules_Field_Transitions_Image", "img/RiskGameImages/Rules_Field_Transitions_Image.png");
            assetManager.loadAndAddBitmap("Rules_Attack_3_Dice", "img/RiskGameImages/Rules_Attack_3_Dice.png");
            assetManager.loadAndAddBitmap("Rules_Defend_2_Dice", "img/RiskGameImages/Rules_Defend_2_Dice.png");
            assetManager.loadAndAddBitmap("Rules_Defending_Team_Wins_Dice_Roll", "img/RiskGameImages/Rules_Defending_Team_Wins_Dice_Roll.png");
            assetManager.loadAndAddBitmap("Blue_Leprechaun", "img/RiskGameImages/Blue_Leprechaun.png");
            assetManager.loadAndAddBitmap("Rules_Map_With_Areas", "img/RiskGameImages/Rules_Map_With_Areas.png");



        }

        // Defining the spacing that will be used to position the buttons
        spacingX = game.getScreenWidth() / 5;
        spacingY = game.getScreenHeight() / 3;

        // Create the trigger buttons
        mBackToMainMenuButton = new PushButton (spacingX * 0.20f, spacingY * 0.42f, spacingX/4, spacingY/10, "main_menu_button", "main_menu_button_pressed",this);
        mHowToPlayTheRules = new PushButton(spacingX * 0.15f, spacingY * 0.12f, spacingX/6, spacingY/6, "How_To_Play_Rule_Button", this);


        nextPageButton = new PushButton(spacingX * -100f, spacingY * -100f, spacingX/7, spacingY/13, "risk_rules_next_button", "risk_rules_next_button_pressed",this);
        prevPageButton = new PushButton(spacingX * -100f, spacingY * -100f, spacingX/7, spacingY/13, "risk_rules_prev_button", "risk_rules_prev_button_pressed", this);
    }


    public void changeToScreen(GameScreen screen) {
        alreadyLoaded = true;
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
    }


    //This method draws the speechBubble bitmap and the wizard on a stump bitmap
    public void drawSpeechBubbleBitmap(IGraphics2D graphics2D)
    {
        graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("speechBubble"),null, BlueRoundRectangle, null);
    }

    public void drawBlueCircleBitmap(IGraphics2D graphics2D)
    {
        graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Blue_Circle_Around_Rules_Button"),null, BlueCircle, null);
    }

    //This method changes the dimensions of the rectangle that the speechBubble is drawn to based off which section/button you press
    public void drawBlueRoundRectangle(int divideTop, int divideLeft, int divideBottom, int divideRight)
    {
        BlueRoundRectangle.top = mGame.getScreenHeight()*100/divideTop;
        BlueRoundRectangle.left = mGame.getScreenWidth()*100/divideLeft;
        BlueRoundRectangle.bottom = mGame.getScreenHeight()*100/divideBottom;
        if(divideRight == 0)
        {
            BlueRoundRectangle.right = mGame.getScreenWidth();
        }
        else
        {
            BlueRoundRectangle.right = mGame.getScreenWidth()*100/divideRight;
        }

    }

    public void drawBlueCircle(int divideTop, int divideLeft, int divideBottom, int divideRight)
    {
        BlueCircle.top = mGame.getScreenHeight()*100/divideTop;
        BlueCircle.left = mGame.getScreenWidth()*100/divideLeft;
        BlueCircle.bottom = mGame.getScreenHeight()*100/divideBottom;
        if(divideRight == 0)
        {
            BlueCircle.right = mGame.getScreenWidth();
        }
        else
        {
            BlueCircle.right = mGame.getScreenWidth()*100/divideRight;
        }

    }

    //These Methods are to draw the speech bubble to rectangle dimensions
    void drawTextShapes(IGraphics2D graphics2D, String instructionType)
    {
        if(Objects.equals(instructionType, "Main"))
        {
            //drawBlueRoundRectangle(900,1200,170,110);
            //drawSpeechBubbleBitmap(graphics2D);

            drawBlueCircle(144, 1400, 97, 350);
            drawBlueCircleBitmap(graphics2D);
        }


        if(Objects.equals(instructionType, "Rules"))
        {
            drawBlueRoundRectangle(750,900,220,110);
            drawSpeechBubbleBitmap(graphics2D);
        }
    }

    @Override
    public void update(ElapsedTime elapsedTime)
    {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        // Update each button and transition if needed
        mBackToMainMenuButton.update(elapsedTime);
        mHowToPlayTheRules.update(elapsedTime);
        nextPageButton.update(elapsedTime);
        prevPageButton.update(elapsedTime);

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {


            TouchEvent touchEvent = touchEvents.get(0);

            if (mBackToMainMenuButton.isPushTriggered()) {

                changeToScreen(new MenuScreen(mGame));
            }
            //Increases the number when the button is pressed to change the image being draw to the rectangle
            if(nextPageButton.isPushTriggered()) {
                prevNextButtonPressed = true;
                if (rulePageCounter < 7) {
                    rulePageCounter++;
                } else {
                    rulePageCounter = 7;
                }

            }
            if(prevPageButton.isPushTriggered())
            {
                prevNextButtonPressed = true;
                if(rulePageCounter >0)
                {
                    rulePageCounter--;
                }
                else {
                    rulePageCounter = 0;
                }
            }
            if(mHowToPlayTheRules.isPushTriggered()){

                nextPageButton.setPosition(spacingX * 0.30f, spacingY * 0.30f);
                prevPageButton.setPosition(spacingX * 0.15f, spacingY * 0.30f);
                rulesOfGamePushed = true;

                rulePageCounter = 0;
            }
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D)
    {
        //Making a new paint so i can display text (colour = black)
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(45.0f);
        //Setting up a second paint colour for some of the text (colour = black)
        Paint paintRules = new Paint();
        paintRules.setColor(Color.WHITE);
        paintRules.setTextSize(45.0f);

        //Drawing the main RiskRulesScreenBackground for the class
        RiskRulesScreenBackground.top = 0;
        RiskRulesScreenBackground.left = 0;
        RiskRulesScreenBackground.bottom = mGame.getScreenHeight();
        RiskRulesScreenBackground.right =mGame.getScreenWidth();
        graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Rules_Black_Background"),null, RiskRulesScreenBackground, null);

        mBackToMainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mHowToPlayTheRules.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        //draw arrows for going through images
        nextPageButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        prevPageButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        if(!rulesOfGamePushed)
        {

            gameImage.top = mGame.getScreenHeight()* 100/350;
            gameImage.left = mGame.getScreenWidth()* 100/310;
            gameImage.bottom = mGame.getScreenHeight()* 100/97;
            gameImage.right = mGame.getScreenWidth()* 100/105;


            drawTextShapes(graphics2D,"Main");
            graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Map_With_Areas"),null, gameImage,null);
            graphics2D.drawText("Hey! Not sure how to play Black Hat Hackers ? The overall goal of",spacingX * 0.90f,spacingY * 0.55f,paintRules);
            graphics2D.drawText("the game is to take overall the entire map with one team remaining!",spacingX * 0.90f,spacingY * 0.75f,paintRules);
            graphics2D.drawText("Hit the rules button to learn how to play!",spacingX * 0.90f,spacingY * 0.95f,paintRules);
        }


        if(rulesOfGamePushed)
        {
            drawTextShapes(graphics2D,"Rules");
            gameImage.top = mGame.getScreenHeight()*100/350;
            gameImage.left = mGame.getScreenWidth()*100/310;
            gameImage.bottom = mGame.getScreenHeight()*100/97;
            gameImage.right = mGame.getScreenWidth()*100/105;



            switch(rulePageCounter)
            {
                case 0:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Dice_Roll"),null, gameImage,null);
                    graphics2D.drawText("All players will roll dice & the player with the highest with",spacingX * 1.0f ,spacingY * 0.65f,paint);
                    graphics2D.drawText("the highest roll will go first.",spacingX * 1.0f,spacingY * 0.85f,paint);
                    break;
                case 1:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Deploy_Armies"),null, gameImage,null);
                    graphics2D.drawText("Player will choose where to deploy their armies. Second", spacingX * 1.0f, spacingY * 0.65f, paint);
                    graphics2D.drawText("highest roller will then deploy their armies and so on...", spacingX * 1.0f, spacingY * 0.85f, paint);

                    break;
                case 2:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Attack_3_Dice"),null, gameImage,null);
                    graphics2D.drawText("Player will then choose to attack another army in a", spacingX * 1.0f, spacingY * 0.65f, paint);
                    graphics2D.drawText("connected field. Attacking Player will roll 3 dice. ", spacingX * 1.0f, spacingY * 0.85f, paint);
                    break;
                case 3:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Defend_2_Dice"),null, gameImage,null);
                    graphics2D.drawText("The defending player will then roll 2 dice.", spacingX * 1.0f, spacingY * 0.65f, paint);
                    break;
                case 4:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Field_Transitions_Image"),null, gameImage,null);
                    graphics2D.drawText("If attacking player dice is greater than defending player", spacingX * 1.0f, spacingY * 0.65f, paint);
                    graphics2D.drawText("dice then the attacking player will take over the field.", spacingX * 1.0f, spacingY * 0.85f, paint);
                    graphics2D.drawText("", spacingX * 0.70f, spacingY * 1.7f, paint);
                    graphics2D.drawText("", spacingX * 0.70f, spacingY * 1.9f, paint);
                    break;
                case 5:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Rules_Defending_Team_Wins_Dice_Roll"),null, gameImage,null);
                    graphics2D.drawText("If defending player dice is greater than attacking player", spacingX * 1.0f, spacingY * 0.65f, paint);
                    graphics2D.drawText("dice then attacking player will not take over.", spacingX * 1.0f, spacingY * 0.85f, paint);
                    break;
                case 6:
                    graphics2D.drawBitmap(mGame.getAssetManager().getBitmap("Blue_Leprechaun"),null, gameImage,null);
                    graphics2D.drawText("      Repeat this process until one player has every field!", spacingX * 1.0f, spacingY * 0.65f, paint);
                    break;
                default:
                    graphics2D.drawText("                          END OF RULES                     ",spacingX * 1.0f,spacingY * 0.70f,paintRules);
                    break;
            }
        }
    }
}
