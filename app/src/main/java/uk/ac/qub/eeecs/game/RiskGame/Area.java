package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import java.util.ArrayList;

public class Area {

    ArrayList<Field> fields = new ArrayList<>();

    private int aColour;
    private String aName;

    // Package private instead of public
    Area() {
        this.aColour = Color.BLACK;
        this.aName = "DEFAULT";
    }

    // Package private instead of public
    Area(String name, int colour) {
        this.aName = name;
        this.aColour = colour;
    }

    public void setColour(int colour) {
        this.aColour = colour;
    }
    public int getColour() {
        return this.aColour;
    }

    public void setName(String name) {
        this.aName = name;
    }
    public String getName() {
        return this.aName;
    }
}
