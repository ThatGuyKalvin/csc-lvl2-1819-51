package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.util.BoundingBox;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.util.ViewportHelper;
import uk.ac.qub.eeecs.gage.world.GameObject;

public class RiskGameScreenMethodsTester {
    public final int ATTACK_NULL = 0;
    public final int INITIAL_ALLOCATE = 5;
    public int state = ATTACK_NULL;
    public String attackStr = "State: Not battling.";

    // ArrayList for Areas and Players
    public final int MAX_AREAS = 5;
    public final int MAX_PLAYERS = 3;
    public ArrayList<Player> mPlayers = new ArrayList<>(MAX_PLAYERS);
    public ArrayList<Area> mAreas = new ArrayList<>(MAX_AREAS);
    public int CurrentPlayerNum = 0;
    public int teamsToAllocate = 6;
    public boolean SuccessfulAttack = false;
    public boolean allocated = false;

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
            case 7 :
            default : createAreas();
            break;
        }
    }

    public void createAreas() {

        // Generate the areas and add colour
        mAreas.add(new Area("Telecommunications", 0xFFeb1c23, 6));
        mAreas.add(new Area("Security", 0xFF0ecf42, 7));
        mAreas.add(new Area("Development", 0xFFffe51c, 4));
        mAreas.add(new Area("Machine Learning", 0xFFb87756,5));
        mAreas.add(new Area("Data & Information", 0xFFb63eb8,4));
        // Generate the fields for the areas

        // Telecommunications
        mAreas.get(0).addField(new Field(1,"Internet Provider",0xFFDE7879));
        mAreas.get(0).getField(0).addColourToArray(0xff012b5d);
        mAreas.get(0).getField(0).addColourToArray(0xffea1b25);
        mAreas.get(0).getField(0).addColourToArray(0xffde7a7a);
        mAreas.get(0).getField(0).addColourToArray(0xffe07877);
        mAreas.get(0).getField(0).addColourToArray(0xffdd797b);
        mAreas.get(0).getField(0).addColourToArray(0xffdf787b);
        mAreas.get(0).getField(0).addColourToArray(0xffde7a78);
        mAreas.get(0).getField(0).addColourToArray(0xffdd7b78);
        mAreas.get(0).getField(0).addColourToArray(0xffdd7b7a);
        mAreas.get(0).getField(0).addColourToArray(0xffe07879);
        mAreas.get(0).getField(0).addColourToArray(0xffde7a7c);
        mAreas.get(0).addField(new Field(2,"Phone Carrier",0xFF9C0003));
        mAreas.get(0).getField(1).addColourToArray(0xff022d60);
        mAreas.get(0).getField(1).addColourToArray(0xff0a2d55);
        mAreas.get(0).getField(1).addColourToArray(0xff042a51);
        mAreas.get(0).getField(1).addColourToArray(0xff062951);
        mAreas.get(0).getField(1).addColourToArray(0xff032950);
        mAreas.get(0).getField(1).addColourToArray(0xff022751);
        mAreas.get(0).getField(1).addColourToArray(0xff012b53);
        mAreas.get(0).getField(1).addColourToArray(0xff052c55);
        mAreas.get(0).getField(1).addColourToArray(0xff042b54);
        mAreas.get(0).getField(1).addColourToArray(0xff052f59);
        mAreas.get(0).getField(1).addColourToArray(0xff032d55);
        mAreas.get(0).getField(1).addColourToArray(0xff042b52);
        mAreas.get(0).getField(1).addColourToArray(0xff062c53);
        mAreas.get(0).getField(1).addColourToArray(0xff032852);
        mAreas.get(0).getField(1).addColourToArray(0xff8a1d32);
        mAreas.get(0).getField(1).addColourToArray(0xffe91c23);
        mAreas.get(0).getField(1).addColourToArray(0xffeb1c22);
        mAreas.get(0).getField(1).addColourToArray(0xff073566);
        mAreas.get(0).getField(1).addColourToArray(0xff022c5c);
        mAreas.get(0).getField(1).addColourToArray(0xff012c5f);
        mAreas.get(0).getField(1).addColourToArray(0xff00295b);
        mAreas.get(0).getField(1).addColourToArray(0xff002a5c);

        mAreas.get(0).addField(new Field(3,"VoIP",0xFFF0ADAD));
        mAreas.get(0).getField(2).addColourToArray(0xff9e0004);
        mAreas.get(0).getField(2).addColourToArray(0xff9c0204);
        mAreas.get(0).getField(2).addColourToArray(0xffee1c25);
        mAreas.get(0).getField(2).addColourToArray(0xffed1b24);
        mAreas.get(0).getField(2).addColourToArray(0xffeb363f);
        mAreas.get(0).getField(2).addColourToArray(0xfff4aeb0);
        mAreas.get(0).getField(2).addColourToArray(0xfff2aeaf);
        mAreas.get(0).getField(2).addColourToArray(0xfff4aeae);
        mAreas.get(0).getField(2).addColourToArray(0xfff0b0ae);
        mAreas.get(0).getField(2).addColourToArray(0xff9e0003);
        mAreas.get(0).getField(2).addColourToArray(0xffa00007);
        mAreas.get(0).getField(2).addColourToArray(0xffa00004);
        mAreas.get(0).getField(2).addColourToArray(0xff9f0005);
        mAreas.get(0).getField(2).addColourToArray(0xff9f0004);
        mAreas.get(0).getField(2).addColourToArray(0xffea1d24);
        mAreas.get(0).getField(2).addColourToArray(0xffea1c20);
        mAreas.get(0).getField(2).addColourToArray(0xffec1a23);
        mAreas.get(0).getField(2).addColourToArray(0xffee1b24);


        mAreas.get(0).addField(new Field(4,"Radio",0xFFB84B4B));
        mAreas.get(0).getField(3).addColourToArray(0xffe91c21);
        mAreas.get(0).getField(3).addColourToArray(0xffea1d24);
        mAreas.get(0).getField(3).addColourToArray(0xff880608);
        mAreas.get(0).getField(3).addColourToArray(0xff5f0001);
        mAreas.get(0).getField(3).addColourToArray(0xff5c0001);
        mAreas.get(0).getField(3).addColourToArray(0xff5f0000);
        mAreas.get(0).getField(3).addColourToArray(0xff600001);
        mAreas.get(0).getField(3).addColourToArray(0xffeb4142);
        mAreas.get(0).getField(3).addColourToArray(0xfff3adab);
        mAreas.get(0).getField(3).addColourToArray(0xfff2aeaf);
        mAreas.get(0).getField(3).addColourToArray(0xfff1adae);
        mAreas.get(0).getField(3).addColourToArray(0xfff3afb0);
        mAreas.get(0).getField(3).addColourToArray(0xfff4aeb0);
        mAreas.get(0).getField(3).addColourToArray(0xfff4aeb0);
        mAreas.get(0).getField(3).addColourToArray(0xfff3adad);
        mAreas.get(0).getField(3).addColourToArray(0xfff0aeaf);
        mAreas.get(0).getField(3).addColourToArray(0xfff1b0ac);
        mAreas.get(0).getField(3).addColourToArray(0xfff3afae);
        mAreas.get(0).getField(3).addColourToArray(0xfff09a9d);
        mAreas.get(0).getField(3).addColourToArray(0xffee2c34);
        mAreas.get(0).getField(3).addColourToArray(0xffb94c49);
        mAreas.get(0).getField(3).addColourToArray(0xffb54b4b);
        mAreas.get(0).getField(3).addColourToArray(0xffb84b4e);
        mAreas.get(0).getField(3).addColourToArray(0xffba494b);
        mAreas.get(0).getField(3).addColourToArray(0xffb54c49);
        mAreas.get(0).getField(3).addColourToArray(0xffbb4a4c);
        mAreas.get(0).getField(3).addColourToArray(0xffb94b4c);
        mAreas.get(0).getField(3).addColourToArray(0xffb74948);
        mAreas.get(0).getField(3).addColourToArray(0xffb84c4a);
        mAreas.get(0).getField(3).addColourToArray(0xffb74b4b);
        mAreas.get(0).getField(3).addColourToArray(0xffba4c4b);
        mAreas.get(0).getField(3).addColourToArray(0xffb84b48);
        mAreas.get(0).getField(3).addColourToArray(0xffeb1d21);
        mAreas.get(0).getField(3).addColourToArray(0xffbf4142);
        mAreas.get(0).getField(3).addColourToArray(0xffed1b24);
        mAreas.get(0).getField(3).addColourToArray(0xffee3f44);
        mAreas.get(0).getField(3).addColourToArray(0xffe81d21);
        mAreas.get(0).getField(3).addColourToArray(0xffed4046);

        mAreas.get(0).addField(new Field(5, "Telegraphy",0xFF5C0000));
        mAreas.get(0).getField(4).addColourToArray(0xffdd797b);
        mAreas.get(0).getField(4).addColourToArray(0xff002859);
        mAreas.get(0).getField(4).addColourToArray(0xff9f0005);
        mAreas.get(0).getField(4).addColourToArray(0xff9e0003);
        mAreas.get(0).getField(4).addColourToArray(0xff9e0006);
        mAreas.get(0).getField(4).addColourToArray(0xff9f0007);
        mAreas.get(0).getField(4).addColourToArray(0xffac070d);
        mAreas.get(0).getField(4).addColourToArray(0xff012d5c);
        mAreas.get(0).getField(4).addColourToArray(0xff9a0104);
        mAreas.get(0).getField(4).addColourToArray(0xff9c0004);
        mAreas.get(0).getField(4).addColourToArray(0xff9d0105);
        mAreas.get(0).getField(4).addColourToArray(0xff9e0004);
        mAreas.get(0).getField(4).addColourToArray(0xff9c0107);
        mAreas.get(0).getField(4).addColourToArray(0xffea1b23);


        // Security
        mAreas.get(1).addField(new Field(6, "Cyber Security",0xFF007820));
        mAreas.get(1).getField(0).addColourToArray(0xff00285e);
        mAreas.get(1).getField(0).addColourToArray(0xff00781f);
        mAreas.get(1).getField(0).addColourToArray(0xff00791f);
        mAreas.get(1).getField(0).addColourToArray(0xff007a20);
        mAreas.get(1).getField(0).addColourToArray(0xff007821);
        mAreas.get(1).getField(0).addColourToArray(0xff01771f);
        mAreas.get(1).getField(0).addColourToArray(0xff007821);
        mAreas.get(1).addField(new Field(7, "CCTV",0xFF4AAB3F));
        mAreas.get(1).getField(1).addColourToArray(0xff00295d);
        mAreas.get(1).getField(1).addColourToArray(0xff48ab3e);
        mAreas.get(1).getField(1).addColourToArray(0xff0ece45);
        mAreas.get(1).getField(1).addColourToArray(0xff0ecf42);
        mAreas.get(1).getField(1).addColourToArray(0xff49ac3f);
        mAreas.get(1).addField(new Field(8, "Audit Trails",0xFF24401F));
        mAreas.get(1).getField(2).addColourToArray(0xff65915e);
        mAreas.get(1).getField(2).addColourToArray(0xff19c744);
        mAreas.get(1).getField(2).addColourToArray(0xff0bce41);
        mAreas.get(1).getField(2).addColourToArray(0xff0ecf42);
        mAreas.get(1).getField(2).addColourToArray(0xff39af4f);
        mAreas.get(1).getField(2).addColourToArray(0xff244220);
        mAreas.get(1).getField(2).addColourToArray(0xff254321);
        mAreas.get(1).getField(2).addColourToArray(0xff25431f);
        mAreas.get(1).getField(2).addColourToArray(0xff0bcf3f);
        mAreas.get(1).getField(2).addColourToArray(0xff0ccf42);
        mAreas.get(1).addField(new Field(9, "TFA",0xFF7AC28C));
        mAreas.get(1).getField(3).addColourToArray(0xff012b5d);
        mAreas.get(1).getField(3).addColourToArray(0xff79c28d);
        mAreas.get(1).getField(3).addColourToArray(0xff79c28b);
        mAreas.get(1).getField(3).addColourToArray(0xff7ac38b);
        mAreas.get(1).getField(3).addColourToArray(0xff65915e);
        mAreas.get(1).getField(3).addColourToArray(0xff7ac38c);
        mAreas.get(1).addField(new Field(10, "Firewall",0xFF648F5D));
        mAreas.get(1).getField(4).addColourToArray(0xff002a5c);
        mAreas.get(1).getField(4).addColourToArray(0xff4b6b46);
        mAreas.get(1).getField(4).addColourToArray(0xff4c6a46);
        mAreas.get(1).getField(4).addColourToArray(0xff496b48);
        mAreas.get(1).getField(4).addColourToArray(0xff4d6b45);
        mAreas.get(1).getField(4).addColourToArray(0xff0fce40);
        mAreas.get(1).getField(4).addColourToArray(0xff0d3c66);
        mAreas.get(1).getField(4).addColourToArray(0xff0ccf42);
        mAreas.get(1).getField(4).addColourToArray(0xff284d7a);
        mAreas.get(1).getField(4).addColourToArray(0xff3c5569);
        mAreas.get(1).getField(4).addColourToArray(0xff194a72);
        mAreas.get(1).getField(4).addColourToArray(0xff0fce43);
        mAreas.get(1).addField(new Field(11, "AntiVirus",0xFF4A6945));

        mAreas.get(1).getField(5).addColourToArray(0xff073066);
        mAreas.get(1).getField(5).addColourToArray(0xff002a5c);
        mAreas.get(1).getField(5).addColourToArray(0xff10cf43);
        mAreas.get(1).getField(5).addColourToArray(0xff0ecf42);
        mAreas.get(1).getField(5).addColourToArray(0xff65915e);
        mAreas.get(1).getField(5).addColourToArray(0xff64905b);
        mAreas.get(1).getField(5).addColourToArray(0xff65915e);
        mAreas.get(1).getField(5).addColourToArray(0xff03386a);
        mAreas.get(1).getField(5).addColourToArray(0xff0ecf42);
        mAreas.get(1).getField(5).addColourToArray(0xff052649);
        mAreas.get(1).getField(5).addColourToArray(0xff12ce43);
        mAreas.get(1).getField(5).addColourToArray(0xff10cf41);
        mAreas.get(1).getField(5).addColourToArray(0xff64905d);
        mAreas.get(1).getField(5).addColourToArray(0xff0fd043);
        mAreas.get(1).getField(5).addColourToArray(0xff0b3e73);
        mAreas.get(1).getField(5).addColourToArray(0xff002858);
        mAreas.get(1).getField(5).addColourToArray(0xff00295d);
        mAreas.get(1).getField(5).addColourToArray(0xff0dd141);

        // Development
        mAreas.get(2).addField(new Field(12, "C++",0xFFAB9700));

        //top south america
        mAreas.get(2).getField(0).addColourToArray(0xff194479);
        mAreas.get(2).getField(0).addColourToArray(0xff012c5f);
        mAreas.get(2).getField(0).addColourToArray(0xff002459);
        mAreas.get(2).getField(0).addColourToArray(0xff012a60);
        mAreas.get(2).getField(0).addColourToArray(0xff022d64);
        mAreas.get(2).getField(0).addColourToArray(0xff032b5c);
        mAreas.get(2).getField(0).addColourToArray(0xff022a5d);
        mAreas.get(2).getField(0).addColourToArray(0xff00285b);
        mAreas.get(2).getField(0).addColourToArray(0xff093469);
        mAreas.get(2).getField(0).addColourToArray(0xff042f64);
        mAreas.get(2).getField(0).addColourToArray(0xff012a5e);
        mAreas.get(2).getField(0).addColourToArray(0xff00295d);
        mAreas.get(2).getField(0).addColourToArray(0xff00285e);
        mAreas.get(2).getField(0).addColourToArray(0xff062f65);
        mAreas.get(2).getField(0).addColourToArray(0xff022b5f);
        mAreas.get(2).getField(0).addColourToArray(0xff002b5e);
        mAreas.get(2).getField(0).addColourToArray(0Xff002c5d);
        mAreas.get(2).getField(0).addColourToArray(0xff002e60);
        mAreas.get(2).getField(0).addColourToArray(0xff003061);
        mAreas.get(2).getField(0).addColourToArray(0xff033663);
        mAreas.get(2).getField(0).addColourToArray(0xff083964);
        mAreas.get(2).getField(0).addColourToArray(0xff033663);
        mAreas.get(2).getField(0).addColourToArray(0xff003360);
        mAreas.get(2).getField(0).addColourToArray(0xff013463);
        mAreas.get(2).getField(0).addColourToArray(0xff003061);
        mAreas.get(2).getField(0).addColourToArray(0xff002e60);
        mAreas.get(2).getField(0).addColourToArray(0xff002d5f);
        mAreas.get(2).getField(0).addColourToArray(0xff013162);
        mAreas.get(2).getField(0).addColourToArray(0xff002d5f);
        mAreas.get(2).getField(0).addColourToArray(0xff012a5e);
        mAreas.get(2).getField(0).addColourToArray(0xff053367);
        mAreas.get(2).getField(0).addColourToArray(0xff002a5f);
        mAreas.get(2).getField(0).addColourToArray(0xff012a5e);
        mAreas.get(2).getField(0).addColourToArray(0xff00295d);


        // middle south america
        mAreas.get(2).addField(new Field(13, "Java",0xFF7D721E));

        mAreas.get(2).getField(1).addColourToArray(0xffad9800);
        mAreas.get(2).getField(1).addColourToArray(0xffac9900);
        mAreas.get(2).getField(1).addColourToArray(0xffbca507);
        mAreas.get(2).getField(1).addColourToArray(0xffffe31d);
        mAreas.get(2).getField(1).addColourToArray(0xfffee41b);
        mAreas.get(2).getField(1).addColourToArray(0xfffee71f);
        mAreas.get(2).getField(1).addColourToArray(0xffffe41e);
        mAreas.get(2).getField(1).addColourToArray(0xffffe51c);
        mAreas.get(2).getField(1).addColourToArray(0xffffe51a);
        mAreas.get(2).getField(1).addColourToArray(0xffffe61d);
        mAreas.get(2).getField(1).addColourToArray(0xff7d731e);
        mAreas.get(2).getField(1).addColourToArray(0xff7c721b);
        mAreas.get(2).getField(1).addColourToArray(0xff7e721c);
        mAreas.get(2).getField(1).addColourToArray(0xff7d711d);
        mAreas.get(2).getField(1).addColourToArray(0xff7d731e);
        mAreas.get(2).getField(1).addColourToArray(0xff8f8021);
        mAreas.get(2).getField(1).addColourToArray(0xffceba19);
        mAreas.get(2).getField(1).addColourToArray(0xffffe41c);
        mAreas.get(2).getField(1).addColourToArray(0xffffe51c);
        mAreas.get(2).getField(1).addColourToArray(0xffffe51e);
        mAreas.get(2).getField(1).addColourToArray(0xffffe61a);
        mAreas.get(2).getField(1).addColourToArray(0xffae9900);
        mAreas.get(2).getField(1).addColourToArray(0xffae9902);
        mAreas.get(2).getField(1).addColourToArray(0xffffe61a);
        mAreas.get(2).getField(1).addColourToArray(0xfffee41d);
        mAreas.get(2).getField(1).addColourToArray(0xfffee41b);
        mAreas.get(2).getField(1).addColourToArray(0xffbca507);
        mAreas.get(2).getField(1).addColourToArray(0xffae9b00);
        mAreas.get(2).getField(1).addColourToArray(0xffad9800);
        mAreas.get(2).getField(1).addColourToArray(0xff002961);




        //bottom south america
        mAreas.get(2).addField(new Field(14, "Python",0xFF453E08));

        mAreas.get(2).getField(2).addColourToArray(0xff022451);
        mAreas.get(2).getField(2).addColourToArray(0xff02254f);
        mAreas.get(2).getField(2).addColourToArray(0xff01244c);
        mAreas.get(2).getField(2).addColourToArray(0xff012350);
        mAreas.get(2).getField(2).addColourToArray(0xff01244e);
        mAreas.get(2).getField(2).addColourToArray(0xff02254f);
        mAreas.get(2).getField(2).addColourToArray(0xff03244f);
        mAreas.get(2).getField(2).addColourToArray(0xff032451);
        mAreas.get(2).getField(2).addColourToArray(0xff032650);
        mAreas.get(2).getField(2).addColourToArray(0xff02224b);
        mAreas.get(2).getField(2).addColourToArray(0xff052148);
        mAreas.get(2).getField(2).addColourToArray(0xff042047);
        mAreas.get(2).getField(2).addColourToArray(0xff042149);
        mAreas.get(2).getField(2).addColourToArray(0xff012148);
        mAreas.get(2).getField(2).addColourToArray(0xff03234a);
        mAreas.get(2).getField(2).addColourToArray(0xff022248);
        mAreas.get(2).getField(2).addColourToArray(0xff03234c);
        mAreas.get(2).getField(2).addColourToArray(0xff042244);
        mAreas.get(2).getField(2).addColourToArray(0xff022042);
        mAreas.get(2).getField(2).addColourToArray(0xff00295d);
        mAreas.get(2).getField(2).addColourToArray(0xff03264e);
        mAreas.get(2).getField(2).addColourToArray(0xff02234c);
        mAreas.get(2).getField(2).addColourToArray(0xff032e61);


        // Machine Learning
        mAreas.get(3).addField(new Field(15, "General Intelligence",0xFF804121));
        mAreas.get(3).getField(0).addColourToArray(0xff804120);
        mAreas.get(3).getField(0).addColourToArray(0xffb97755);
        mAreas.get(3).getField(0).addColourToArray(0xffb87653);
        mAreas.get(3).getField(0).addColourToArray(0xff914923);
        mAreas.get(3).getField(0).addColourToArray(0xff814221);
        mAreas.get(3).getField(0).addColourToArray(0xff8f4927);
        mAreas.get(3).getField(0).addColourToArray(0xffb77656);
        mAreas.get(3).getField(0).addColourToArray(0xffb87854);
        mAreas.get(3).getField(0).addColourToArray(0xffb87855);
        mAreas.get(3).getField(0).addColourToArray(0xff173d54);
        mAreas.get(3).getField(0).addColourToArray(0xff0e365a);
        mAreas.get(3).getField(0).addColourToArray(0xff143e66);
        mAreas.get(3).getField(0).addColourToArray(0xff485367);
        mAreas.get(3).getField(0).addColourToArray(0xff7f3f23);
        mAreas.get(3).getField(0).addColourToArray(0xff814223);

        mAreas.get(3).addField(new Field(16, "AI Cars",0xFF4D220D));
        mAreas.get(3).getField(1).addColourToArray(0xff00285c);
        mAreas.get(3).getField(1).addColourToArray(0xff632101);
        mAreas.get(3).getField(1).addColourToArray(0xff632100);
        mAreas.get(3).getField(1).addColourToArray(0xff642200);
        mAreas.get(3).getField(1).addColourToArray(0xff622000);
        mAreas.get(3).getField(1).addColourToArray(0xff632101);
        mAreas.get(3).getField(1).addColourToArray(0xff754c38);

        mAreas.get(3).addField(new Field(17, "Robotics",0xFF734B37));
        mAreas.get(3).addField(new Field(18, "Virtual Reality",0xFF612000));
        mAreas.get(3).getField(3).addColourToArray(0xff642200);
        mAreas.get(3).getField(3).addColourToArray(0xff632100);
        mAreas.get(3).getField(3).addColourToArray(0xff622000);
        mAreas.get(3).getField(3).addColourToArray(0xff632101);

        // Data & Information
        mAreas.get(4).addField(new Field(19, "Social Media",0xFF8B1D8F));
        mAreas.get(4).getField(0).addColourToArray(0xff002955);
        mAreas.get(4).getField(0).addColourToArray(0xff022855);
        mAreas.get(4).getField(0).addColourToArray(0xff436996);
        mAreas.get(4).getField(0).addColourToArray(0xff052d5e);
        mAreas.get(4).getField(0).addColourToArray(0xff042c5d);
        mAreas.get(4).getField(0).addColourToArray(0xff436996);
        mAreas.get(4).getField(0).addColourToArray(0xff0b2f5f);
        mAreas.get(4).getField(0).addColourToArray(0xff12355f);
        mAreas.get(4).getField(0).addColourToArray(0xff16345a);
        mAreas.get(4).getField(0).addColourToArray(0xff3c495a);
        mAreas.get(4).getField(0).addColourToArray(0xff616b77);
        mAreas.get(4).getField(0).addColourToArray(0xff304854);
        mAreas.get(4).getField(0).addColourToArray(0xff2f4155);
        mAreas.get(4).getField(0).addColourToArray(0xffcb5dcd);
        mAreas.get(4).getField(0).addColourToArray(0xffca63ce);
        mAreas.get(4).getField(0).addColourToArray(0xffcc62ce);
        mAreas.get(4).getField(0).addColourToArray(0xffcb64d1);
        mAreas.get(4).getField(0).addColourToArray(0xffb63eb9);
        mAreas.get(4).getField(0).addColourToArray(0xffc759ca);
        mAreas.get(4).getField(0).addColourToArray(0xff274054);
        mAreas.get(4).getField(0).addColourToArray(0xffb53eb6);
        mAreas.get(4).getField(0).addColourToArray(0xff9473a8);
        mAreas.get(4).getField(0).addColourToArray(0xff3a4750);
        mAreas.get(4).getField(0).addColourToArray(0xff59666f);
        mAreas.get(4).getField(0).addColourToArray(0xff4a5d6c);
        mAreas.get(4).getField(0).addColourToArray(0xff203b56);
        mAreas.get(4).getField(0).addColourToArray(0xff183756);
        mAreas.get(4).getField(0).addColourToArray(0xff002c59);
        mAreas.get(4).getField(0).addColourToArray(0xff002b5a);
        mAreas.get(4).getField(0).addColourToArray(0xff002b52);
        mAreas.get(4).getField(0).addColourToArray(0xff0b375e);
        mAreas.get(4).getField(0).addColourToArray(0xff00234d);
        mAreas.get(4).getField(0).addColourToArray(0xff00214e);
        mAreas.get(4).getField(0).addColourToArray(0xff224974);
        mAreas.get(4).getField(0).addColourToArray(0xff00204d);
        mAreas.get(4).getField(0).addColourToArray(0xff022a4e);
        mAreas.get(4).getField(0).addColourToArray(0xff032e58);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff012a58);
        mAreas.get(4).getField(0).addColourToArray(0xff002959);
        mAreas.get(4).getField(0).addColourToArray(0xff002a57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);
        mAreas.get(4).getField(0).addColourToArray(0xff022b57);



        mAreas.get(4).addField(new Field(20, "Research Labs",0xFF8F0081));
        mAreas.get(4).getField(1).addColourToArray(0xffca63d0);
        mAreas.get(4).getField(1).addColourToArray(0xffc962cd);
        mAreas.get(4).getField(1).addColourToArray(0xff8f0081);
        mAreas.get(4).getField(1).addColourToArray(0xffca63ce);
        mAreas.get(4).getField(1).addColourToArray(0xff920083);
        mAreas.get(4).getField(1).addColourToArray(0xff910083);
        mAreas.get(4).getField(1).addColourToArray(0xffb53fb9);
        mAreas.get(4).getField(1).addColourToArray(0xff012b5d);
        mAreas.get(4).getField(1).addColourToArray(0xff002c5d);
        mAreas.get(4).getField(1).addColourToArray(0xff022b57);
        mAreas.get(4).getField(1).addColourToArray(0xff022b57);
        mAreas.get(4).getField(1).addColourToArray(0xff022b57);
        mAreas.get(4).getField(1).addColourToArray(0xff022b57);
        mAreas.get(4).getField(1).addColourToArray(0xff022b57);



        mAreas.get(4).addField(new Field(21, "Surveyors",0xFFC963CF));

        mAreas.get(4).getField(2).addColourToArray(0xff01274e);
        mAreas.get(4).getField(2).addColourToArray(0xff12344f);
        mAreas.get(4).getField(2).addColourToArray(0xff04273b);
        mAreas.get(4).getField(2).addColourToArray(0xff05254e);
        mAreas.get(4).getField(2).addColourToArray(0xff0c2850);
        mAreas.get(4).getField(2).addColourToArray(0xff001d49);
        mAreas.get(4).getField(2).addColourToArray(0xff00224c);
        mAreas.get(4).getField(2).addColourToArray(0xff092a55);
        mAreas.get(4).getField(2).addColourToArray(0xff002552);
        mAreas.get(4).getField(2).addColourToArray(0xff022c5e);


        addConnectedFields();

    }

    public void addConnectedFields()
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

    public void createPlayers() {
        // Generate the players and add colour
        mPlayers.add(new Player("Microsoft", Color.BLACK));
        mPlayers.add(new Player("Google", Color.GREEN));
        mPlayers.add(new Player("Apple", Color.RED));
        assignFields();
    }


    public void assignFields()
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

    public boolean FieldsAllOwned(ArrayList<Field> fields)
    {
        for(Field f : fields)
        {
            if(!f.checkAssigned()) return false;
        }
        return true;
    }

    public void firstTurn(){
        Random Rand = new Random();
        CurrentPlayerNum = Rand.nextInt(3);
    }

    public void AllocateTeams(int numOfTeams){
        teamsToAllocate = numOfTeams;
        testerInitialAllocate();
    }

    public void AllocateTeams(int numOfTeams, int playerNum){
        teamsToAllocate = numOfTeams;
        testerAllocate();
    }

    public void endTurn(boolean bool)
    {
        CurrentPlayerNum++;
        if(CurrentPlayerNum > 2) CurrentPlayerNum = 0;
    }

    public void endTurn()
    {
        if(SuccessfulAttack) {mPlayers.get(CurrentPlayerNum).incrementRiskCards();}
        SuccessfulAttack = false;
        CurrentPlayerNum++;
        if(CurrentPlayerNum > 2) CurrentPlayerNum = 0;
        beginTurn();
    }

    public void beginTurn()
    {
        ArrayList<Field> PlayerFieldsAtTurnStart = findPlayerFields(CurrentPlayerNum);
        int numOfTeamsAllocated = PlayerFieldsAtTurnStart.size() + riskCardCalc() + areaControlledCalc();
        AllocateTeams(numOfTeamsAllocated, CurrentPlayerNum);
    }


    public int riskCardCalc()
    {
        int TeamsForCards = 0;
        int NumOfCards = mPlayers.get(CurrentPlayerNum).getNumOfRiskCards();
        if (NumOfCards > 3) {
            TeamsForCards += 6;
            mPlayers.get(CurrentPlayerNum).useRiskCards();
        }
        return TeamsForCards;
    }

    public int areaControlledCalc()
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


    public ArrayList<Field> findPlayerFields(int PlayerNum)
    {
        ArrayList<Field> TempList = new ArrayList<>();
        for(int i = 0; i < mAreas.size(); i++)
        {
            for(int j = 0; j < mAreas.get(i).getFieldSize(); j++)
            {
                if(mAreas.get(i).getField(j).getFPlayer() == mPlayers.get(PlayerNum))
                {
                    TempList.add(mAreas.get(i).getField(j));
                }
            }
        }
        return TempList;
    }

    public Field findOriginalField(Field tempField)
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

    //Allocation is done through the update method, so cant be tested in its original location
    //This method replaces the "state == ALLLOCATE" statement and corresponding actions
    public void testerInitialAllocate()
    {
        Field tmpField = findPlayerFields(CurrentPlayerNum).get(1);
        if(tmpField != null && findPlayerFields(CurrentPlayerNum).contains(tmpField)){
            findOriginalField(tmpField).incrementNumOfTeams();
            teamsToAllocate--;
            endTurn(false);
        }
        if(teamsToAllocate <= 0)
        {
            state = ATTACK_NULL;
        }
    }

    public void testerAllocate()
    {
        Field tmpField = findPlayerFields(CurrentPlayerNum).get(1);
        if(tmpField != null && findPlayerFields(CurrentPlayerNum).contains(tmpField)){
            findOriginalField(tmpField).incrementNumOfTeams();
            teamsToAllocate--;
        }
        if(teamsToAllocate <= 0)
        {
            state = ATTACK_NULL;
        }
    }
}
