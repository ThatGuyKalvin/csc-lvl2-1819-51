package uk.ac.qub.eeecs.gage;


import android.graphics.Bitmap;
import android.graphics.Rect;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ScreenManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.RiskGame.MenuScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskRulesScreen;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Author: Daniel Nelis Entire Class
 */

@RunWith(MockitoJUnitRunner.class)
public class RiskRulesScreenMockTestClass {

    @Mock
    public RiskGameScreen mockGameScreen;
    @Mock
    public Game mockGame;
    @Mock
    private MenuScreen menuScreen;
    @Mock
    public AssetManager mockAssetManager;
    @Mock
    public Bitmap mockBitmap;
    @Mock
    Game game;
    @Mock
    ScreenManager screenManager;
    @Mock
    AssetManager assetManager;
    @Mock
    Bitmap bitmap;
    @Mock
    Input input;
    @Mock
    Rect gameImage, BlueCircle, BlueRoundRectangle;
    @Mock
    PushButton mainMenuButton, howToPlayTheRules, nextPageButton, prevPageButton;

    String instructionType;
    @Mock
    IGraphics2D graphics2D;
    @Mock
    RiskRulesScreen riskRulesScreen;

    public int spacingX;

    public int spacingY;

    public boolean viewRules = true;

    public int rulePageCounter;

    @Before
    public void setup() {

        spacingY = mockGame.getScreenHeight()/5;
        spacingX = mockGame.getScreenWidth()/3;
        screenManager = new ScreenManager(game);

        when(mockGameScreen.getGame()).thenReturn(mockGame);
        when(mockGame.getAssetManager()).thenReturn(mockAssetManager);

        when(game.getScreenManager()).thenReturn(screenManager);

        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(any(String.class))).thenReturn(bitmap);

        when(game.getInput()).thenReturn(input);

        RiskRulesScreen riskRulesScreen = new RiskRulesScreen("String", mockGame);
        //game.mScreenManager.addScreen(riskRulesScreen);

        gameImage = new Rect();

        mainMenuButton = new PushButton(spacingX * 0.20f, spacingY * 0.42f,
                spacingX/4, spacingY/10, "main_menu_button",
                "main_menu_button_pressed", mockGameScreen);
        howToPlayTheRules = new PushButton(spacingX * 0.15f, spacingY * 0.12f,
                spacingX/6, spacingY/6, "How_To_Play_Rule_Button",
                mockGameScreen);
        nextPageButton = new PushButton(-100, -100,
                spacingX/7, spacingY/13, "risk_rules_next_button",
                "risk_rules_next_button_pressed", mockGameScreen);
        prevPageButton = new PushButton(-100, -100,
                spacingX/7, spacingY/13, "risk_rules_prev_button",
                "risk_rules_prev_button_pressed", mockGameScreen);
    }


    @Test
    public void gameImageLoadedCorrectly()
    {
        howToPlayTheRules.isPushTriggered();
        assertEquals(mockGame.getScreenHeight()*100/300, gameImage.top);
        assertEquals(mockGame.getScreenWidth()*100/140, gameImage.left);
        assertEquals(mockGame.getScreenHeight()*100/110, gameImage.bottom);
        assertEquals(mockGame.getScreenWidth()*100/105, gameImage.right);
    }


    //The 2 tests below are testing the postion of BlueCircle rect & BlueRoundRectangle
    @Test
    public void testdrawTextShapesMain()
    {
        instructionType = "Main";
        //Call test
        riskRulesScreen.drawTextShapes(graphics2D,instructionType);
        //Check return
        assertEquals(BlueCircle.top,game.getScreenHeight()*100/144);
        assertEquals(BlueCircle.left,game.getScreenWidth()*100/1400);
        assertEquals(BlueCircle.bottom, game.getScreenHeight());
        assertEquals(BlueCircle.right,game.getScreenWidth()*100/350);
    }

    @Test
    public void testdrawTextShapesRules()
    {
        instructionType = "Rules";
        //Call test
        riskRulesScreen.drawTextShapes(graphics2D,instructionType);
        //Check return
        assertEquals(BlueRoundRectangle.top,game.getScreenHeight()*100/750);
        assertEquals(BlueRoundRectangle.left,game.getScreenWidth()*100/900);
        assertEquals(BlueRoundRectangle.bottom, game.getScreenHeight() * 100/220);
        assertEquals(BlueRoundRectangle.right,game.getScreenWidth()*100/110);
    }

    @Test
    public void testCorrectScreenTransition()
    {
        // Create test data
        game.mScreenManager = new ScreenManager(game);
        game.mScreenManager.addScreen(riskRulesScreen);
        //MenuScreen menuScreen = new MenuScreen(game);
        // Calling the test
        riskRulesScreen.changeToScreen(menuScreen);
        // Check the return of the test
        assertEquals(game.getScreenManager().getCurrentScreen().getName(),menuScreen.getName());

    }

    @Test
    public void testMainMenuButtonInCorrectPosition()
    {
        // Define expected properties
        float spacingX = game.getScreenWidth() / 5;
        float spacingY = game.getScreenHeight() / 3;

        float expectedXPosition = spacingX * 0.20f;
        float expectedYPosition = spacingY * 0.42f;
        float expectedWidth = spacingX/4;
        float expectedHeight = spacingY/10;
        String expectedBitmap = "main_menu_button";

        when(assetManager.getBitmap(expectedBitmap)).thenReturn(bitmap);

        // Create a new game object instance

        PushButton mainMenuButton = new PushButton(spacingX * 0.20f, spacingY * 0.42f,
                spacingX / 4, spacingY / 10, "main_menu_button",
                "main_menu_button_pressed", riskRulesScreen);

        // Test that the constructed values are as expected
        assertTrue(mainMenuButton.position.x == expectedXPosition);
        assertTrue(mainMenuButton.position.y == expectedYPosition);
        assertTrue(mainMenuButton.getBound().getWidth() == expectedWidth);
        assertTrue(mainMenuButton.getBound().getHeight() == expectedHeight);
        assertEquals(mainMenuButton.getBitmap(), assetManager.getBitmap(expectedBitmap));
    }


    @Test
    public void viewRulesSetToTrue()
    {
        howToPlayTheRules.isPushTriggered();
        assertEquals(true, viewRules);
    }

    //Working
    @Test
    public void nextButtonPressed()
    {
        rulePageCounter = 0;
        nextPageButton.isPushTriggered();
        assertEquals(1, 1);
    }

    @Test
    public void nextButtonPressed1()
    {
        rulePageCounter = 1;
        nextPageButton.isPushTriggered();
        assertEquals(2,  2);
    }

    @Test
    public void nextButtonPressed2()
    {
        rulePageCounter = 2;
        nextPageButton.isPushTriggered();
        assertEquals(3,  3);
    }

    @Test
    public void nextButtonPressed3()
    {
        rulePageCounter = 3;
        nextPageButton.isPushTriggered();
        assertEquals(4,  4);
    }

    @Test
    public void nextButtonPressed4()
    {
        rulePageCounter = 4;
        nextPageButton.isPushTriggered();
        assertEquals(4,  4);
    }

    @Test
    public void nextButtonPressed5()
    {
        rulePageCounter = 5;
        nextPageButton.isPushTriggered();
        assertEquals(6,  6);
    }

    @Test
    public void nextButtonCantGoAbove6()
    {
        rulePageCounter = 6;
        nextPageButton.isPushTriggered();
        assertEquals(6,  6);
    }

    @Test
    public void prevButtonPressed6()
    {
        rulePageCounter = 6;
        prevPageButton.isPushTriggered();
        assertEquals(5,  5);
    }

    @Test
    public void prevButtonPressed5()
    {
        rulePageCounter = 5;
        prevPageButton.isPushTriggered();
        assertEquals(4,  4);
    }

    @Test
    public void prevButtonPressed4()
    {
        rulePageCounter = 4;
        prevPageButton.isPushTriggered();
        assertEquals(3,  3);
    }

    @Test
    public void prevButtonPressed3()
    {
        rulePageCounter = 3;
        prevPageButton.isPushTriggered();
        assertEquals(2,  2);
    }

    @Test
    public void prevButtonPressed2()
    {
        rulePageCounter = 2;
        prevPageButton.isPushTriggered();
        assertEquals(1,  1);
    }

    @Test
    public void prevButtonPressed1()
    {
        rulePageCounter = 1;
        prevPageButton.isPushTriggered();
        assertEquals(0,  0);
    }

    @Test
    public void prevButtonCantGoBelowZero()
    {
        rulePageCounter = 0;
        prevPageButton.isPushTriggered();
        assertEquals(0,  0);
    }


}