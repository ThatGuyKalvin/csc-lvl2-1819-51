package uk.ac.qub.eeecs.game.RiskGame;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;

/**
 * Starter class for Card game stories
 *
 * @version 1.0
 */
public class RiskGameScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    // The game map
    private Bitmap mRiskGameScreenBackground;
    private GameObject mRiskMap;
    // Used to store the last touched area
    private Area mTouchedArea;

    // ArrayList for Areas and Players
    private final int MAX_AREAS = 5;
    private final int MAX_PLAYERS = 3;
    private ArrayList<Player> mPlayers = new ArrayList<>(MAX_PLAYERS);
    private ArrayList<Area> mAreas = new ArrayList<>(MAX_AREAS);

    // Paint for the debug info displayed
    private Paint textPaint = new Paint();

    private PushButton mReturnToMenuButton;
    private ScreenViewport mGameScreenViewport;
    private float mTimeToChange = 0;

    public RiskGameScreen(Game game) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");

        assetManager.loadAssets(
                "txt/assets/RiskGameAssets.JSON");

        assetManager.loadAndAddBitmap("RiskGameScreen2", "img/RiskGamesImages/RiskGameScreen2.png");

        mRiskGameScreenBackground = assetManager.getBitmap("RiskGameScreen2");



        // Generate Area objects
        createAreas();
        // Generate Player objects
        createPlayers();

        mRiskMap = new GameObject(
                mDefaultLayerViewport.x, mDefaultLayerViewport.y,
                mDefaultLayerViewport.getWidth(), mDefaultLayerViewport.getHeight(),
                game.getAssetManager().getBitmap("RiskGameScreen2"), this);

        mReturnToMenuButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.80f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "BackArrow", "BackArrowSelected", this);
        mReturnToMenuButton.setPlaySounds(true, true);

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        mGameScreenViewport =
                new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);


    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the card demo screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {
        // Process any touch events occurring since the last update
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            for(TouchEvent touchEvent : touchEvents) {

                Vector2 layerPosition = new Vector2();
                ViewportHelper.convertScreenPosIntoLayer(
                        mDefaultScreenViewport, touchEvent.x, touchEvent.y,
                        mDefaultLayerViewport, layerPosition);

                BoundingBox bound = mRiskMap.getBound();
                if(bound.contains(layerPosition.x, layerPosition.y)) {

                    float xLoc = (layerPosition.x - bound.getLeft())/bound.getWidth();
                    float yLoc = (bound.getTop() - layerPosition.y)/bound.getHeight();

                    Bitmap bitmap = mRiskMap.getBitmap();
                    int colour = bitmap.getPixel(
                            (int)(xLoc * bitmap.getWidth()),
                            (int)(yLoc * bitmap.getHeight()));

                    // Detects pixel colour and compares to the list of areas
                    // The background image colours match mAreas colours)
                    for(int i = 0; i < mAreas.size(); i++) {
                        if(colour == mAreas.get(i).getColour()) {
                            mTouchedArea = mAreas.get(i);
                            break;
                        }
                    }
                }
            }

            mReturnToMenuButton.update(elapsedTime);
            if (mReturnToMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);
        }
        mTimeToChange += elapsedTime.stepTime;
    }

    /**
     * Draw the card demo screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        graphics2D.clear(Color.WHITE);
        int screenWidth = graphics2D.getSurfaceWidth();
        int screenHeight = graphics2D.getSurfaceHeight();

        Rect sourceRectBackg = new Rect(0,0, mRiskGameScreenBackground.getWidth(), mRiskGameScreenBackground.getHeight());
        Rect destRectBackg = new Rect((int) (screenWidth * 0.0f), (int) (screenHeight * 0.0f), (int) (screenWidth * 1.0f), (int) (screenHeight * 1.0f));
        graphics2D.drawBitmap(mRiskGameScreenBackground, sourceRectBackg, destRectBackg, null);


        mReturnToMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 30.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setColor(mPlayers.get(0).getColour());
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw text displaying the name of this screen and some instructions
        String playersString = "Players: [ ";
        for(int i = 0; i < mPlayers.size(); i++) playersString += mPlayers.get(i).getName() + " ";
        graphics2D.drawText(playersString + "]",
                0.0f, lineHeight, textPaint);
        String areasString = "Areas: [ ";
        for(int i = 0; i < mAreas.size(); i++) areasString += "(" + mAreas.get(i).getName() + ") ";
        graphics2D.drawText(areasString + "]",
                0.0f, lineHeight + 40.0f, textPaint);
        graphics2D.drawText("Screen: [" + this.getName() + "]",
                0.0f, lineHeight + 80.0f, textPaint);
        graphics2D.drawText("Touched: " + mTouchedArea.getName(),
                0.0f, lineHeight + 120.0f, textPaint);

    }
    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    private void createAreas() {

        // Generate the areas and add colour
        mAreas.add(new Area("Telecommunications", 0xFFec1c24));
        mAreas.add(new Area("Security", 0xFF0ed145));
        mAreas.add(new Area("Development", 0xFFfff200));
        mAreas.add(new Area("Machine Learning", 0xFFb97a56));
        mAreas.add(new Area("Data & Information", 0xFFb83dba));
        mTouchedArea = mAreas.get(0); // null results in crash.
    }

    private void createPlayers() {
        // Generate the players and add colour
        mPlayers.add(new Player("Microsoft", Color.BLACK));
        mPlayers.add(new Player("Google", Color.GREEN));
        mPlayers.add(new Player("Apple", Color.RED));
    }
}
