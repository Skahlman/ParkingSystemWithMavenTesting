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
        //Arrange – position is 0 and that metre is free
        car.position = 0;
        car.parking_situation[0] = true;
        //Act – perform the actual work of the test.
        Boolean result = car.isEmpty();

        // Assert – verify the result.
        assertEquals(true,result);
    }

    @Test
    public void TestisEmpty_returnsFalse(){
        car.position = 0;
        car.parking_situation[0] = false;

        Boolean result = car.isEmpty();
        // Assert – verify the result.
        assertEquals(false,result);

    }

    // WHEREIS TEST
    @Test
    public void TestwhereIs_Position0andNotParked(){ //tests start position for whereIs

        whereIsReturnStruct start_whereIs = new whereIsReturnStruct(0,false);
        assertEquals(start_whereIs,car.whereIs());

    }


    // MOVEFORWARD TESTS
    @Test
    public void TestMoveForward_SuccessfullyMovesForward() { //test adam move forward
        assertEquals(car.MoveForward().position(),1);
    }
   

    @Test
    public void TestMoveForward_MovesForward_IncrementOfPositionSuccessfull(){ 

        Volvo car = new Volvo();
        int position_integer = car.position;
        position_integer = position_integer + 1;
        assertEquals(position_integer, car.MoveForward().position());

    }

    @Test
    public void TestMoveForward_EndOfStreet_CanNotMoveForward(){

        //Arrange
       // Volvo car = new Volvo();
        car.position = 500;

        //Act
        MoveReturnStruct move = car.MoveForward();
        

        assertEquals(500, move.position()); // car is still standing at 500

    }

    @Test
    public void TestMoveForward_CarIsParked_DontMoveForward(){
       
        car.isParked = true;
        car.position = 0;
        MoveReturnStruct move = car.MoveForward();

        assertEquals(0,car.position);

    }

    // MOVE BACKWARD TEST

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

        java.util.Arrays.fill(car.parking_situation, true ); 
        //car.position = 10;
        //Volvo car = new Volvo();
        boolean parked;
        car.isParked = false;
        boolean park =   car.Park();
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
