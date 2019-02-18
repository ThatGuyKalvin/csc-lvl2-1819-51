package uk.ac.qub.eeecs.game.RiskGame;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;

public class Battle {
    private int numOfDiceAtt, numOfDiceDef, numOfAttTeams, numOfDefTeams, DefTeamsLost, AttTeamsLost, minimumNumDice;
    private int[] BattleResults = new int[3]; //first number = attackers teams lost / second number = defenders teams lost / third number = results, 0 for Attacker withdraws, 1 for attackers win
    private int[] DefResults, AttResults;

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
           DiceRoll def = new DiceRoll(numOfDiceDef);
           DiceRoll att = new DiceRoll(numOfDiceAtt);
           DefResults = sort(def.getDiceResults());
           AttResults = sort(att.getDiceResults());
           if(DefResults.length > AttResults.length){minimumNumDice = AttResults.length;}
           else {minimumNumDice = DefResults.length;}
           for(int i = 0; i < minimumNumDice; i++)
           {
               if(DefResults[i] >= AttResults[i]){AttTeamsLost++;}
               else {DefTeamsLost++;}
           }
        }
        while (numOfAttTeams!= 0 || numOfDefTeams!=0);
        BattleResults[0] = AttTeamsLost;
        BattleResults[1] = DefTeamsLost;
        if(numOfDefTeams == 0) BattleResults[2] = 1;
        else BattleResults[2] = 0;
        return BattleResults;
    }

    public int[] sort(int[] results)
    {
        int temp;
        for (int i = 1; i < results.length; i++)
        {
            for(int j = i; j > 0; j--)
                if(results[i] > results[i-1])
                {
                    temp = results[i-1];
                    results[i-1] = results[i];
                    results[i] = temp;
                }
        }
        return results;
    }

    //getters for the dice class
    //Philip Murphy

    //public String attackersName(){return attPlayer.getName();}
    //public String defendersName(){return defPlayer.getName();}

    //public int attackerColour(){return attPlayer.getColour();}
    //public int defenderColour(){return defPlayer.getColour();}




}
