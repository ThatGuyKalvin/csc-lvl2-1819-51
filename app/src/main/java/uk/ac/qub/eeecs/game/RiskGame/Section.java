package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import java.util.ArrayList;

public class Section {

    private int aColour;
    private String aName;
    private ArrayList<Field> FieldsInSection;

    public Section() {
        this.aColour = Color.RED;
        this.aName = "aName";
    }

    //Set colour of the section
    public void setColour(int colour) {
        this.aColour = colour;
    }

    //Get colour of the section
    public int getColour() {
        return this.aColour;
    }

    //Set name of the section
    public void setName(String name) {
        this.aName = name;
    }

    //Get name of the section
    public String getName() {
        return this.aName;
    }
}

