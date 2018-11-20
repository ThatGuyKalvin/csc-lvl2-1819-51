package uk.ac.qub.eeecs.game.RiskGame;

import android.graphics.Color;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class Player {

    private String pName = "";
    private int pColour = Color.RED;

    public Player(String name) {
        this.pName = name;
    }
}
