package uk.ac.qub.eeecs.gage;

import android.graphics.Color;

import org.junit.Test;

import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreenMethodsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RiskGameScreenMethodsTesting {

    @Test
    public void TestIfCreateAreasCreatesAreas()
    {
        RiskGameScreenMethodsTester risk1 = new RiskGameScreenMethodsTester(1);
        assertEquals(risk1.mAreas.get(1).getField(2).getColour(), 0xFF24401F);
        assertEquals(risk1.mAreas.get(2).getField(2).getName(), "Python");
    }

    @Test
    public void TestIfPlayersCreated()
    {
        RiskGameScreenMethodsTester Risk = new RiskGameScreenMethodsTester(2);
        assertEquals(Risk.mPlayers.get(1).getName(), "Google");
        assertEquals(Risk.mPlayers.get(0).getColour(), Color.BLACK);
    }


    //Author Michael McKeown
    @Test
    public void TestIfFieldsAreConnected()
    {
        RiskGameScreenMethodsTester Risk1 = new RiskGameScreenMethodsTester(2);
        assertEquals(Risk1.mAreas.get(1).getField(2).getConnectedFields().get(1).getNum(), 7);
        assertEquals(Risk1.mAreas.get(2).getField(1).getConnectedFields().get(2).getNum(), 15);
        assertEquals(Risk1.mAreas.get(1).getField(3).getConnectedFields().get(0).getNum(), 7);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod()
    {
        RiskGameScreenMethodsTester risk2 = new RiskGameScreenMethodsTester(3);
        assertNotNull(risk2.mAreas.get(1).getField(1).getPlayer());
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod1()
    {
        RiskGameScreenMethodsTester risk2 = new RiskGameScreenMethodsTester(3);
        assertNotNull(risk2.mAreas.get(1).getField(1).getPlayer());
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod2() {
        RiskGameScreenMethodsTester risk3 = new RiskGameScreenMethodsTester(3);
        assertNotNull(risk3.mAreas.get(0).getField(0).getPlayer());
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod3() {
        RiskGameScreenMethodsTester risk4 = new RiskGameScreenMethodsTester(3);
        assertNotNull(risk4.mAreas.get(2).getField(2).getPlayer());
    }

    //@Test
    //public void TestIf
}