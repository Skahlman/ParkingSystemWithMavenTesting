import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


//import org.Mockito.mockito;
//new push

public class VolvoTest {

    private Volvo volvoMock;
    private SensorClass sensorMock;
    Volvo car;
    Logic logic;
    private SensorClass realSensor;
    private ParkingAnalyser analyserMock;


    @BeforeEach
    public void init() {
        
        logic = new Logic();
        volvoMock = Mockito.mock(Volvo.class);
        sensorMock = Mockito.spy(SensorClass.class);
        analyserMock = Mockito.spy(ParkingAnalyser.class);
        realSensor = new SensorClass();
        car = new Volvo(sensorMock);


    }

    // ISEMPTY TEST

    @Ignore
    public void TestisEmpty_returnsTrue()
    {
        // Arrange – position is 0 and that metre is free
        car.position = 0;
        car.parking_situation[0] = true;
        // Act – perform the actual work of the test.
        int result = car.isEmpty();
        // Assert – verify the result.
        assertEquals(1, result);
    }

    @Ignore
    public void TestisEmpty_returnsFalse(){
        // Arrange
        car.position = 0;
        car.parking_situation[0] = false;
        // Act
        int result = car.isEmpty();
        // Assert – verify the result.
        assertEquals(0, result);

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
        car.position = 499;
        int expectedPosition = 499;
        //Act
        MoveReturnStruct move = car.MoveForward();
        //Assert
        assertEquals(expectedPosition, move.position()); // car is still standing at 500
         assertEquals(expectedPosition, car.position); // car is still standing at 500

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



    // PARK TESTS
    @Test
    public void TestPark_ParkingSuccessfull_mockito(){
        //Arrange
       // java.util.Arrays.fill(car.parking_situation, true);
        when(sensorMock.readSensor1()).thenReturn(200); 
        when(sensorMock.readSensor2()).thenReturn(200); 
        boolean parked;
        car.isParked = false;
        car.position = 0;
        //car.position = 10;
        //Act
        parked = car.Park();
        //Assert
        assertEquals(true, parked);
    }

    @Test
    public void TestPark_NoFreeParkingSpots_CouldNotPark() {
        java.util.Arrays.fill(car.parking_situation, false );
        boolean result = car.Park();
        assertFalse(result);
    }

    @Test
    public void TestPark_NoFreeParkingSpots_CouldNotPark_mockito() {
       
         // is empty always returns 0 here so that all spaces are occupied
        // this will fill parkung_situation array with false

        when(sensorMock.readSensor1()).thenReturn(0); 
        when(sensorMock.readSensor2()).thenReturn(0); 


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
    public void TestPark_EndOfStreetAndFree5MetersBehind_DontPark() {
        //volvoMock.position = 500;
        car.position = 499;
        for(int i = 495; i < 500; i++)
            car.parking_situation[i] = true;
                                                 
    /* sensors always returns 0 here so that all spaces are free,
    this will fill parkung_situation array with true so that the only reason for the car to not park is that
    the car is at the end of the street and cant move one meter forward */  
        // when(sensorMock.readSensor1()).thenReturn(0); 
        // when(sensorMock.readSensor2()).thenReturn(0); 
                                                                
        boolean park = car.Park();
        assertFalse(park);      // did not park since the car can not go beyond 500 meters and 
                                // it would have had to move 1 meter forward to be able to park
    }

    @Test // rad 92 i
    public void TestPark_EndOFStreet_NoPositionsAvaliable()
    {
        car.position = 499;
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>();
        when(analyserMock.calculateBestParkingSpot(list)).thenReturn(-1);
        boolean result = car.Park();
        assertFalse(result);


    }


    // UNPARK TESTS

    @Test
    public void TestUnPark_CarIsParked_UnParkingSuccessfull(){
        car.isParked = true;
        boolean parked;
        car.UnPark();
        parked = car.isParked();
        assertFalse(parked);
    }

    @Test
    public void TestUnPark_CarNotParked_DoNothing(){
        car.isParked = false; //the car is not parked
        car.UnPark();
        assertFalse(car.isParked);
    }



    //ISEMPTY TEST, TESTING SENSORS NOISY USING MOCKITO
     @Test
    public void Sensor1NoisyReturnAverageOfSensor2(){
        assertNotNull(sensorMock);
        // Arrange
        int sensorValue1 = 100;
        int sensorValue2 = 159;
        int sensorValue3 = 201;
        int sensorValue4 = 134;
        int sensorValue5 = 500;
        int sensorValue6 = 175;
        // Act
        when(sensorMock.readSensor1()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
        when(sensorMock.readSensor2()).thenReturn(sensorValue6);
        // Assert
        assertEquals(175, car.isEmpty());
    }
    
        @Test
    public void Sensor2NoisyReturnAverageOfSensor1(){
        assertNotNull(sensorMock);
        // Arrange
        int sensorValue1 = 100;
        int sensorValue2 = 159;
        int sensorValue3 = 201;
        int sensorValue4 = 134;
        int sensorValue5 = 500;
        int sensorValue6 = 175;
        // Act
        when(sensorMock.readSensor1()).thenReturn(sensorValue6);
        when(sensorMock.readSensor2()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
        // Assert
        assertEquals(175, car.isEmpty());
    }

    @Test
    public void BothSenorNoisy(){
        assertNotNull(sensorMock);
        // Arrange
        int sensorValue1 = 100;
        int sensorValue2 = 159;
        int sensorValue3 = 201;
        int sensorValue4 = 134;
        int sensorValue5 = 500;
        // Act
        when(sensorMock.readSensor1()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
        when(sensorMock.readSensor2()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
        // Assert
        assertThrows(NoSensorWorking.class, () -> car.isEmpty());
    }

// Phase 2
@Test
    public void isEmptyReturnAverageLessThan100() {
        assertNotNull(sensorMock);
        int value1 = 75;
        int value2 = 50;
        // Act
        when(sensorMock.readSensor1()).thenReturn(value1);
        when(sensorMock.readSensor2()).thenReturn(value2);
        assertTrue(car.isEmpty() < 100);
    } 

@Test
public void BothSensorWorkingReturnAverageGreaterThan100() {
    assertNotNull(sensorMock);
    // Arrange
    int value1 = 110;
    int value2 = 175;
    // Act
    when(sensorMock.readSensor1()).thenReturn(value1);
    when(sensorMock.readSensor2()).thenReturn(value2);
    // Assert
    assertTrue(car.isEmpty() > 100);
}


}
