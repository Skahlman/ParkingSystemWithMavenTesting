//import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//import org.Mockito.mockito;

public class VolvoTest {

    private Volvo volvoMock;
    Volvo car;
    SensorClass sensorMock;
    Logic logic = new Logic();

    @BeforeEach
    public void init() {
        volvoMock = Mockito.mock(Volvo.class);
        sensorMock = Mockito.mock(SensorClass.class);
        car = new Volvo(sensorMock, logic);
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



    // PARK TESTS

    @Test
    public void TestPark_ParkingSuccessfull(){
        //Arrange
        java.util.Arrays.fill(car.parking_situation, true);
        boolean parked;
        car.isParked = false;
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
    public void TestPark_AlreadyParked_DontPark(){
        car.isParked = true;
        boolean park = car.Park();
        assertFalse(park); //did not park successfully since it is already parked
    }

    @Test
    public void TestPark_EndOfStreetAndFree5MetersBehind_DontPark() {
        car.position = 500;
        java.util.Arrays.fill(car.parking_situation, true);
        boolean park = car.Park();
        assertFalse(park);      // did not park since the car can not go beyond 500 meters and 
                                // it would have had to move 1 meter forward to be able to park
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


    // Phase 2
    @Test
    public void isEmptyReturnAverageLessThan100() {
        assertNotNull(sensorMock);
        int[] sensorValues = {50,50};
        when(sensorMock.readSensor()).thenReturn(sensorValues);
        assertTrue(car.isEmpty2() < 100);
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
        assertTrue(car.isEmpty2() > 100);
    }
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
        assertEquals(175, car.isEmpty2());
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
        assertEquals(175, car.isEmpty2());
    }
    @Test ()
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
        assertThrows(NoSensorWorking.class, () -> car.isEmpty2());
    }
}