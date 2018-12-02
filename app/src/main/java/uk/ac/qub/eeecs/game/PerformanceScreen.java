package uk.ac.qub.eeecs.game;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class PerformanceScreen extends GameScreen {

    /**
     * Create a new game screen associated with the specified game instance
     *
     * @param name
     * @param game Game instance to which the game screen belongs
     */

    private PushButton mReturnToMenuButton;
    private PushButton mIncreaseSizeButton;
    private PushButton mDecreaseSizeButton;
    private float mTimeToChange = 0;

    /** Define the size of the world within which the game objects will
     * be created and within the layer viewport will be confined.
     */
    private final static float WORLD_WIDTH = 500.0f;
    private final static float WORLD_HEIGHT = 250.0f;

    /**
     * Define the size of each game object that will be created within
     * the world.
     */
    private final static int GAMEOBJECT_WIDTH = 20;
    private final static int GAMEOBJECT_HEIGHT = 20;

    /**
     * Define tne number of and associated storage for the game objects
     * that will live inside this world.
     */
    private final static int NUM_GAMEOBJECTS = 100;
    private GameObject[] mGameObjects = new GameObject[NUM_GAMEOBJECTS];

    /**
     * Define a number of viewports - two for the game layer and two
     * for the screen. One pair of viewports will provide a mapping from
     * a window into the large game world (game layer viewport) and
     * display any game objects within the viewport on the screen
     * of the device (game screen viewport). The other two viewports will
     * be used to provide a high-level map view of the world. In this
     * case the layer viewport (map layer viewport) will capture the
     * entire game world and draw this onto a small region of the
     * device's screen (defined by map screen viewport).
     */

    private LayerViewport mGameLayerViewport;
    private ScreenViewport mGameScreenViewport;

    /**
     * Define the width of the layer viewport. The height of the layer
     * viewport will be calculated based on the display screen aspect
     * ratio to ensure that drawn objects maintain the right length/width
     * ratio. The size of the game viewport will determine how much of the
     * world is visible on screen at any one time.
     */
    private final static float FOCUSED_VIEWPORT_WIDTH = 100.0f;



    public PerformanceScreen(String name, Game game) {
        super(name, game);

        // Load in the bitmaps used on the performance screen
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        assetManager.loadAndAddBitmap("GreenPlus", "img/GreenPlus.png");
        assetManager.loadAndAddBitmap("RedMinus", "img/RedMinus.png");
        assetManager.loadAndAddBitmap("TexturedRectangle", "img/TexturedRectangle.png");


        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 3;

        mReturnToMenuButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.80f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "BackArrow", "BackArrowSelected", this);
        mReturnToMenuButton.setPlaySounds(true, true);
        mIncreaseSizeButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.10f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "GreenPlus","GreenPlus",  this);
        mDecreaseSizeButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.20f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "RedMinus","RedMinus",  this);

        // Whenever a game screen is created the constructor (called through super(...)
        // will automatically generate two viewports - mDefaultLayerViewport and
        // mDefaultScreenViewport. The default layer viewport is a 480x320 sized region
        // and the default screen viewport is a center 3:2 aspect ratio region.
        // For this demo we won't be using or modifying these viewports, instead we'll
        // create new viewports.

        float screenWidth = mGame.getScreenWidth();
        float screenHeight = mGame.getScreenHeight();

        // Build the two layers viewports. The map layer viewport is created to be the
        // same size as the entire game world (because the map viewport displays the
        // whole world. In contrast, the game viewport is sized based on a specified
        // width value. The height of the viewport is calculated from this width and also
        // the aspect ratio of the screen we will be drawing to. This ensures that when
        // drawing from the game viewport onto the screen viewport objects will correctly
        // display (e.g. a square object will be displayed as a square on screen)

        float aspectRatio = screenHeight / screenWidth;
        mGameLayerViewport = new LayerViewport(
                WORLD_WIDTH / 2.0f, WORLD_HEIGHT / 2.0f,
                FOCUSED_VIEWPORT_WIDTH,
                aspectRatio * FOCUSED_VIEWPORT_WIDTH);

        // The game screen viewport is simply sized to take over all of the drawable
        // space on the screen.
        mGameScreenViewport =
                new ScreenViewport(0, 0, (int) screenWidth, (int) screenHeight);

        // Create a bunch of game objects that can be randomly positioned within
        // the world to provide something that can be seen as the viewport moves.

        Random random = new Random();
        for (int idx = 0; idx < NUM_GAMEOBJECTS; idx++) {
            GameObject platform = new GameObject(
                    random.nextInt((int) WORLD_WIDTH), random.nextInt((int) WORLD_HEIGHT),
                    random.nextInt((int) GAMEOBJECT_WIDTH), random.nextInt((int) GAMEOBJECT_HEIGHT),
                    assetManager.getBitmap("TexturedRectangle"), this);
            mGameObjects[idx] = platform;
        }
    }

    @Override
    public void update(ElapsedTime elapsedTime) {
// Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0 && mTimeToChange > 0.5f) {

            // Update each button and transition if needed
            mReturnToMenuButton.update(elapsedTime);
            mIncreaseSizeButton.update(elapsedTime);
            mDecreaseSizeButton.update(elapsedTime);
            if (mReturnToMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);
            if (mIncreaseSizeButton.isPushTriggered())
            {
                for(GameObject platform : mGameObjects)
                {
                    platform.setHeight(platform.getHeight() + 1.0f);
                    platform.setWidth(platform.getWidth() + 1.0f);
                }
            }
            if (mDecreaseSizeButton.isPushTriggered())
            {
                for(GameObject platform : mGameObjects)
                {
                    platform.setHeight(platform.getHeight() - 1.0f);
                    platform.setWidth(platform.getWidth() - 1.0f);
                }
            }
        }

        mTimeToChange += elapsedTime.stepTime;
    }

    //private Paint rectPaint = new Paint();

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);

        // Go through each game object and draw it against the game layer viewport,
        // mGameLayerViewport - any object that isn't visible within this viewport
        // won't be displayed. Aside: for 1000 objects most won't be visible within
        // the game viewport at any one point in time - hence this is inefficient.
        // A scene graph can be used to more efficiently determine which objects
        // may be visible if desired.

        for (GameObject platforms : mGameObjects) {
            platforms.draw(elapsedTime, graphics2D, mGameLayerViewport, mGameScreenViewport);
        }




        mReturnToMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mIncreaseSizeButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mDecreaseSizeButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
    }
}
