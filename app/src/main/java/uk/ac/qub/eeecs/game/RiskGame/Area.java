package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

public class Area {

    private int aColour;
    private String aName;

    public Area() {
        this.aColour = Color.BLACK;
        this.aName = "aName";
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
