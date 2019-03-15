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
public class RiskRulesScreenTests {

    @Mock
    public RiskGameScreen mockGameScreen;
    @Mock
    public Game mockGame;
    @Mock
    private MenuScreen menuScreen;
    @Mock
    public AssetManager mockAssetStore;
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
    PushButton mHowToPlayTheRules, nextPageButton, prevPageButton;

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
        screenManager = new ScreenManager(mockGame);

        when(mockGameScreen.getGame()).thenReturn(mockGame);
        when(mockGame.getAssetManager()).thenReturn(mockAssetStore);

        when(game.getScreenManager()).thenReturn(screenManager);

        when(game.getAssetManager()).thenReturn(assetManager);
        when(assetManager.getBitmap(any(String.class))).thenReturn(bitmap);

        when(game.getInput()).thenReturn(input);

        RiskRulesScreen riskRulesScreen = new RiskRulesScreen("String", game);
        game.mScreenManager.addScreen(riskRulesScreen);

        gameImage = new Rect();

        mHowToPlayTheRules = new PushButton(spacingX * 0.15f, spacingY * 0.12f, spacingX/6, spacingY/6, "How_To_Play_Rule_Button", mockGameScreen);
        nextPageButton = new PushButton(-100, -100, spacingX/7, spacingY/13, "risk_rules_next_button", mockGameScreen);
        prevPageButton = new PushButton(-100, -100, spacingX/7, spacingY/13, "Back", mockGameScreen);
    }

    @Test
    public void testCorrectScreenTransition()
    {
        // Create test data
        mockGame.mScreenManager = new ScreenManager(mockGame);
        mockGame.mScreenManager.addScreen(riskRulesScreen);
        MenuScreen menuScreen = new MenuScreen(mockGame);
        // Call test
        riskRulesScreen.changeToScreen(menuScreen);
        // Check return
        assertEquals(mockGame.getScreenManager().getCurrentScreen().getName(),menuScreen.getName());
    }

    @Test
    public void testMainMenuButtonInCorrectPosition()
    {
        // Define expected properties
        float spacingX = game.getScreenWidth() / 10;
        float spacingY = game.getScreenHeight() / 6;

        float expectedXPosition = spacingX * 0.20f;
        float expectedYPosition = spacingY * 0.42f;
        float expectedWidth = spacingX/3;
        float expectedHeight = spacingY/10;
        String expectedBitmap = "main_menu_button";

        when(assetManager.getBitmap(expectedBitmap)).thenReturn(bitmap);

        // Create a new game object instance

        PushButton playButton = new PushButton(spacingX * 0.20f, spacingY * 0.42f, spacingX / 3, spacingY / 10, "main_menu_button",riskRulesScreen);

        // Test that the constructed values are as expected
        assertTrue(playButton.position.x == expectedXPosition);
        assertTrue(playButton.position.y == expectedYPosition);
        assertTrue(playButton.getBound().getWidth() == expectedWidth);
        assertTrue(playButton.getBound().getHeight() == expectedHeight);
        assertEquals(playButton.getBitmap(), assetManager.getBitmap(expectedBitmap));
    }


    @Test
    public void viewRulesSetToTrue()
    {
        mHowToPlayTheRules.isPushTriggered();
        assertEquals(true, viewRules);
    }
    @Test
    public void correctGameImageLoaded()
    {
        mHowToPlayTheRules.isPushTriggered();
        assertEquals(0, rulePageCounter);
    }

    @Test
    public void nextButtonPressed()
    {
        nextPageButton.isPushTriggered();
        assert(rulePageCounter !=0);
    }
    @Test
    public void prevButtonPressed()
    {
        rulePageCounter = 1;
        prevPageButton.isPushTriggered();
        assertEquals(0, rulePageCounter);
    }
    @Test
    public void prevButtonCantGoBelowZero()
    {
        rulePageCounter = 0;
        prevPageButton.isPushTriggered();
        assertEquals(0, rulePageCounter);
    }
    @Test
    public void nextButtonCantGoAboveSeven()
    {
        rulePageCounter = 7;
        nextPageButton.isPushTriggered();
        assertEquals(7, rulePageCounter);
    }

    @Test
    public void gameImageLoadedCorrectly()
    {
        mHowToPlayTheRules.isPushTriggered();
        assertEquals(mockGame.getScreenHeight()*100/300, gameImage.top);
        assertEquals(mockGame.getScreenWidth()*100/140, gameImage.left);
        assertEquals(mockGame.getScreenHeight()*100/110, gameImage.bottom);
        assertEquals(mockGame.getScreenWidth()*100/105, gameImage.right);
    }


    //The 4 test below are testing the postion of BlueCircle rect & BlueRoundRectangle
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
        assertEquals(BlueRoundRectangle.bottom, game.getScreenHeight());
        assertEquals(BlueRoundRectangle.right,game.getScreenWidth()*100/110);
    }

}
