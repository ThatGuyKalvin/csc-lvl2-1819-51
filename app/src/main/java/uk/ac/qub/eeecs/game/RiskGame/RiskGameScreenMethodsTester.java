package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.world.GameObject;

public class RiskGameScreenMethodsTester {
    private final int ATTACK_NULL = 0;
    private final int ATTACK_PICK = 1;
    private final int ATTACK_PICK_AGAIN = 2;
    private final int ATTACK_BATTLING = 3;
    private final int ALLOCATE = 4;
    private final int INITIAL_ALLOCATE = 5;
    private String attackStr = "State: Not battling.";
    private PushButton mAttackButton;
    private Bitmap mRiskGameScreenBackground;
    private Bitmap mRiskMapAreas;
    private GameObject mRiskMap;
    // Used to store the last touched area
    private Area[] mTouchedArea = new Area[2];
    public Field[] mFieldsAttacking = new Field[2];
    public Field mFieldTouched;
    private int state = ATTACK_NULL;
    private int clickedColour = 0;

    // ArrayList for Areas and Players
    private final int MAX_AREAS = 5;
    private final int MAX_PLAYERS = 3;
    private ArrayList<Player> mPlayers = new ArrayList<>(MAX_PLAYERS);
    public ArrayList<Area> mAreas = new ArrayList<>(MAX_AREAS);
    private int CurrentPlayerNum = 0;
    private int teamsToAllocate = 0;
    private boolean SuccessfulAttack = false;
    private boolean allocated = false;
    private Paint textPaint = new Paint();

    public boolean fieldListReturned = false;
    public boolean firstListHasPlayersAssigned = false;
    public RiskGameScreenMethodsTester(int choice)
    {
        switch(choice)
        {
            case 1:
                createAreas();
                break;
            case 2:
                createAreas();
                createPlayers();
                break;
            case 3:
                createAreas();
                createPlayers();
                assignFields();
                break;
            case 4:
                createAreas();
                createPlayers();
                assignFields();
                firstTurn();
                break;
            case 5:
                createAreas();
                createPlayers();
                assignFields();
                firstTurn();
                AllocateTeams(60);
                break;
            case 6:
                createAreas();
                createPlayers();
                assignFields();
                firstTurn();
                AllocateTeams(60);
                beginTurn();
                break;
            case 7:
            default: break;
        }
    }

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

    }

    private void addConnectedFields()
    {
        //Blank method to add list of fields connected to each field
        ArrayList<Field> connectToIntPro = new ArrayList<>(1);
        connectToIntPro.add(mAreas.get(0).getField(1));

        ArrayList<Field> connectToPhoneCarr = new ArrayList<>(4);
        connectToPhoneCarr.add(mAreas.get(0).getField(0));
        connectToPhoneCarr.add(mAreas.get(0).getField(2));
        connectToPhoneCarr.add(mAreas.get(0).getField(3));
        connectToPhoneCarr.add(mAreas.get(0).getField(4));

        ArrayList<Field> connectToVoIP = new ArrayList<>(4);
        connectToVoIP.add(mAreas.get(0).getField(0));
        connectToVoIP.add(mAreas.get(0).getField(3));
        connectToVoIP.add(mAreas.get(0).getField(4));
        connectToVoIP.add(mAreas.get(1).getField(2));

        ArrayList<Field> connectToRadio = new ArrayList<>(2);
        connectToRadio.add(mAreas.get(0).getField(2));
        connectToRadio.add(mAreas.get(0).getField(4));

        ArrayList<Field> connectToTeleg = new ArrayList<>(4);
        connectToTeleg.add(mAreas.get(0).getField(1));
        connectToTeleg.add(mAreas.get(0).getField(2));
        connectToTeleg.add(mAreas.get(0).getField(3));
        connectToTeleg.add(mAreas.get(2).getField(0));

        ArrayList<Field> connectToCybSec = new ArrayList<>(2);
        connectToCybSec.add(mAreas.get(1).getField(1));
        connectToCybSec.add(mAreas.get(3).getField(0));

        ArrayList<Field> connectToCCTV = new ArrayList<>(5);
        connectToCCTV.add(mAreas.get(1).getField(0));
        connectToCCTV.add(mAreas.get(1).getField(2));
        connectToCCTV.add(mAreas.get(1).getField(3));
        connectToCCTV.add(mAreas.get(1).getField(5));
        connectToCCTV.add(mAreas.get(3).getField(1));

        ArrayList<Field> connectToAudit = new ArrayList<>(4);
        connectToAudit.add(mAreas.get(0).getField(2));
        connectToAudit.add(mAreas.get(1).getField(1));
        connectToAudit.add(mAreas.get(1).getField(3));
        connectToAudit.add(mAreas.get(1).getField(4));

        ArrayList<Field> connectToTFA = new ArrayList<>(4);
        connectToTFA.add(mAreas.get(1).getField(1));
        connectToTFA.add(mAreas.get(1).getField(2));
        connectToTFA.add(mAreas.get(1).getField(4));
        connectToTFA.add(mAreas.get(1).getField(5));

        ArrayList<Field> connectToFW = new ArrayList<>(3);
        connectToFW.add(mAreas.get(1).getField(2));
        connectToFW.add(mAreas.get(1).getField(3));
        connectToFW.add(mAreas.get(1).getField(5));

        ArrayList<Field> connectToAV = new ArrayList<>(5);
        connectToAV.add(mAreas.get(1).getField(1));
        connectToAV.add(mAreas.get(1).getField(3));
        connectToAV.add(mAreas.get(1).getField(4));
        connectToAV.add(mAreas.get(4).getField(0));
        connectToAV.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToC = new ArrayList<>(3);
        connectToC.add(mAreas.get(0).getField(4));
        connectToC.add(mAreas.get(2).getField(1));
        connectToC.add(mAreas.get(2).getField(2));

        ArrayList<Field> connectToJava = new ArrayList<>(3);
        connectToJava.add(mAreas.get(2).getField(0));
        connectToJava.add(mAreas.get(2).getField(2));
        connectToJava.add(mAreas.get(3).getField(0));

        ArrayList<Field> connectToPython = new ArrayList<>(3);
        connectToPython.add(mAreas.get(2).getField(0));
        connectToPython.add(mAreas.get(2).getField(1));

        ArrayList<Field> connectToGI = new ArrayList<>(4);
        connectToGI.add(mAreas.get(1).getField(0));
        connectToGI.add(mAreas.get(2).getField(1));
        connectToGI.add(mAreas.get(3).getField(1));
        connectToGI.add(mAreas.get(3).getField(2));

        ArrayList<Field> connectToAICars = new ArrayList<>(4);
        connectToAICars.add(mAreas.get(1).getField(1));
        connectToAICars.add(mAreas.get(3).getField(0));
        connectToAICars.add(mAreas.get(3).getField(2));
        connectToAICars.add(mAreas.get(3).getField(3));

        ArrayList<Field> connectToRobot = new ArrayList<>(3);
        connectToRobot.add(mAreas.get(3).getField(0));
        connectToRobot.add(mAreas.get(3).getField(1));
        connectToRobot.add(mAreas.get(3).getField(3));

        ArrayList<Field> connectToVR = new ArrayList<>(2);
        connectToVR.add(mAreas.get(3).getField(1));
        connectToVR.add(mAreas.get(3).getField(2));

        ArrayList<Field> connectToSM = new ArrayList<>(2);
        connectToSM.add(mAreas.get(4).getField(1));
        connectToSM.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToRL = new ArrayList<>(3);
        connectToRL.add(mAreas.get(1).getField(5));
        connectToRL.add(mAreas.get(4).getField(0));
        connectToRL.add(mAreas.get(4).getField(2));

        ArrayList<Field> connectToSurvey = new ArrayList<>(3);
        connectToSurvey.add(mAreas.get(1).getField(5));
        connectToSurvey.add(mAreas.get(4).getField(0));
        connectToSurvey.add(mAreas.get(4).getField(1));



        mAreas.get(0).getField(0).addConnectedFields(connectToIntPro);
        mAreas.get(0).getField(1).addConnectedFields(connectToPhoneCarr);
        mAreas.get(0).getField(2).addConnectedFields(connectToVoIP);
        mAreas.get(0).getField(3).addConnectedFields(connectToRadio);
        mAreas.get(0).getField(4).addConnectedFields(connectToTeleg);

        mAreas.get(1).getField(0).addConnectedFields(connectToCybSec);
        mAreas.get(1).getField(1).addConnectedFields(connectToCCTV);
        mAreas.get(1).getField(2).addConnectedFields(connectToAudit);
        mAreas.get(1).getField(3).addConnectedFields(connectToTFA);
        mAreas.get(1).getField(4).addConnectedFields(connectToFW);
        mAreas.get(1).getField(5).addConnectedFields(connectToAV);

        mAreas.get(2).getField(0).addConnectedFields(connectToC);
        mAreas.get(2).getField(1).addConnectedFields(connectToJava);
        mAreas.get(2).getField(2).addConnectedFields(connectToPython);

        mAreas.get(3).getField(0).addConnectedFields(connectToGI);
        mAreas.get(3).getField(1).addConnectedFields(connectToAICars);
        mAreas.get(3).getField(2).addConnectedFields(connectToRobot);
        mAreas.get(3).getField(3).addConnectedFields(connectToVR);

        mAreas.get(4).getField(0).addConnectedFields(connectToSM);
        mAreas.get(4).getField(1).addConnectedFields(connectToRL);
        mAreas.get(4).getField(2).addConnectedFields(connectToSurvey);

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
        for (int i = 0; i <= 4; i++)
        {
            for (int j = 0; j < mAreas.get(i).getFieldSize(); j++)
            {
                allFields.add(mAreas.get(i).getField(j));
            }
        }

        Random randomNumber = new Random();
        int random;
        do{
            random = randomNumber.nextInt(allFields.size());
            if(!allFields.get(random).checkAssigned())
            {
                allFields.get(random).setPlayer(mPlayers.get(CurrentPlayerNum));
                endTurn(false);
            }
        }while(!FieldsAllOwned(allFields));

        for(Area area : mAreas)
        {
            for(Field field : allFields)
            {
                for(Field areaField : area.aFields)
                {
                    if(field.getNum() == areaField.getNum())
                    {
                        areaField.setPlayer(field.getPlayer());
                    }
                }
            }
        }
    }

    private boolean FieldsAllOwned(ArrayList<Field> fields)
    {
        for(Field f : fields)
        {
            if(!f.checkAssigned()) return false;
        }
        return true;
    }

    private void firstTurn(){
        Random Rand = new Random();
        CurrentPlayerNum = Rand.nextInt(3);
    }

    private void AllocateTeams(int numOfTeams){
        teamsToAllocate = numOfTeams;
        state = INITIAL_ALLOCATE;
    }

    private void AllocateTeams(int numOfTeams, int playerNum){
        teamsToAllocate = numOfTeams;
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
        if(CurrentPlayerNum > 2) CurrentPlayerNum = 0;
        beginTurn();
    }

    private void beginTurn()
    {
        ArrayList<Field> PlayerFieldsAtTurnStart = findPlayerFields(CurrentPlayerNum);
        int numOfTeamsAllocated = (PlayerFieldsAtTurnStart.size()/2) + riskCardCalc() + areaControlledCalc();
        AllocateTeams(numOfTeamsAllocated, CurrentPlayerNum);
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
            for (int j = 0; j < mAreas.get(i).aFields.size(); i++)
            {
                if(mAreas.get(i).aFields.get(j).getPlayer() != mPlayers.get(CurrentPlayerNum))
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
            for(int j = 0; j < mAreas.get(i).getFieldSize(); j++)
            {
                if(mAreas.get(i).getField(j).getPlayer() == mPlayers.get(PlayerNum))
                {
                    TempList.add(mAreas.get(i).getField(j));
                }
            }
        }
        return TempList;
    }

    private Field findOriginalField(Field tempField)
    {
        for(Area area : mAreas){
            for(int i = 0; i < area.getFieldSize(); i++){
                if(tempField == area.getField(i)){
                    return area.getField(i);
                }
            }
        }
        return null;
    }
}