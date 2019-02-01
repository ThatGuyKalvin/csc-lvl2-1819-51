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

    private Bitmap mRiskGameScreenBackground;
    private GameObject mRiskMap;
    private Area mTouchedArea;
    private String touchedAreaColour;

    private final int MAX_AREAS = 5;
    private final int MAX_PLAYERS = 3;
    private Area[] mAreas = new Area[MAX_AREAS];
    private Player[] mPlayers = new Player[MAX_PLAYERS];
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

                    // The colours should be changed to work by .getColour() in the future
                    switch (colour)
                    {
                        //RED
                        case 0xFFeb1c23: mTouchedArea = mAreas[0]; break;
                        //GREEN
                        case 0xFF0ecf42: mTouchedArea = mAreas[1]; break;
                        //YELLOW
                        case 0xFFffe51c: mTouchedArea = mAreas[2]; break;
                        //BROWN
                        case 0xFFb87756: mTouchedArea = mAreas[3]; break;
                        //Purple
                        case 0xFFb63eb8: mTouchedArea = mAreas[4]; break;
                    }
                    //touchedAreaColour = (new Integer(colour)).toString();
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
        textPaint.setColor(mPlayers[0].getColour());
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw text displaying the name of this screen and some instructions
        String playersString = "Players: [ ";
        for(int i = 0; i < MAX_PLAYERS; i++) playersString += mPlayers[i].getName() + " ";
        graphics2D.drawText(playersString + "]",
                0.0f, lineHeight, textPaint);
        String areasString = "Areas: [ ";
        for(int i = 0; i < MAX_AREAS; i++) areasString += "(" + mAreas[i].getName() + ") ";
        graphics2D.drawText(areasString + "]",
                0.0f, lineHeight + 40.0f, textPaint);
        graphics2D.drawText("Screen: [" + this.getName() + "]",
                0.0f, lineHeight + 80.0f, textPaint);
        graphics2D.drawText("Touched: " + mTouchedArea.getName(),
                0.0f, lineHeight + 120.0f, textPaint);
        // Used during testing...
        /*graphics2D.drawText("Touched Colour: " + touchedAreaColour,
                0.0f, lineHeight + 160.0f, textPaint);*/

    }
    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    private void createAreas() {

        // Generate the areas and add colour
        for(int i = 0; i < MAX_AREAS; i++) {

            mAreas[i] = new Area();
            mAreas[i].setColour(i); // give each area a colour
        }

        // Code could crash if MAX_AREAS is less than 5
        // Setting the names of the areas
        mAreas[0].setName("Telecommunications");
        mAreas[1].setName("Security");
        mAreas[2].setName("Development");
        mAreas[3].setName("Machine Learning");
        mAreas[4].setName("Data & Information");
        mTouchedArea = mAreas[0];
    }

    private void createPlayers() {
        mPlayers[0] = new Player("Microsoft", Color.BLACK);
        mPlayers[1] = new Player("Google", Color.GREEN);
        mPlayers[2] = new Player("Apple", Color.RED);
    }
}
