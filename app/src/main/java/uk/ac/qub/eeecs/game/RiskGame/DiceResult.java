package uk.ac.qub.eeecs.game.RiskGame;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class DiceResult extends Sprite {


    private static final float NUMBER_WIDTH = 50.0f;
    private static final float NUMBER_HEIGHT = 75.0f;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public DiceResult(float startX, float startY, GameScreen gameScreen, int number) {
        super(startX, startY, NUMBER_WIDTH, NUMBER_HEIGHT, null, gameScreen);


        String diceNumber = selectBitmap(number);
        mBitmap = gameScreen.getGame().getAssetManager().getBitmap(diceNumber);
        //the string for drawing the bit map will use this.

    }

    public void update(ElapsedTime elapsedTime){
        super.update(elapsedTime);

    }



    public String selectBitmap(int number){
        switch (number){
            case 1 : return "Number1";
            case 2 : return "Number2";
            case 3 : return "Number3";
            case 4 : return "Number4";
            case 5 : return "Number5";
            case 6 : return "Number6";
            default: return "Number0";
        }
    }
}