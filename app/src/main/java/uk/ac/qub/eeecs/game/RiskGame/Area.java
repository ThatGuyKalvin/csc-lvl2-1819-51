package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import java.util.ArrayList;

public class Area {

    private int aColour;
    private String aName;
    private Player aOwner;
    private ArrayList<Field> aFields = new ArrayList<>();

    // Package private instead of public
    Area() {
        this.aColour = Color.BLACK;
        this.aName = "DEFAULT";
        this.aOwner = null;
    }

    // Package private instead of public
    Area(String name, int colour) {
        this.aName = name;
        this.aColour = colour;
    }

    void addField(Field f) { aFields.add(f); }
    Field getField(int i) { return aFields.get(i); }

    public void setOwner(Player owner) { this.aOwner = owner; }
    public Player getOwner() {
        return this.aOwner;
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
