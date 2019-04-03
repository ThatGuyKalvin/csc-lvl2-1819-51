package uk.ac.qub.eeecs.gage;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.qub.eeecs.game.RiskGame.Area;
import uk.ac.qub.eeecs.game.RiskGame.Field;
import uk.ac.qub.eeecs.game.RiskGame.Player;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreen;
import uk.ac.qub.eeecs.game.RiskGame.RiskGameScreenMethodsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RiskGameScreenMethodsTesting {


    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void TestIfCreateAreasCreatesAreas()
    {
        RiskGameScreenMethodsTester risk1 = new RiskGameScreenMethodsTester(1);
        assertEquals(risk1.mAreas.get(1).getField(2).getColour(), 0xFF24401F);
        assertEquals(risk1.mAreas.get(2).getField(2).getFName(), "Python");
    }

    @Test
    public void TestIfPlayersCreated()
    {
        RiskGameScreenMethodsTester Risk = new RiskGameScreenMethodsTester(2);
        assertEquals(Risk.mPlayers.get(1).getName(), "Google");
        assertEquals(Risk.mPlayers.get(0).getColour(), Color.BLACK);
    }

    @Test
    public void TestIfFieldsAreConnected()
    {
        RiskGameScreenMethodsTester Risk1 = new RiskGameScreenMethodsTester(2);
        assertEquals(Risk1.mAreas.get(1).getField(2).getFConnectedFields().get(1).getFNum(), 7);
        assertEquals(Risk1.mAreas.get(2).getField(1).getFConnectedFields().get(2).getFNum(), 15);
        assertEquals(Risk1.mAreas.get(1).getField(3).getFConnectedFields().get(0).getFNum(), 7);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod()
    {
        RiskGameScreenMethodsTester risk2 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk2.mAreas.get(1).getField(1).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod1()
    {
        RiskGameScreenMethodsTester risk3 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk3.mAreas.get(1).getField(2).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod2() {
        RiskGameScreenMethodsTester risk4 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk4.mAreas.get(0).getField(0).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod3() {
        RiskGameScreenMethodsTester risk5 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk5.mAreas.get(2).getField(2).getFPlayer() != null);
    }

    @Test
    public void TestIf
}