package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Bitmap;

import java.util.ArrayList;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class Field extends Sprite {

    private String FColour;
    private String FAssociation;
    private int FNumOfTeams;
    private ArrayList<Field> FConnectedFields;
    private float FCentreX;
    private float FCentreY;
    private Bitmap FBitmap;

    public Field(float startX, float startY,
                 float width, float height, Bitmap bitmap, GameScreen gameScreen, ArrayList<Field> ConnectedFields) {
        super(startX, startY, width, height, bitmap, gameScreen);

        FCentreX = startX;
        FCentreY = startY;
        FBitmap = bitmap;
        FConnectedFields = ConnectedFields;
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

    public void changeAssociation(String Association, String Colour){
        FAssociation = Association;
        FColour = Colour;
    }

    public String getFAssociation(){
        return FAssociation;
    }

    public String getFColour(){
        return FColour;
    }

    public int getFNumOfTeams(){
        return FNumOfTeams;
    }

    public ArrayList<Field> getFConnectedFields(){
        return FConnectedFields;
    }

    public float GetFCentreX(){
        return FCentreY;
    }

    public float getFCentreY(){
        return FCentreY;
    }






}
