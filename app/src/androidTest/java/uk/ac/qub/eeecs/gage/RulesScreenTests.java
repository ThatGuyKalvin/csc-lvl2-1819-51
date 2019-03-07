package uk.ac.qub.eeecs.gage;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.content.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/*
Author: Daniel Nelis Entire Class
 */

@RunWith(AndroidJUnit4.class)
public class RulesScreenTests {




    private Context context;

    @Before
    public void setUp(){
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testCorrectScreenChange()
    {
        //This is the test data
    }

}
