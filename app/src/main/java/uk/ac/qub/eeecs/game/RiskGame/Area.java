package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import java.util.ArrayList;

public class Area {

    ArrayList<Field> fields = new ArrayList<>();

    private int aColour;
    private String aName;
    private ArrayList<Field> aFields = new ArrayList<>();
    private int aValue = 0;

    // Package private instead of public
    public Area() {
        this.aColour = Color.BLACK;
        this.aName = "DEFAULT";
    }

    // Package private instead of public
    public Area(String name, int colour, int value) {
        this.aName = name;
        this.aColour = colour;
        this.aValue = value;
    }

    public void addField(Field f) { aFields.add(f); }
    public Field getField(int i) { return aFields.get(i); }

    public int getFieldSize() { return aFields.size(); }

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

    public void setValue(int value) {
        this.aValue = value;
    }
    public int getValue() {
        return this.aValue;
    }
}
