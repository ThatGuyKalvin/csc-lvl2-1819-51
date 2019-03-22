package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

//Field class to use for individual fields within an area and manipulate them
//Author: Peter Gilfedder

public class Field{

    private int FNum;
    private String FName;
    private Player FPlayer;
    private int FNumOfTeams = 1;
    private ArrayList<Field> FConnectedFields;
    private int FColour;

    public Field(int Num, String Name, int Colour) {
        FNum = Num;
        FName = Name;
        FColour = Colour;
    }

    public void setPlayer(Player player){FPlayer = player;}

    public int getColour() { return FColour; }

    public void setColour(int colour) { FColour = colour; }

    public void setNumOfTeams(int teams) {FNumOfTeams = teams;}

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

    public void addConnectedFields(ArrayList<Field> Connected){FConnectedFields = Connected;}

    public String getFName(){return FName;}

    public int getFNum(){return FNum;}

    public void hostileTakeOver(Player Team, int numOfTeams)
    {
        FPlayer = Team;
        FNumOfTeams = numOfTeams;
    }
}
