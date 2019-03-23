package uk.ac.qub.eeecs.game.RiskGame;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    private final int ALLOCATE = 4;
    private String attackStr = "State: Not battling.";
    private PushButton mAttackButton;
    private Bitmap mRiskGameScreenBackground;
    private Bitmap mRiskMapAreas;
    private GameObject mRiskMap;
    // Used to store the last touched area
    private Area[] mTouchedArea = new Area[2];
    private Field[] mFieldsAttacking = new Field[2];
    private Field mFieldTouched;
    private int state = ATTACK_NULL;
    private int clickedColour = 0;

    // ArrayList for Areas and Players
    private final int MAX_AREAS = 5;
    private final int MAX_PLAYERS = 3;
    private ArrayList<Player> mPlayers = new ArrayList<>(MAX_PLAYERS);
    private ArrayList<Area> mAreas = new ArrayList<>(MAX_AREAS);
    private int CurrentPlayerNum = 0;
    private boolean SuccessfulAttack = false;
    private Paint textPaint = new Paint();

    private PushButton mReturnToMenuButton;
    private PushButton mMainMenuButton;
    private PushButton mEndTurnButton;
    private ScreenViewport mGameScreenViewport;
    private float mTimeToChange = 0;

    public RiskGameScreen(Game game) {
        super("RiskScreen", game);
        AssetManager assetManager = mGame.getAssetManager();
        assetManager.loadAssets(
                "txt/assets/OptionsScreenAssets.JSON");

        assetManager.loadAssets(
                "txt/assets/RiskGameAssets.JSON");
        assetManager.loadAndAddBitmap("BackArrow", "img/BackArrow.png");
        assetManager.loadAndAddBitmap("BackArrowSelected", "img/BackArrowSelected.png");
        assetManager.loadAndAddBitmap("main_menu_button", "img/RiskGameImages/main_menu_button.png");
        assetManager.loadAndAddBitmap("main_menu_button_pressed", "img/RiskGameImages/main_menu_button_pressed.png");
        assetManager.loadAndAddBitmap("end_turn_button","img/RiskGameImages/end_turn_button.png");
        assetManager.loadAndAddBitmap("end_turn_pressed", "img/RiskGameImages/end_turn_pressed.png");

        //assetManager.loadAndAddBitmap("RiskGameScreen2", "img/RiskGamesImages/RiskGameScreen2.png");
        assetManager.loadAndAddBitmap("RiskAttackButton", "img/RiskGameImages/risk_attack_pressed.png");

        mRiskGameScreenBackground = assetManager.getBitmap("RiskGameScreen2");
        mRiskMapAreas = assetManager.getBitmap("RiskGameScreen3");



        // Generate Area objects
        createAreas();
        // Generate Player objects
        createPlayers();
        //Assign Players fields
        assignFields();
        //Decide which player goes first
        firstTurn();
        // Allocate teams
        AllocateTeams(initialTeamsCalc());
        //Begin First players turn
        beginTurn();

        // Define the spacing that will be used to position the buttons
        int spacingX = (int)mDefaultLayerViewport.getWidth() / 5;
        int spacingY = (int)mDefaultLayerViewport.getHeight() / 15;

        // Create the trigger buttons
        mMainMenuButton = new PushButton(
                spacingX * 0.8f, spacingY * 1.4f, spacingX, spacingY,
                "main_menu_button", "main_menu_button_pressed", this);
        mMainMenuButton.setPlaySounds(true, true);

        mEndTurnButton = new PushButton(spacingX * 1.5f, spacingY * 1f, spacingX, spacingY, "end_turn_button", "end_turn_pressed", this);

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


            // Update each button and transition if needed
            mMainMenuButton.update(elapsedTime);
            mEndTurnButton.update(elapsedTime);

            // If we're in attacking mode get a touched area
            //if(state != ATTACK_NULL) getAreaClicked();
            if(state == ATTACK_PICK) {
                Field tmpField = getFieldClicked();
                if(tmpField != null) {
                    mFieldsAttacking[0] = tmpField;
                    state = ATTACK_PICK_AGAIN;
                }
            }
            else if (mMainMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

            else if (mEndTurnButton.isPushTriggered())
                endTurn();

            else if(state == ATTACK_PICK_AGAIN) {
                Field tmpField = getFieldClicked();
                if(tmpField != null && tmpField != mFieldsAttacking[0]) {
                    mFieldsAttacking[1] = tmpField;
                    state = ATTACK_BATTLING;
                }
            }

            else if(state == ALLOCATE) {
                Field tmpField = getFieldClicked();
                if(tmpField != null && findPlayerFields(CurrentPlayerNum).contains(tmpField)){
                    tmpField.incrementNumOfTeams();
                    state = ATTACK_NULL;
                }
            }

            if (state == ATTACK_BATTLING){
                beginBattle(mFieldsAttacking[0], mFieldsAttacking[1]);
                state = ATTACK_NULL;
            }

            mReturnToMenuButton.update(elapsedTime);
            if (mReturnToMenuButton.isPushTriggered())
                mGame.getScreenManager().removeScreen(this);

            // Changing attack state
            mAttackButton.update(elapsedTime);
            if (mAttackButton.isPushTriggered())
                state = ATTACK_PICK;
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
        mMainMenuButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);
        mEndTurnButton.draw(elapsedTime, graphics2D, mDefaultLayerViewport, mDefaultScreenViewport);


        float textSize =
                ViewportHelper.convertXDistanceFromLayerToScreen(
                        mDefaultLayerViewport.getHeight() * 0.05f,
                        mDefaultLayerViewport, mDefaultScreenViewport);

        float lineHeight = screenHeight / 30.0f;
        textPaint.setTextSize(lineHeight);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        boolean enabled = true;
        if(enabled) {
            // Draw text displaying the name of this screen and some instructions
            String playersString = "Players: [ ";
            for (int i = 0; i < MAX_PLAYERS; i++) playersString += mPlayers.get(i).getName() + " ";
            graphics2D.drawText(playersString + "]",
                    0.0f, lineHeight, textPaint);
            String areasString = "Areas: [ ";
            for (int i = 0; i < MAX_AREAS; i++) areasString += "(" + mAreas.get(i).getName() + ") ";
            graphics2D.drawText(areasString + "]",
                    0.0f, lineHeight + 40.0f, textPaint);
            graphics2D.drawText("Screen: [" + this.getName() + "]",
                    0.0f, lineHeight + 80.0f, textPaint);
            if(mFieldTouched != null){
                graphics2D.drawText("Touch[0]: " + mFieldTouched.getFName() + " Number of teams assigned: " + mFieldTouched.getFNumOfTeams() + " Owned by: " + mFieldTouched.getFPlayer().getName(),
                        0.0f, lineHeight + 120.0f, textPaint);
            }

            switch (state) {
                case ATTACK_NULL:
                    attackStr = "State: Not battling.";
                    break;
                case ATTACK_PICK:
                    attackStr = "State: Pick area 1.";
                    break;
                case ATTACK_PICK_AGAIN:
                    attackStr = "State: Pick area 2.";
                    break;
                case ATTACK_BATTLING:
                    attackStr = "State: Battle: " + mFieldsAttacking[0].getFName() + " vs. " + mFieldsAttacking[1].getFName();
                    break;
                case ALLOCATE:
                    attackStr = "State: Player " + CurrentPlayerNum + " Allocate Team";
                    break;
                default:
                    attackStr = "State: Not battling.";
                    break;
            }
            graphics2D.drawText(attackStr,
                    0.0f, lineHeight + 160.0f, textPaint);
            graphics2D.drawText("clickedColour:" + Integer.toHexString(clickedColour),
                    0.0f, lineHeight + 200.0f, textPaint);
        }

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
        mAreas.add(new Area("Telecommunications", 0xFFeb1c23, 6));
        mAreas.add(new Area("Security", 0xFF0ecf42, 7));
        mAreas.add(new Area("Development", 0xFFffe51c, 4));
        mAreas.add(new Area("Machine Learning", 0xFFb87756,5));
        mAreas.add(new Area("Data & Information", 0xFFb63eb8,4));
        // Generate the fields for the areas

        // Telecommunications
        mAreas.get(0).addField(new Field(1,"Internet Provider",0xFFDE7879));
        mAreas.get(0).addField(new Field(2,"Phone Carrier",0xFF9C0003));
        mAreas.get(0).addField(new Field(3,"VoIP",0xFFF0ADAD));
        mAreas.get(0).addField(new Field(4,"Radio",0xFFB84B4B));
        mAreas.get(0).addField(new Field(5, "Telegraphy",0xFF5C0000));
        // Security
        mAreas.get(1).addField(new Field(6, "Cyber Security",0xFF007820));
        mAreas.get(1).addField(new Field(7, "CCTV",0xFF4AAB3F));
        mAreas.get(1).addField(new Field(8, "Audit Trails",0xFF24401F));
        mAreas.get(1).addField(new Field(9, "TFA",0xFF7AC28C));
        mAreas.get(1).addField(new Field(10, "Firewall",0xFF648F5D));
        mAreas.get(1).addField(new Field(11, "AntiVirus",0xFF4A6945));
        // Development
        mAreas.get(2).addField(new Field(12, "C++",0xFFAB9700));
        mAreas.get(2).addField(new Field(13, "Java",0xFF7D721E));
        mAreas.get(2).addField(new Field(14, "Python",0xFF453E08));
        // Machine Learning
        mAreas.get(3).addField(new Field(15, "General Intelligence",0xFF804121));
        mAreas.get(3).addField(new Field(16, "AI Cars",0xFF4D220D));
        mAreas.get(3).addField(new Field(17, "Robotics",0xFF734B37));
        mAreas.get(3).addField(new Field(18, "Virtual Reality",0xFF612000));
        // Data & Information
        mAreas.get(4).addField(new Field(19, "Social Media",0xFF8B1D8F));
        mAreas.get(4).addField(new Field(20, "Research Labs",0xFF8F0081));
        mAreas.get(4).addField(new Field(21, "Surveyors",0xFFC963CF));

        addConnectedFields();

        mTouchedArea[0] = mAreas.get(0); // null results in crash when displaying getName() text
        mTouchedArea[1] = mAreas.get(0); // null results in crash when displaying getName() text
    }

    private void addConnectedFields()
    {
        //Blank method to add list of fields connected to each field
        ArrayList<Field> connectToIntPro = new ArrayList<Field>(1);
        connectToIntPro.add(mAreas.get(0).getField(1));

        ArrayList<Field> connectToPhoneCarr = new ArrayList<Field>(4);
        connectToPhoneCarr.add(mAreas.get(0).getField(0));
        connectToPhoneCarr.add(mAreas.get(0).getField(2));
        connectToPhoneCarr.add(mAreas.get(0).getField(3));
        connectToPhoneCarr.add(mAreas.get(0).getField(4));

        ArrayList<Field> connectToVoIP = new ArrayList<Field>(4);
        connectToVoIP.add(mAreas.get(0).getField(0));
        connectToVoIP.add(mAreas.get(0).getField(3));
        connectToVoIP.add(mAreas.get(0).getField(4));
        connectToVoIP.add(mAreas.get(1).getField(2));

        ArrayList<Field> connectToRadio = new ArrayList<Field>(2);
        connectToRadio.add(mAreas.get(0).getField(2));
        connectToRadio.add(mAreas.get(0).getField(4));

        ArrayList<Field> connectToTeleg = new ArrayList<Field>(4);
        connectToTeleg.add(mAreas.get(0).getField(1));
        connectToTeleg.add(mAreas.get(0).getField(2));
        connectToTeleg.add(mAreas.get(0).getField(3));
        connectToTeleg.add(mAreas.get(2).getField(0));

        ArrayList<Field> connectToCybSec = new ArrayList<Field>(2);
        connectToCybSec.add(mAreas.get(1).getField(1));
        connectToCybSec.add(mAreas.get(3).getField(0));

        ArrayList<Field> connectToCCTV = new ArrayList<Field>(5);
        connectToCCTV.add(mAreas.get(1).getField(0));
        connectToCCTV.add(mAreas.get(1).getField(2));
        connectToCCTV.add(mAreas.get(1).getField(3));
        connectToCCTV.add(mAreas.get(1).getField(5));
        connectToCCTV.add(mAreas.get(3).getField(1));

        ArrayList<Field> connectToAudit = new ArrayList<Field>(4);
        connectToAudit.add(mAreas.get(0).getField(2));
        connectToAudit.add(mAreas.get(1).getField(1));
        connectToAudit.add(mAreas.get(1).getField(3));
        connectToAudit.add(mAreas.get(1).getField(4));

        ArrayList<Field> connectToTFA = new ArrayList<Field>(4);
        connectToTFA.add(mAreas.get(1).getField(1));
        connectToTFA.add(mAreas.get(1).getField(2));
        connectToTFA.add(mAreas.get(1).getField(4));
        connectToTFA.add(mAreas.get(1).getField(5));

        ArrayList<Field> connectToFW = new ArrayList<Field>(3);
        connectToFW.add(mAreas.get(1).getField(2));
        connectToFW.add(mAreas.get(1).getField(3));
        connectToFW.add(mAreas.get(1).getField(5));

        ArrayList<Field> connectToAV = new ArrayList<Field>(5);
        connectToAV.add(mAreas.get(1).getField(1));
        connectToAV.add(mAreas.get(1).getField(3));
        connectToAV.add(mAreas.get(1).getField(4));
        connectToAV.add(mAreas.get(4).getField(0));
        connectToAV.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToC = new ArrayList<Field>(3);
        connectToC.add(mAreas.get(0).getField(4));
        connectToC.add(mAreas.get(2).getField(1));
        connectToC.add(mAreas.get(2).getField(2));

        ArrayList<Field> connectToJava = new ArrayList<Field>(3);
        connectToJava.add(mAreas.get(2).getField(0));
        connectToJava.add(mAreas.get(2).getField(2));
        connectToJava.add(mAreas.get(3).getField(0));

        ArrayList<Field> connectToPython = new ArrayList<Field>(3);
        connectToPython.add(mAreas.get(2).getField(0));
        connectToPython.add(mAreas.get(2).getField(1));

        ArrayList<Field> connectToGI = new ArrayList<Field>(4);
        connectToGI.add(mAreas.get(1).getField(0));
        connectToGI.add(mAreas.get(2).getField(1));
        connectToGI.add(mAreas.get(3).getField(1));
        connectToGI.add(mAreas.get(3).getField(2));

        ArrayList<Field> connectToAICars = new ArrayList<Field>(4);
        connectToAICars.add(mAreas.get(1).getField(1));
        connectToAICars.add(mAreas.get(3).getField(0));
        connectToAICars.add(mAreas.get(3).getField(2));
        connectToAICars.add(mAreas.get(3).getField(3));

        ArrayList<Field> connectToRobot = new ArrayList<Field>(3);
        connectToRobot.add(mAreas.get(3).getField(0));
        connectToRobot.add(mAreas.get(3).getField(1));
        connectToRobot.add(mAreas.get(3).getField(3));

        ArrayList<Field> connectToVR = new ArrayList<Field>(2);
        connectToVR.add(mAreas.get(3).getField(1));
        connectToVR.add(mAreas.get(3).getField(2));

        ArrayList<Field> connectToSM = new ArrayList<Field>(2);
        connectToSM.add(mAreas.get(4).getField(1));
        connectToSM.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToRL = new ArrayList<Field>(3);
        connectToRL.add(mAreas.get(1).getField(5));
        connectToRL.add(mAreas.get(4).getField(0));
        connectToRL.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToSurvey = new ArrayList<Field>(3);
        connectToSurvey.add(mAreas.get(1).getField(5));
        connectToSurvey.add(mAreas.get(4).getField(0));
        connectToSurvey.add(mAreas.get(4).getField(1));
    }

    private void createPlayers() {
        // Generate the players and add colour
        mPlayers.add(new Player("Microsoft", Color.BLACK));
        mPlayers.add(new Player("Google", Color.GREEN));
        mPlayers.add(new Player("Apple", Color.RED));
        assignFields();
    }

    private void assignFields()
    {
        ArrayList<Field> allFields = new ArrayList<>();
        for (int i = 0; i < mAreas.size(); i++)
        {
            for (int j = 0; j < mAreas.get(i).fields.size(); i++)
            {
                allFields.add(mAreas.get(i).fields.get(j));
            }
        }

        Random randomNumber = new Random();
        int random;
        for(int i = 0; i < allFields.size(); i++){
            random = randomNumber.nextInt(allFields.size() - 1);
            if(allFields.get(random).checkAssigned())
            {
                allFields.get(random).setPlayer(mPlayers.get(CurrentPlayerNum));
                endTurn(false);
            }
        }

        for(Field field : allFields)
        {
            for(Area area : mAreas)
            {
                for(Field areaField : area.fields)
                {
                    if(field.getFNum() == areaField.getFNum())
                    {
                        areaField.setPlayer(field.getFPlayer());
                    }
                }
            }
        }
    }

    // Kinda obsolete code since fields got revamped...
    // Keep it for now? Maybe could be useful for layered images
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
                // The RiskRulesScreenBackground image colours match mAreas colours)
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

    private Field getFieldClicked() {
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
                clickedColour = colour; // debug info

                // Detects pixel colour and compares to the list of areas
                // The RiskRulesScreenBackground image colours match mAreas colours)
                for(int i = 0; i < mAreas.size(); i++) {
                    for(int x = 0; x < mAreas.get(i).getFieldSize(); x++) {
                        if (colour == mAreas.get(i).getField(x).getColour()) {
                            mFieldTouched =  mAreas.get(i).getField(x);
                            return mAreas.get(i).getField(x);
                        }
                    }
                }
            }
        }
        // Be careful, returning null can cause crashes
        return null;
    }

    private void beginBattle(Field att, Field def){

        Battle battle = new Battle(att, def);

        if(battle.canBattle())
            mGame.getScreenManager().addScreen(
                    new DiceRollScreen(mGame, battle, att.getFPlayer(), def.getFPlayer()));

        if(battle.attackersWin())
            def.hostileTakeOver(att.getFPlayer(), att.getFNumOfTeams());
    }

    private void firstTurn(){
        Random Rand = new Random();
        CurrentPlayerNum = Rand.nextInt(3);
    }

    private void AllocateTeams(int numOfTeams){
       for(int i = 0; i < 3; i++) {
           IncrementTeams(false);
       }
       while(numOfTeams > 0) {
           AllocateTeams(--numOfTeams);
       }
    }

    private void endTurn(boolean bool)
    {
        CurrentPlayerNum++;
        if(CurrentPlayerNum > 2) CurrentPlayerNum = 0;
    }

    private void endTurn()
    {
        if(SuccessfulAttack) {mPlayers.get(CurrentPlayerNum).incrementRiskCards();}
        SuccessfulAttack = false;
        CurrentPlayerNum++;
        if(CurrentPlayerNum > MAX_PLAYERS) CurrentPlayerNum = 0;
        beginTurn();
    }

    private void beginTurn()
    {
        ArrayList<Field> PlayerFieldsAtTurnStart = findPlayerFields(CurrentPlayerNum);
        int numOfTeamsAllocated = (PlayerFieldsAtTurnStart.size()/2) + riskCardCalc() + areaControlledCalc();
        for(int i = 0; i < numOfTeamsAllocated; i++) {
            IncrementTeams();
        }
    }

    private void IncrementTeams()
    {
        state = ALLOCATE;
    }

    private void IncrementTeams(boolean bool)
    {
        state = ALLOCATE;
        endTurn(bool);
    }

    private int riskCardCalc()
    {
        int TeamsForCards = 0;
        int NumOfCards = mPlayers.get(CurrentPlayerNum).getNumOfRiskCards();
        if (NumOfCards > 3) {
            TeamsForCards += 6;
            mPlayers.get(CurrentPlayerNum).useRiskCards();
        }
        return TeamsForCards;
    }

    private int areaControlledCalc()
    {
        boolean areaOwned = true;
        int teamsEarned = 0;
        for (int i = 0; i < mAreas.size(); i++)
        {
            for (int j = 0; j < mAreas.get(i).fields.size(); i++)
            {
                if(mAreas.get(i).fields.get(j).getFPlayer() != mPlayers.get(CurrentPlayerNum))
                {
                    areaOwned = false;
                }
            }
            if(areaOwned) teamsEarned =+ mAreas.get(i).getValue();
            areaOwned = true;
        }
        return teamsEarned;
    }


    private ArrayList<Field> findPlayerFields(int PlayerNum)
    {
        ArrayList<Field> TempList = new ArrayList<>();
        for(int i = 0; i < mAreas.size(); i++)
        {
            for(int j = 0; j < mAreas.get(i).fields.size(); i++)
            {
                if(mAreas.get(i).fields.get(j).getFPlayer() == mPlayers.get(PlayerNum))
                {
                    TempList.add(mAreas.get(i).fields.get(j));
                }
            }
        }
        return TempList;
    }

    private int initialTeamsCalc(){
        return (60 / MAX_PLAYERS);
    }

}
