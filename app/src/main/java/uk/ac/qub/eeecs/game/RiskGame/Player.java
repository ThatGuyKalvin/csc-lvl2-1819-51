package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class Player {

    private String pName;
    private int pColour;

    public Player(String name, int colour) {
        this.pName = name;
        this.pColour = colour;
    }


    // Get name of the Player
    public String getName() {
        return this.pName;
    }
    // Set name of the Player
    public void setName(String name) {
        this.pName = name;
    }

    // Get colour of the Player
    public int getColour() {
        return this.pColour;
    }
    // Set colour of the Player
    public void setColour(int colour) {
        this.pColour = colour;
    }
}
