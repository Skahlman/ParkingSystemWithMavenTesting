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
//hello emleie test

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
    public void TestMoveForward_SuccessfullyMovesForward() { //test adam move forward
        assertEquals(car.MoveForward().position(),1);
    }
    @Test
    public void TestMoveBackward_SuccessfullyMovesBackward() { //Adams move backward
        car.position = 2;
        assertEquals(car.MoveBackward().position(),1);
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
    public void TestMoveForward_EndOfStreet_CanNotMoveForward(){

        //Arrange
        Volvo car = new Volvo();
        car.position = 500;
        // boolean result = car.parking_situation[car.position];

        //Act
        MoveReturnStruct move = car.MoveForward();
        assertEquals(500, car.position);

    }

    @Test
    public void TestMoveBackward_BeginningOfStreet_CanNotMoveBackward(){

        //Arrange
        //Volvo car = new Volvo();
        car.position = 0;
        // boolean result = car.parking_situation[car.position];

        //Act
        MoveReturnStruct move = car.MoveBackward();
        assertEquals(0, car.position);

    }

    @Test
    public void TestBackward_SuccessfullyMovesBackward(){

        Volvo car = new Volvo();
        int position_integer = car.position;
        position_integer = position_integer -1;
        assertEquals(position_integer, car.MoveBackward());

    }

    // PARK TEST

    @Test
    public void TestPark_ParkingSuccessfull(){

        java.util.Arrays.fill(car.parking_situation, true ); //byt till adams srructs
        //Volvo car = new Volvo();
        boolean parked;
        car.isParked = false;
        car.Park();
        parked = car.isParked();
        assertEquals(parked, true);

    }

    @Test
    public void TestPark_NoFreeParkingSpots_CouldNotPark()
    {
        java.util.Arrays.fill(car.parking_situation, false );
        //when(volvoMock.checkIfFreeParkingSpot()).thenReturn(false); //
        //assertFalse(volvoMock.isParked());
        boolean result = car.Park();

        assertFalse(result);

    }

    //UNPARK TESTS

    @Test
    public void TestUnPark_CarIsParked_UnParkingSuccessfull(){

        Volvo car = new Volvo();
        boolean parked;
        car.UnPark();
        parked = car.isParked();
        assertEquals(parked, false);

    }

    @Test
    public void TestUnPark_CarNotParked_DoNothing(){

        car.isParked = false; //the car is not parked
        car.UnPark();
        assertFalse(car.isParked);


    }
}
