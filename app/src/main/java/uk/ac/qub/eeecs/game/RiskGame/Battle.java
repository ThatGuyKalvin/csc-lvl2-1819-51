package uk.ac.qub.eeecs.game.RiskGame;

public class Battle {
    private int numOfDiceAtt, numOfDiceDef, numOfAttTeams, numOfDefTeams, DefTeamsLost, AttTeamsLost, minimumNumDice;
    Field attackField, defendField;
    private int[] BattleResults = new int[3]; //first number = attackers teams lost / second number = defenders teams lost / third number = results, 0 for Attacker withdraws, 1 for attackers win
    private int[] DefResults, AttResults;
    private DiceRoll diceRollAtt = new DiceRoll(numOfDiceAtt);
    private DiceRoll diceRollDef = new DiceRoll(numOfDiceDef);

    //Player attPlayer, Player defPlayer,
    public Battle(Field att, Field def){
        attackField = att;
        defendField = def;
        numOfAttTeams = att.getFNumOfTeams();
        numOfDefTeams = def.getFNumOfTeams();
        autoSetNumOfDiceAtt();
        setNumOfDiceDef();
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

    //A method that will complete the battle asap
    //Original by Micheal and reimplemented by philip
    //after peter changed the battle class.
    public void fastBattle(){
        do{
            autoSetNumOfDiceAtt();
            singleBattle();
        }
        while (!noArmies());
    }

    //A battle that is done one by one by pressing the roll button on the dice screen.
    //@Philip Murphy
    public void singleBattle(){
        setNumOfDiceDef();
        if(canBattle()){
            newRoll();
            if(diceRollDef.getTotal() >= diceRollAtt.getTotal()){
                numOfAttTeams--;
                attackField.decreaseNumOfTeams(1);
            }else {
                numOfDefTeams--;
                defendField.decreaseNumOfTeams(1);
            }
        }
    }

    public boolean attackersWin(){
        if(numOfDefTeams == 0)
            return true;
        return false;
    }

    public boolean noArmies(){
        if(numOfAttTeams == 1 || numOfDefTeams == 0)
            return true;
        return false;
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
        for(int i = 0; i<diceRollAtt.getDiceResults().length; i++)
            diceRollAtt.getDiceResults()[i] = 0;
        for(int i = 0; i <diceRollDef.getDiceResults().length; i++)
            diceRollDef.getDiceResults()[i] = 0;
    }
    public void autoSetNumOfDiceAtt(){
        switch  (attackField.getFNumOfTeams()){
            case 2 : this.numOfDiceAtt = 1;
                break;
            case 3 : this.numOfDiceAtt = 2;
                break;
            default: this.numOfDiceAtt = 3;
                break;
        }
    }

    //getters and setters that can be used by the buttons in the dice screen
    //@Philip Murphy

    public void setNumOfDiceAtt(int number){ this.numOfDiceAtt = number;}

    public void setNumOfDiceDef(){
        if(defendField.getFNumOfTeams() >= 3){
            this.numOfDiceDef = 2;
        }else{
            this.numOfDiceDef = 1;
        }
    }

    public int getNumOfAttTeams(){ return numOfAttTeams; }
    public int getNumOfDefTeams(){ return numOfDefTeams; }

    public int[] getDiceResultsAtt(){return diceRollAtt.getDiceResults();}
    public int[] getDiceResultsDef(){ return diceRollDef.getDiceResults();}

    public int getAttDiceTotal(){return diceRollAtt.getTotal();}
    public int getDefDiceTotal(){return diceRollDef.getTotal();}
}




