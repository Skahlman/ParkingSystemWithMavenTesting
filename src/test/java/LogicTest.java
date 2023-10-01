import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    LogicClass logic;

    @Before
    public void init(){
        logic = new LogicClass();
    }

    /* Testcase [1]: Calculates average receives an array of only fives and should return 5 as all numbers are the same*/
    @Test
    public void TestAverageOf5(){
        int[] testValues = new int[]{5, 5, 5, 5};
        int expected = 5;
        int result = logic.calculateAverage(testValues);
        assertEquals(expected, result);
    }
    /* Testcase [2]: Calculates average receives two arrays and should return 5 as:
     * 1 + 3 + 9 + 8 + 3 + 7+ 4 + 6 = 40 and 40 / 8 = 5*/
    @Test
    public void TestAverageOfTwoArrays() {
        int[] testValues1 = new int[]{1, 3, 9, 7};
        int[] testValues2 = new int[]{3, 7, 4, 6};
        int expected = 5;
        int result = logic.calculateAverage(testValues1, testValues2);
        assertEquals(expected, result);
    }

    /* Testcase [3]: Standard deviation receives an array of equal values and should return 0. */
    @Test
    public void TestStandardDeviationIs0(){
        int[] testValues = new int[]{5, 5, 5, 5};
        int expected = 0;
        double result = logic.calculateStandardDeviation(testValues);
        assertEquals(expected, result);
    }

    /* Testcase [4]: Standard deviation receives an array of 1, 2, 3, 4 and should return 1.224744871391589
    according to the mathematical formula.*/
    @Test
    public void TestStandardDeviationOf1234(){
        int[] testValues = new int[]{1, 2, 3, 4};
        double expected = 1.224744871391589;
        double result = logic.calculateStandardDeviation(testValues);
        assertEquals(expected, result);
    }
}