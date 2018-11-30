package uk.ac.qub.eeecs.game.RiskGame;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;

public class Battle {
    private Player attPlayer;
    private Player defPlayer;
    private Area attArea;
    private Area defArea;
    private int attTotal, defTotal, numOfDiceAtt, numOfDiceDef, numOfAttArmies, numOfDefArmies;
    private DiceRoll diceRollAtt = new DiceRoll(numOfDiceAtt);
    private DiceRoll diceRollDef = new DiceRoll(numOfDiceDef);

    public Battle(Player a, Player d, Area aa, Area ad){
        attPlayer = a;
        defPlayer = d;
        attArea = aa;
        defArea = ad;
    }

    public void Battling(){
        do{
            for(int i=0; i<numOfDiceAtt; i++)
            {
                attTotal+=diceRollAtt.roll();
            }
            for(int i=0; i<numOfDiceDef; i++)
            {
                defTotal+=diceRollDef.roll();
            }
            if(defTotal >= attTotal){numOfAttArmies = numOfAttArmies - 1;}
            else{numOfDefArmies = numOfDefArmies - 1;}
        }
        while (numOfAttArmies!= 0 || numOfDefArmies!=0);
    }
}
