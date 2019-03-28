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
    private int FNumOfTeams = 2;
    private boolean assigned = false;
    private ArrayList<Field> FConnectedFields;
    private ArrayList<Integer> colourArray = new ArrayList<>();
    private int FColour;

    public Field(int Num, String Name, int Colour) {
        FNum = Num;
        FName = Name;
        FColour = Colour;
    }

    public void setPlayer(Player player)
    {
        FPlayer = player;
        assigned = true;
    }

    public int getColourArray(int i) { return colourArray.get(i); }

    public int getColourArraySize() { return colourArray.size(); }

    public void addColourToArray(int colour) { colourArray.add(colour); }

    public boolean checkAssigned(){return assigned;}

    public int getColour() { return FColour; }

    public void setColour(int colour) { FColour = colour; }

    public void setNumOfTeams(int teams) {FNumOfTeams = teams;}

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
