import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    LogicClass logic;

    @Before
    public void init(){
        logic = new LogicClass();
    }

    /* Testcase [1]: car is at position 500 and wants to move forward, the actuators should return false,
     since it should not move forward in this case. */
    @Test
    public void TestAverageOf5(){
        int[] testValues = new int[]{5, 5, 5, 5};
        int expected = 5;
        int result = logic.calculateAverage(testValues);
        assertEquals(expected, result);
    }
    @Test
    public void TestAverageOfTwoArrays() {
        int[] testValues1 = new int[]{1, 3, 9, 8};
        int[] testValues2 = new int[]{3, 7, 4, 6};
        int expected = 5;
        int result = logic.calculateAverage(testValues1, testValues2);
        assertEquals(expected, result);
    }

    /* Testcase [2]: car is at position 0 and wants to move backward, the actuators should return false,
     since it should not move backward in this case. */
    @Test
    public void TestStandardDeviationIs0(){
        int[] testValues = new int[]{5, 5, 5, 5};
        int expected = 0;
        double result = logic.calculateStandardDeviation(testValues);
        assertEquals(expected, result);
    }

    /* Testcase [3]: car is at a position > 0 and < 500 and wants to move forward, the actuators should return true,
     since it should not be problem moving forward in this case. */
    @Test
    public void TestStandardDeviationOf1234(){
        int[] testValues = new int[]{1, 2, 3, 4};
        double expected = 1.224744871391589;
        double result = logic.calculateStandardDeviation(testValues);
        assertEquals(expected, result);

    }
}