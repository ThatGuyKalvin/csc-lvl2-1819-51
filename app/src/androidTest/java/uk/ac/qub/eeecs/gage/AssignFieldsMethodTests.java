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

public class AssignFieldsMethodTests {


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
    public void TestIfFieldsAreAssignedByAssignFieldsMethod()
    {
        RiskGameScreenMethodsTester risk2 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk2.mAreas.get(1).getField(1).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod1()
    {
        RiskGameScreenMethodsTester risk2 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk2.mAreas.get(1).getField(1).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod2() {
        RiskGameScreenMethodsTester risk3 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk3.mAreas.get(0).getField(0).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsAreAssignedByAssignFieldsMethod3() {
        RiskGameScreenMethodsTester risk4 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk4.mAreas.get(2).getField(2).getFPlayer() != null);
    }

    @Test
    public void TestIfFieldsListIsCreatedInAssignFields() {
        RiskGameScreenMethodsTester risk5 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk5.fieldListReturned);
    }

    @Test
    public void TestIfFallFieldsListsIsUpdatedWithPlayers() {
        RiskGameScreenMethodsTester risk6 = new RiskGameScreenMethodsTester(3);
        assertTrue(risk6.firstListHasPlayersAssigned);
    }
}