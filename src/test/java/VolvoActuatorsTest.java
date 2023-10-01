import org.junit.Test;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

public class VolvoActuatorsTest{

    VolvoActuators actuator;

    @Before
    public void init(){
        actuator = new VolvoActuators();
    }

    /* Testcase [1]: car is at position 500 and wants to move forward, the actuators should return false,
     since it should not move forward in this case. */ 
    @Test 
    public void TestmoveIfAllowed_MoveForwardAndEndOfStreet_returnsFalse(){
        
        actuator.position = 499;
        boolean moveforward = true;

        boolean result = actuator.moveIfAllowed(moveforward);

        assertFalse(result);

    }

    /* Testcase [2]: car is at position 0 and wants to move backward, the actuators should return false,
     since it should not move backward in this case. */ 
     @Test 
    public void TestmoveIfAllowed_MoveBackwardAndBeginningOfStreet_returnsFalse(){

        actuator.position = 0;
        boolean moveforward = false;

        boolean result = actuator.moveIfAllowed(moveforward);

        assertFalse(result);

    }

    /* Testcase [3]: car is at a position > 0 and < 500 and wants to move forward, the actuators should return true,
     since it should not be problem moving forward in this case. */ 
    @Test 
    public void TestmoveIfAllowed_MoveForwardOK_returnsTrue(){

        actuator.position = 5;
        boolean moveforward = true;

        boolean result = actuator.moveIfAllowed(moveforward);

        assertEquals(6, actuator.position); //check if it moved forward one step
        assertTrue(result);

    }

      /* Testcase [4]: car is at a position > 0 and < 500 and wants to move backward, the actuators should return true,
        since it should not be problem moving backward in this case. */ 
    @Test 
    public void TestmoveIfAllowed_MoveBackwardOK_returnsTrue(){

        actuator.position = 5;
        boolean moveforward = false;

        boolean result = actuator.moveIfAllowed(moveforward);
        assertEquals(4, actuator.position); // check if it moved back one step
        assertTrue(result);

    }

    /*Testcase [5]: car is at negative position, moveIfAllowed() should return false
    * */
    @Test
    public void TestmoveIfAllowed_positionBelowZero_returnFalse()
    {
        actuator.position = -1;
        boolean moveforward = true; // could be false also, does not matter
        boolean result = actuator.moveIfAllowed(moveforward);

        assertFalse(result);
    }



}