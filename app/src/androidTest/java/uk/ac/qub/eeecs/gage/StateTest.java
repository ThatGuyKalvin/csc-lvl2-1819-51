//@Author: Kalvin Johnston
package uk.ac.qub.eeecs.gage;
import android.content.Context;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StateTest {

    private Context Context;
    private final int ATTACK_NULL = 0;
    private final int ATTACK_PICK = 1;
    private final int ATTACK_PICK_AGAIN = 2;
    private final int ATTACK_BATTLING = 3;
    private final int ALLOCATE = 4;
    private final int INITIAL_ALLOCATE = 5;
    private int state = ATTACK_NULL;
    private String attackStr = "State: Not battling.";

    @Test
    public void correctStateTest() {
        for(int state = 0; state < 7; state++) {
            switch (state) {
                case ATTACK_NULL:
                    attackStr = "State: Not battling.";
                    assertEquals("State: Not battling.", attackStr);
                    break;
                case ATTACK_PICK:
                    attackStr = "State: Pick area 1.";
                    assertEquals("State: Pick area 1.", attackStr);
                    break;
                case ATTACK_PICK_AGAIN:
                    attackStr = "State: Pick area 2.";
                    assertEquals("State: Pick area 2.", attackStr);
                    break;
                case ATTACK_BATTLING:
                    attackStr = "State: Battle: [ ] vs. [ ].";
                    assertEquals("State: Battle: [ ] vs. [ ].", attackStr);
                    break;
                case ALLOCATE:
                    attackStr = "State: Player [ ] Allocate Team.";
                    assertEquals("State: Player [ ] Allocate Team.", attackStr);
                    break;
                case INITIAL_ALLOCATE:
                    attackStr = "State: Player [ ] Allocate Team";
                    assertEquals("State: Player [ ] Allocate Team", attackStr);
                    break;
                default:
                    attackStr = "State: Not battling.";
                    assertEquals("State: Not battling.", attackStr);
                    break;
            }
        }
    }
}