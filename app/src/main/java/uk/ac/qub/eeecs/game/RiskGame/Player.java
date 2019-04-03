package uk.ac.qub.eeecs.game.RiskGame;

//@Author: Kalvin Johnston & Aimee Millar (I think?)
public class Player {

    private String name;
    private int colour;
    private int riskCardCount = 0;

    public Player(String name, int colour) {
        this.name = name;
        this.colour = colour;
    }


    // Get name of the Player
    public String getName() {
        return this.name;
    }
    // Set name of the Player
    public void setName(String name) {
        this.name = name;
    }
    // Increment Number of Risk Cards
    public void incrementRiskCards(){
        riskCardCount++;}
    // Use Risk Cards
    public void useRiskCards(){
        riskCardCount = riskCardCount - 3;}
    //Get Number of Risk Cards
    public int getNumOfRiskCards(){return riskCardCount;}
    // Get colour of the Player
    public int getColour() {
        return this.colour;
    }
    // Set colour of the Player
    public void setColour(int colour) {
        this.colour = colour;
    }
}
