package uk.ac.qub.eeecs.game.RiskGame;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;

public class Battle {
    private int attTotal, defTotal, numOfDiceAtt, numOfDiceDef, numOfAttTeams, numOfDefTeams, DefTeamsLost, AttTeamsLost;
    private DiceRoll diceRollAtt = new DiceRoll(numOfDiceAtt);
    private DiceRoll diceRollDef = new DiceRoll(numOfDiceDef);
    private int[] BattleResults = new int[3]; //first number = attackers teams lost / second number = defenders teams lost / third number = results, 0 for Attacker withdraws, 1 for attackers win

    public Battle(int numOfAttDice, int numOfDefDice, int numOfTeamsAtt, int numOfTeamsDef){
        numOfAttTeams = numOfTeamsAtt;
        numOfDefTeams = numOfTeamsDef;
        numOfDiceAtt = numOfAttDice;
        numOfDiceDef = numOfDefDice;
        DefTeamsLost = 0;
        AttTeamsLost = 0;
    }

    public int[] Battling(){
        do{
            for(int i=0; i<numOfDiceAtt; i++)
            {
                attTotal+=diceRollAtt.roll();
            }
            for(int i=0; i<numOfDiceDef; i++)
            {
                defTotal+=diceRollDef.roll();
            }
            if(defTotal >= attTotal){AttTeamsLost += 1;}
            else{DefTeamsLost += 1;}
        }
        while (numOfAttTeams!= 0 || numOfDefTeams!=0);
        BattleResults[0] = AttTeamsLost;
        BattleResults[1] = DefTeamsLost;
        if(numOfDefTeams == 0) BattleResults[2] = 1;
        else BattleResults[2] = 0;
        return BattleResults;
    }

    //getters for the dice class
    //Philip Murphy

    //public String attackersName(){return attPlayer.getName();}
    //public String defendersName(){return defPlayer.getName();}

    //public int attackerColour(){return attPlayer.getColour();}
    //public int defenderColour(){return defPlayer.getColour();}




}
