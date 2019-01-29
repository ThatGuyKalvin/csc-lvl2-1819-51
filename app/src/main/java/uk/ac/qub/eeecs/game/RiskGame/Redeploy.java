package uk.ac.qub.eeecs.game.RiskGame;

import java.util.ArrayList;

public class Redeploy {


    private float FieldArmyRatio = 0.5f;
    private int minTeams = 3;
    private Player rPlayer;
    private ArrayList<Field> rPlayerFields;

    public Redeploy (Player player) {

        rPlayer = player;
        // rPlayerFields = rPlayer.getListOfFields
        AssignNewTeams(calculateTeams());
    }


    public int calculateTeams(){

        //calculate length array * ratio

        

        // if smaller than min return min

//        if(team < minTeams){
//            return minTeams
//        } else

        // else return big

        // calculate teams added for controlling area(collection of fields)

        return 0;
    }

    public void AssignNewTeams(int Teams) {

    }


}
