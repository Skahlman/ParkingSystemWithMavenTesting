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

    // ISEMPTY TEST

    @Test
    public void TestisEmpty_returnsTrue()
    {
        // Arrange – position is 0 and that metre is free
        car.position = 0;
        car.parking_situation[0] = true;
        // Act – perform the actual work of the test.
        Boolean result = car.isEmpty();
        // Assert – verify the result.
        assertEquals(true, result);
    }

    @Test
    public void TestisEmpty_returnsFalse(){
        // Arrange
        car.position = 0;
        car.parking_situation[0] = false;
        // Act
        Boolean result = car.isEmpty();
        // Assert – verify the result.
        assertEquals(false, result);

    }

    // WHEREIS TEST
    @Test
    public void TestwhereIs_Position0andNotParked(){ //tests start position for whereIs
        // Arrange
        whereIsReturnStruct start_whereIs = new whereIsReturnStruct(0,false);
        whereIsReturnStruct actual;
        // Act
        actual = car.whereIs();
        // Assert
        assertEquals(start_whereIs, actual);

    }


    // MOVEFORWARD TESTS
    @Test
    public void TestMoveForward_SuccessfullyMovesForward() { //test adam move forward
        //Arrange
        int expectedPosition = 1;
        //Act
        car.MoveForward();
        //Assert
        assertEquals(expectedPosition, car.position);
    }
   

    @Test
    public void TestMoveForward_EndOfStreet_CanNotMoveForward(){
        //Arrange
        car.position = 500;
        int expectedPosition = 500;
        //Act
        MoveReturnStruct move = car.MoveForward();
        //Assert
        assertEquals(expectedPosition, move.position()); // car is still standing at 500

    }

    @Test
    public void TestMoveForward_CarIsParked_DontMoveForward(){
        //Arrange
        car.isParked = true;
        int expectedCarPosition = 0;
        //Act
        int resultPosition = car.MoveForward().position();
        //Assert
        assertEquals(expectedCarPosition, resultPosition);

    }

    // MOVE BACKWARD TEST

    @Test
    public void TestMoveBackward_BeginningOfStreet_CanNotMoveBackward(){
        //Arrange
        car.position = 0;
        int expectedPosition = 0;
        int resultPosition;
        //Act
        resultPosition = car.MoveBackward().position();
        //Assert
        assertEquals(expectedPosition,resultPosition);

    }

     @Test
    public void TestMoveBackward_SuccessfullyMovesBackward() { //Adams move backward
        car.position = 2;
        assertEquals(car.MoveBackward().position(),1);
    }

    // @Test
    // public void TestBackward_SuccessfullyMovesBackward(){

    //     Volvo car = new Volvo();
    //     int position_integer = car.position;
    //     position_integer = position_integer -1;
    //     assertEquals(position_integer, car.MoveBackward());

    // }

        //Old test using maps
    /*
    @Test
    public void TestAdamMoveBackwards() {
        boolean[] expectedSituation = new boolean[500];
        expectedSituation[0] = false;
        Map<Integer, boolean[]> expectedMap = Map.of(1,expectedSituation);
        assertEquals(car.AdamsMoveForward(), expectedMap);
    }*/

    // PARK TESTS

    @Test
    public void TestPark_ParkingSuccessfull(){
        //Arrange
        java.util.Arrays.fill(car.parking_situation, true);
        boolean parked;
        car.isParked = false;
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

    @Test
    public void TestPark_AlreadyParked_DontPark(){
        car.isParked = true;
        boolean park = car.Park();
        assertFalse(park); //did not park successfully since it is already parked
        
    }

    @Test
    public void TestPark_EndOfStreetAndFree5MetersBehind_DontPark()
    {
        car.position = 500;
        java.util.Arrays.fill(car.parking_situation, true);
        boolean park = car.Park();
        assertFalse(park);      // did not park since the car can not go beyond 500 meters and 
                                // it would have had to move 1 meter forward to be able to park
    }


    // UNPARK TESTS

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

}
