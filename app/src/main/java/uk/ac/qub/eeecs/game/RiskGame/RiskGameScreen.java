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
    private final int ATTACK_NULL = 0;
    private final int ATTACK_PICK = 1;
    private final int ATTACK_PICK_AGAIN = 2;
    private final int ATTACK_BATTLING = 3;
    private String attackStr = "State: Not battling.";
    private PushButton mAttackButton;
    private Bitmap mRiskGameScreenBackground;
    private GameObject mRiskMap;
    // Used to store the last touched area
    private Area[] mTouchedArea = new Area[2];
    private int attackState = ATTACK_NULL;

    // ArrayList for Areas and Players
    private final int MAX_AREAS = 5;
    private final int MAX_PLAYERS = 3;
    private ArrayList<Player> mPlayers = new ArrayList<>(MAX_PLAYERS);
    private ArrayList<Area> mAreas = new ArrayList<>(MAX_AREAS);
    private Field[] mFields;
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

        //assetManager.loadAndAddBitmap("RiskGameScreen2", "img/RiskGamesImages/RiskGameScreen2.png");
        assetManager.loadAndAddBitmap("RiskAttackButton", "img/RiskGameImages/risk_attack_pressed.png");

        mRiskGameScreenBackground = assetManager.getBitmap("RiskGameScreen2");



        // Generate Area objects
        createAreas();
        // Generate Player objects
        createPlayers();
        mFields = GenerateFields();

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
        mAttackButton = new PushButton(
                mDefaultLayerViewport.getWidth() * 0.95f,
                mDefaultLayerViewport.getHeight() * 0.60f,
                mDefaultLayerViewport.getWidth() * 0.075f,
                mDefaultLayerViewport.getHeight() * 0.10f,
                "RiskAttackButton", "RiskAttackButton", this);
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

            // If we're in attacking mode get a touched area
            //if(attackState != ATTACK_NULL) getAreaClicked();
            if(attackState == ATTACK_PICK) {
                Area tmpArea = getAreaClicked();
                if(tmpArea != null) {
                    mTouchedArea[0] = tmpArea;
                    attackState = ATTACK_PICK_AGAIN;
                }
            }
            else if(attackState == ATTACK_PICK_AGAIN) {
                Area tmpArea = getAreaClicked();
                if(tmpArea != null) {
                    if (tmpArea != mTouchedArea[0]) {
                        mTouchedArea[1] = getAreaClicked();
                        attackState = ATTACK_BATTLING;
                        beginBattle(mTouchedArea[0], mTouchedArea[1]);
                    }
                }
            }


            mReturnToMenuButton.update(elapsedTime);
            if (mReturnToMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

            // Changing attack state
            mAttackButton.update(elapsedTime);
            if (mAttackButton.isPushTriggered())
                attackState = ATTACK_PICK;
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
        mAttackButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);

        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 30.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw text displaying the name of this screen and some instructions
        String playersString = "Players: [ ";
        for(int i = 0; i < MAX_PLAYERS; i++) playersString += mPlayers.get(i).getName() + " ";
        graphics2D.drawText(playersString + "]",
                0.0f, lineHeight, textPaint);
        String areasString = "Areas: [ ";
        for(int i = 0; i < MAX_AREAS; i++) areasString += "(" + mAreas.get(i).getName() + ") ";
        graphics2D.drawText(areasString + "]",
                0.0f, lineHeight + 40.0f, textPaint);
        graphics2D.drawText("Screen: [" + this.getName() + "]",
                0.0f, lineHeight + 80.0f, textPaint);
        graphics2D.drawText("Touch[0]: " + mTouchedArea[0].getName() + " Touch[1]: " + mTouchedArea[1].getName(),
                0.0f, lineHeight + 120.0f, textPaint);

        switch(attackState) {
            case ATTACK_NULL: attackStr = "State: Not battling."; break;
            case ATTACK_PICK: attackStr = "State: Pick area 1."; break;
            case ATTACK_PICK_AGAIN: attackStr = "State: Pick area 2."; break;
            case ATTACK_BATTLING: attackStr = "State: Battle: " + mTouchedArea[0].getName() + " vs. " + mTouchedArea[1].getName(); break;
            default: attackStr = "State: Not battling."; break;
        }
        graphics2D.drawText(attackStr,
                0.0f, lineHeight + 160.0f, textPaint);

        /* Work In Progress
        graphics2D.drawText("Winner: " + winner.getName() + " Loser: " + loser.getName(),
                0.0f, lineHeight + 200.0f, textPaint);
        */

    }
    ///////////////////////////////////////////////////
    //other methods;
    //////////////////////////////////////////////////

    private void createAreas() {

        // Generate the areas and add colour
        mAreas.add(new Area("Telecommunications", 0xFFeb1c23));
        mAreas.add(new Area("Security", 0xFF0ecf42));
        mAreas.add(new Area("Development", 0xFFffe51c));
        mAreas.add(new Area("Machine Learning", 0xFFb87756));
        mAreas.add(new Area("Data & Information", 0xFFb63eb8));
        // Generate the fields for the areas


        // Field class should probably be revamped
        mAreas.get(0).addField(new Field(this, 1, "Internet Provider", null, 5));
        mAreas.get(0).addField(new Field(this, 1, "Phone Carrier", null, 5));
        mAreas.get(1).addField(new Field(this, 1, "Cyber Security", null, 5));
        mAreas.get(1).addField(new Field(this, 1, "CCTV", null, 5));
        mAreas.get(2).addField(new Field(this, 1, "C++", null, 5));
        mAreas.get(2).addField(new Field(this, 1, "Java", null, 5));
        mAreas.get(3).addField(new Field(this, 1, "General Intelligence", null, 5));
        mAreas.get(3).addField(new Field(this, 1, "AI Cars", null, 5));
        mAreas.get(4).addField(new Field(this, 1, "Social Media", null, 5));
        mAreas.get(4).addField(new Field(this, 1, "Research Labs", null, 5));

        mTouchedArea[0] = mAreas.get(0); // null results in crash when displaying getName() text
        mTouchedArea[1] = mAreas.get(0); // null results in crash when displaying getName() text
    }

    private void createPlayers() {
        // Generate the players and add colour
        mPlayers.add(new Player("Microsoft", Color.BLACK));
        mPlayers.add(new Player("Google", Color.GREEN));
        mPlayers.add(new Player("Apple", Color.RED));
    }
    private Field[] GenerateFields()
    {
        Field[] fields = new Field[2];
        fields[0] = new Field(50, 50, 50, 50, null, this, 1, "AI", mPlayers.get(0), 5);
        fields[1] = new Field(50, 50, 50, 50, null, this, 2, "SelfDrivingCars", mPlayers.get(1), 5);
        return fields;
    }

    private Area getAreaClicked() {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();

        for (TouchEvent touchEvent : touchEvents) {
            Vector2 layerPosition = new Vector2();
            ViewportHelper.convertScreenPosIntoLayer(
                    mDefaultScreenViewport, touchEvent.x, touchEvent.y,
                    mDefaultLayerViewport, layerPosition);

            BoundingBox bound = mRiskMap.getBound();
            if (bound.contains(layerPosition.x, layerPosition.y)) {

                float xLoc = (layerPosition.x - bound.getLeft()) / bound.getWidth();
                float yLoc = (bound.getTop() - layerPosition.y) / bound.getHeight();

                Bitmap bitmap = mRiskMap.getBitmap();
                int colour = bitmap.getPixel(
                        (int) (xLoc * bitmap.getWidth()),
                        (int) (yLoc * bitmap.getHeight()));

                // Detects pixel colour and compares to the list of areas
                // The background image colours match mAreas colours)
                for (int i = 0; i < mAreas.size(); i++) {
                    if (colour == mAreas.get(i).getColour()) {
                        return mAreas.get(i);
                    }
                }
            }
        }
        // Be careful, returning null can cause crashes
        return null;
    }

    private void beginBattle(Area att, Area def) {
        // Something here...

        Battle battle = new Battle(3, 3,
                att.getField(0).getFNumOfTeams()-1, def.getField(0).getFNumOfTeams()-1);
        // Transfer ownership of lost fields should be done here...
        // Not really doing much here, battle happens but need to display/do something...
    }

    private void Battle(Field Attacker, Field Defender) {
        Battle tempBattle = new Battle(3,3, Attacker.getFNumOfTeams() - 1, Defender.getFNumOfTeams());
        int[] Results = tempBattle.Battling();
        mFields[Attacker.getFNum()].decreaseNumOfTeams(Results[0]);
        if(Results[2] == 0)
        {
            mFields[Defender.getFNum()].hostileTakeOver(mFields[Attacker.getFNum()].getFPlayer(),  mFields[Attacker.getFNum()].getFNumOfTeams()-1);

        }
        else
        {
            mFields[Defender.getFNum()].decreaseNumOfTeams(Results[1]);
        }
    }
}
