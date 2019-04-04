package uk.ac.qub.eeecs.game.RiskGame;

import java.util.ArrayList;

//Field class to use for individual fields within an area and manipulate them
//Author: @Peter Gilfedder & @Kalvin Johnston

public class Field{

    private int num;
    private String name;
    private Player player;
    private int numOfTeams = 1;
    private boolean assigned = false;
    private ArrayList<Field> connectedFields;
    private ArrayList<Integer> colourArray = new ArrayList<>();
    private int colour;

    public Field(int num, String name, int colour) {
        this.num = num;
        this.name = name;
        this.colour = colour;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
        assigned = true;
    }

    public int getColourArray(int i) { return colourArray.get(i); }

    public int getColourArraySize() { return colourArray.size(); }

    public void addColourToArray(int colour) { colourArray.add(colour); }

    public boolean checkAssigned() { return assigned; }

    public int getColour() { return colour; }

    public void setColour(int colour) { this.colour = colour; }

    public void setNumOfTeams(int teams) {
        numOfTeams = teams;}

    public void incrementNumOfTeams(){
        numOfTeams++;
    }

    public void decreaseNumOfTeams(int decrease){
        numOfTeams -= decrease;
    }

    public Player getPlayer(){
        return player;
    }

    public int getNumOfTeams(){
        return numOfTeams;
    }

    public ArrayList<Field> getConnectedFields(){
        return connectedFields;
    }

    public void addConnectedFields(ArrayList<Field> Connected){
        connectedFields = Connected;}

    public String getName(){return name;}

    public int getNum(){return num;}

    public void hostileTakeOver(Player Team, int numOfTeams)
    {
        player = Team;
        this.numOfTeams = numOfTeams;
    }
}
