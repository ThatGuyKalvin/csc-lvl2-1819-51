package uk.ac.qub.eeecs.game.RiskGame;


public class DiceRoll {

    private int[] diceResults;
    private int total;

    public DiceRoll(int num_of_dice){

        //set the size of the array based on the number of dice the user wants to roll
        diceResults = new int[num_of_dice];

        //adds a list of random number to the array using the roll method
        for(int i=0;i<num_of_dice;i++) {
            diceResults[i] = roll();
            total += diceResults[i];
        }
    }

    //get a random number between one and 6 simulating rolling a dice.
    public int roll(){
        return (int) (Math.random() * 6) + 1;
    }

    //returns the int array of results
    public int[] getDiceResults(){
        return diceResults;
    }

    //returns the total of all the dice rolled
    public int getTotal(){return total;}
}