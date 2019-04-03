package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import java.util.ArrayList;

public class Area {

    // Peter? - Not really sure what this is.
    ArrayList<Field> aFields = new ArrayList<>();

    private int colour;
    private String name;
    private ArrayList<Field> fields = new ArrayList<>();
    private int value = 0;

    // Package private instead of public
    public Area() {
        this.colour = Color.BLACK;
        this.name = "DEFAULT";
    }

    // Package private instead of public
    public Area(String name, int colour, int value) {
        this.name = name;
        this.colour = colour;
        this.value = value;
    }

    public void addField(Field f) { fields.add(f); }
    public Field getField(int i) { return fields.get(i); }

    public int getFieldSize() { return fields.size(); }

    public void setColour(int colour) {
        this.colour = colour;
    }
    public int getColour() {
        return this.colour;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
