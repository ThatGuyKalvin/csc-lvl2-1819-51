package uk.ac.qub.eeecs.game.RiskGame;

public class Battle {
    private int numOfDiceAtt, numOfDiceDef, numOfAttTeams, numOfDefTeams;
    private Field attackField, defendField;
    private DiceRoll diceRollAtt = new DiceRoll(numOfDiceAtt);
    private DiceRoll diceRollDef = new DiceRoll(numOfDiceDef);

    //Player attPlayer, Player defPlayer,
    public Battle(Field att, Field def){
        attackField = att;
        defendField = def;
        numOfAttTeams = att.getNumOfTeams();
        numOfDefTeams = def.getNumOfTeams();
        //automatically setting the highest number of dice that each can roll
        autoSetNumOfDiceAtt();
        setNumOfDiceDef();
    }

    //A method that will complete the battle asap
    //Original by Micheal and reimplemented by philip
    public void fastBattle(){
        do{
            autoSetNumOfDiceAtt();
            singleBattle();
        }
        while (!noTeams());
    }

    //A battle that is done one by one by pressing the roll button on the dice screen.
    //Created by Michael, modified by Philip
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

    //checks if that attacking field wins and returns a boolean value
    //@Philip Murphy
    public boolean attackersWin(){
        if(numOfDefTeams == 0) return true;
        else return false;
    }

    //checks that there are enough teams to have a battle
    //@philip Murphy
    public boolean noTeams(){
        if(numOfAttTeams == 1 || numOfDefTeams == 0) return true;
        else return false;
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
        if(numOfDiceAtt < numOfAttTeams && connected()) return true;
        else return false;
    }

    //checks that two fields are connected
    //@Philip Murphy
    private boolean connected(){
        for(int i = 0; i < attackField.getConnectedFields().size(); i++)
            if(defendField == attackField.getConnectedFields().get(i))
                return true;

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
        switch  (attackField.getNumOfTeams()){
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
        if(defendField.getNumOfTeams() >= 3){
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

    public String getAttPlayerName(){return attackField.getPlayer().getName();}
    public String getDefPlayerName(){return defendField.getPlayer().getName();}
}




