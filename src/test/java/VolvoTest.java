import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//import org.Mockito.mockito;

public class VolvoTest {
    private Volvo volvoMock;
    Volvo car;
    @BeforeEach
    public void init() {
        car = new Volvo();
        volvoMock = Mockito.spy(Volvo.class);
    }

    /* Phase 2
    @Test
    public void isEmptySensorReadingsReturn75() {
        assertNotNull(volvoMock);
        double [] sensorvalues = {75, 75};
        when(volvoMock.sensorReadings()).thenReturn(sensorvalues);
        assertFalse(volvoMock.isEmpty());
    }
    @Test
    public void isEmptySensorReadingsReturn74() {
        init();
        assertNotNull(volvoMock);
        double [] sensorvalues = {74, 74};
        when(volvoMock.sensorReadings()).thenReturn(sensorvalues);
        assertTrue(volvoMock.isEmpty());
    }
     */



    @Test
    public void isEmptyTest_returnsEmpty()
    {
        //Arrange – setup the testing objects and prepare the prerequisites for your test.
        Volvo car = new Volvo();
        car.setSensors(0);

        //Act – perform the actual work of the test.
        Boolean result = car.isEmpty();

        // Assert – verify the result.
        assertEquals(result,true);
    }


    @Test
    public void TestwhereIsPosition0andNotParked(){

        Volvo car = new Volvo();
        Map<Integer, Boolean> expectedMap = Map.of(1, false);
        assertEquals(expectedMap, car.whereIs());
        //int position_integer = car.position;
        //assertEquals("The position of the car is: " + position_integer, car.WhereIs());

    }
    @Test
    public void TestAdamsMoveForward() {
        assertEquals(car.AdamsMoveForward().position(),1);
    }
    @Test
    public void TestAdamsMoveBackward() {
        car.position = 2;
        assertEquals(car.AdamMoveBackwards().position(),1);
    }
    //Old test using maps
    /*
    @Test
    public void TestAdamMoveBackwards() {
        boolean[] expectedSituation = new boolean[500];
        expectedSituation[0] = false;
        Map<Integer, boolean[]> expectedMap = Map.of(1,expectedSituation);
        assertEquals(car.AdamsMoveForward(), expectedMap);
    }*/

    @Test
    public void TestMoveForward(){

        Volvo car = new Volvo();
        int position_integer = car.position;
        position_integer = position_integer + 1;
        assertEquals(position_integer, car.MoveForward());

    }

    //@Test
    public void TestMoveForward_EndOfStreet(){

        //Arrange
        Volvo car = new Volvo();
        car.position = 500;
        // boolean result = car.parking_situation[car.position];

        //Act
        boolean[] bool = car.MoveForward();
        assertEquals(500, car.position);

    }

    @Test
    public void TestMoveBackward_BeginningOfStreet(){

        //Arrange
        //Volvo car = new Volvo();
        car.position = 0;
        // boolean result = car.parking_situation[car.position];

        //Act
        int test = car.MoveBackward();
        assertEquals(0, car.position);

    }

    @Test
    public void TestBackward(){

        Volvo car = new Volvo();
        int position_integer = car.position;
        position_integer = position_integer -1;
        assertEquals(position_integer, car.MoveBackward());

    }

    @Test
    public void TestPark(){

        Volvo car = new Volvo();
        boolean parked;
        car.Park();
        parked = car.isParked();
        assertEquals(parked, true);

    }

    @Test
    public void TestPark_NoFreeParkingSpots()
    {
        java.util.Arrays.fill(car.parking_situation, false );
        //when(volvoMock.checkIfFreeParkingSpot()).thenReturn(false); //
        //assertFalse(volvoMock.isParked());
        boolean result = car.Park();

        assertFalse(result);

    }

    @Test
    public void TestUnPark(){

        Volvo car = new Volvo();
        boolean parked;
        car.UnPark();
        parked = car.isParked();
        assertEquals(parked, false);

    }
}
