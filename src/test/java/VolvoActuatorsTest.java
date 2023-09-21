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
    public void TestinsideLimits_MoveForwardAndEndOfStreet_returnsFalse(){

        int position = 500;
        boolean moveforward = true;
        
        boolean result = actuator.insideLimits(position, moveforward);

        assertFalse(result);

    }
    /* Testcase [2]: car is at position 0 and wants to move backward, the actuators should return false,
     since it should not move backward in this case. */ 
     @Test 
    public void TestinsideLimits_MoveBackwardAndBeginningOfStreet_returnsFalse(){

        int position = 0;
        boolean moveforward = false;
        
        boolean result = actuator.insideLimits(position, moveforward);

        assertFalse(result);

    }

    /* Testcase [3]: car is at a position > 0 and < 500 and wants to move forward, the actuators should return true,
     since it should not be problem moving forward in this case. */ 
    @Test 
    public void TestinsideLimits_MoveForwardOK_returnsTrue(){

        int position = 5;
        boolean moveforward = true;
        
        boolean result = actuator.insideLimits(position, moveforward);

        assertTrue(result);

    }

      /* Testcase [4]: car is at a position > 0 and < 500 and wants to move backward, the actuators should return true,
        since it should not be problem moving backward in this case. */ 
    @Test 
    public void TestinsideLimits_MoveBackwardOK_returnsTrue(){

        int position = 5;
        boolean moveforward = false;
        
        boolean result = actuator.insideLimits(position, moveforward);

        assertTrue(result);

    }



}