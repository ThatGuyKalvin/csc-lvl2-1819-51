package uk.ac.qub.eeecs.game.RiskGame;

import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.ui.PushButton;

public class Battle {
    private int numOfDiceAtt, numOfDiceDef, numOfAttTeams, numOfDefTeams, DefTeamsLost, AttTeamsLost, minimumNumDice;
    private int[] BattleResults = new int[3]; //first number = attackers teams lost / second number = defenders teams lost / third number = results, 0 for Attacker withdraws, 1 for attackers win
    private int[] DefResults, AttResults;
    private DiceRoll diceRollAtt = new DiceRoll(numOfDiceAtt);
    private DiceRoll diceRollDef = new DiceRoll(numOfDiceDef);

    //Player attPlayer, Player defPlayer,
    public Battle( int numOfAttDice, int numOfDefDice, int numOfTeamsAtt, int numOfTeamsDef){
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



    //A battle that is done one by one by pressing the roll button on the dice screen.
    //@Philip Murphy
    public void singleBattle(){
        setDiceRollDef();
        if(canBattle()){
            newRoll();

            if(diceRollDef.getTotal() >= diceRollAtt.getTotal()){
                numOfAttTeams--;
            } else{numOfDefTeams--;}

            if(numOfAttTeams == 0 || numOfDefTeams == 0){
                //do something
            }
        }
    }

    //Creates a new set of values for the arrays
    //@Philip Murphy
    public void newRoll(){
        diceRollAtt = new DiceRoll(numOfDiceAtt);
        diceRollDef = new DiceRoll(numOfDiceDef);
    }

    //@Philip Murphy
    //Checks that the number of dice is right.
    public boolean canBattle(){
        if(numOfDiceAtt < numOfAttTeams){ return true; }
        return false;
    }

    //Creating an array and then setting all the values to 0 for the first roll
    //@Philip Murphy
    public void resetDice(){
        newRoll();
        for(int i = 0; i<diceRollAtt.getDiceResults().length; i++)
            diceRollAtt.getDiceResults()[i] = 0;
        for(int i = 0; i <diceRollDef.getDiceResults().length; i++)
            diceRollDef.getDiceResults()[i] = 0;
    }


    //getters and setters that can be used by the buttons in the dice screen
    //@Philip Murphy

    public void setNumOfAttTeams(int numOfAttTeams) { this.numOfAttTeams = numOfAttTeams; }
    public void setNumOfDefTeams(int numOfDefTeams){ this.numOfDefTeams = numOfDefTeams; }

    public void setNumOfDiceAtt(int number){ this.numOfDiceAtt = number;}
    public void setDiceRollDef(){
        if(numOfDefTeams >= 3){
            this.numOfDiceDef = 2;
        }else{
            this.numOfDiceDef = 1;
        }
    }

    public int getNumOfAttTeams(){ return numOfAttTeams; }
    public int getNumOfDefTeams(){ return numOfDefTeams; }

    public int[] getDiceResultsAtt(){return diceRollAtt.getDiceResults();}
    public int[] getDiceResultsDef(){ return diceRollDef.getDiceResults(); }

    public int getAttDiceTotal(){return diceRollAtt.getTotal();}
    public int getDefDiceTotal(){return diceRollDef.getTotal();}
}




