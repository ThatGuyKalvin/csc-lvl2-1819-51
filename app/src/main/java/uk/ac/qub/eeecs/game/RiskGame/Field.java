package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

//Field class to use for individual fields within an area and manipulate them
//Author: Peter Gilfedder

public class Field extends Sprite {

    private int FNum;
    private String FName;
    private Player FPlayer;
    private int FNumOfTeams;
    private ArrayList<Field> FConnectedFields;
    private float FCentreX;
    private float FCentreY;
    private Bitmap FBitmap;

    public Field(float startX, float startY,
                 float width, float height, Bitmap bitmap, GameScreen gameScreen, int Num, String Name, Player player, int NumOfTeams) {
        super(startX, startY, width, height, bitmap, gameScreen);

        FCentreX = startX;
        FCentreY = startY;
        FBitmap = bitmap;
        FNum = Num;
        FName = Name;
        FPlayer = player;
        FNumOfTeams = NumOfTeams;
    }

    Field(GameScreen gameScreen, int num, String name, Player owner, int numOfTeams) {
        super(50, 50, 50, 50, null, gameScreen);
        FNum = num;
        FName = name;
        FPlayer = owner;
        FNumOfTeams = numOfTeams;
    }

    public void increaseNumOfTeams(int increase){
        FNumOfTeams += increase;
    }

    public void incrementNumOfTeams(){
        FNumOfTeams++;
    }

    public void decreaseNumOfTeams(int decrease){
        FNumOfTeams -= decrease;
    }

    public void decrementNumOfTeams(){
        FNumOfTeams--;
    }

    public Player getFPlayer(){
        return FPlayer;
    }

    public int getFNumOfTeams(){
        return FNumOfTeams;
    }

    public ArrayList<Field> getFConnectedFields(){
        return FConnectedFields;
    }

    public float GetFCentreX(){ return FCentreX; }

    public float getFCentreY(){
        return FCentreY;
    }

    public String getFName(){return FName;}

    public int getFNum(){return FNum;}

    public void hostileTakeOver(Player Team, int numOfTeams)
    {
        FPlayer = Team;
        FNumOfTeams = numOfTeams;
    }
}
