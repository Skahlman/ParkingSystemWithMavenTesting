import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class VolvoTest {

    private Volvo volvoMock;
    private SensorClass sensorMock;
    Volvo car;
    LogicClass logic;
    private SensorClass realSensor;
    private ParkingAnalyser analyserMock;
    private VolvoActuators actuator;


    @BeforeEach
    public void init() {
        
        logic = new LogicClass();
        volvoMock = Mockito.mock(Volvo.class);
        sensorMock = Mockito.spy(SensorClass.class);
        analyserMock = Mockito.spy(ParkingAnalyser.class);
        realSensor = new SensorClass();
        car = new Volvo(sensorMock);
        actuator = new VolvoActuators();


    }

    // WHEREIS TEST

    @Test /*Testcase [1]: test if whereIs returns the right values.*/
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

    @Test /*Testcase [2]: Test if car moves forward from position 0 to 1 */
    public void TestMoveForward_SuccessfullyMovesForward() { //test adam move forward
        //Arrange
        int expectedPosition = 1;
        //Act
        car.MoveForward();
        //Assert
        assertEquals(expectedPosition, car.actuator.position);
    }
   

    @Test /*Testcase [3]: The car is at the end of the street and should not be able to move forward */
    public void TestMoveForward_EndOfStreet_CanNotMoveForward(){
        //Arrange
        car.actuator.position = 499;
        int expectedPosition = 499;
        //Act
        MoveReturnStruct move = car.MoveForward();
        //Assert
        assertEquals(expectedPosition, move.position()); // car is still standing at 500
         assertEquals(expectedPosition, car.actuator.position); // car is still standing at 500

    }

    @Test /*Testcase [4]: The car is parked and should not be able to move forward */
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

    @Test /*Testcase [5]: The car is at position 0 of the street abd should not be able to move backward.*/
    public void TestMoveBackward_BeginningOfStreet_CanNotMoveBackward(){
        //Arrange
        car.actuator.position = 0;
        int expectedPosition = 0;
        int resultPosition;
        //Act
        resultPosition = car.MoveBackward().position();
        //Assert
        assertEquals(expectedPosition,resultPosition);

    }

     @Test /*Testcase [6]: The car has space behind and is able to move backwards */
    public void TestMoveBackward_SuccessfullyMovesBackward() {
        car.actuator.position = 2;
        car.MoveBackward();
        assertEquals(1, car.actuator.position);
    }

    // PARK TESTS

    @Test /*Testcase [7]: Free parking spot at beginning of street, car parks sucessfully */
    public void TestPark_ParkingSuccessfull_mockito(){
        //Arrange
        when(sensorMock.readSensor1()).thenReturn(200); 
        when(sensorMock.readSensor2()).thenReturn(200); 
        boolean parked;
        car.isParked = false;
        car.actuator.position = 0;
        //Act
        parked = car.Park();
        //Assert
        assertEquals(true, parked);
    }

    @Ignore //old park test, Testcase [8] has updated mockito version
    public void TestPark_NoFreeParkingSpots_CouldNotPark() {
        java.util.Arrays.fill(car.parking_situation, false );
        boolean result = car.Park();
        assertFalse(result);
    }

    @Test /* Testcase [8]: No parking spots avaliable, should not park. */
    public void TestPark_NoFreeParkingSpots_CouldNotPark_mockito() {
         // is empty always returns 0 here so that all spaces are occupied
        // this will fill parkung_situation array with false
        when(sensorMock.readSensor1()).thenReturn(0); 
        when(sensorMock.readSensor2()).thenReturn(0); 

        boolean result = car.Park();

        assertFalse(result);
    }

    @Test /* Testcase [9]: car is parked already, and can not park again. */
    public void TestPark_AlreadyParked_DontPark(){
        car.isParked = true;
        boolean park = car.Park();
        assertFalse(park); //did not park successfully since it is already parked
    }



    @Test
    /* Testcase [10]: There is a 5-meter free parking spot at the very end of the street,
        but the car should not park there because the car needs to be able to move one meter
        in front of the parking spot. There is no space for that, the car should not park.
        This testcase is partly fulfilled inside the function calculateBestParkingSpot() inside
        ParkingAnalyser class.
     */
    public void TestPark_EndOfStreetAndFree5MetersBehind_DontPark() {

        car.actuator.position = 499;
        for(int i = 495; i < 500; i++)
            car.parking_situation[i] = true;
                                                                
        boolean park = car.Park();
        assertFalse(park);      // did not park since the car can not go beyond 500 meters and 
                                // it would have had to move 1 meter forward to be able to park
    }

    @Test /* mockito version of Testcase [8]. No free parking spots, don't park. */
    public void TestPark_EndOFStreet_NoPositionsAvaliable_mockito()
    {
        car.actuator.position = 499;
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>();
        when(analyserMock.calculateBestParkingSpot(list)).thenReturn(-1);
        boolean result = car.Park();
        assertFalse(result);

    }


    // UNPARK TESTS

    @Test /* Testcase [11]: The car is parked, and the car unparks successfully */
    public void TestUnPark_CarIsParked_UnParkingSuccessfull(){
        car.isParked = true;
        boolean parked;
        car.UnPark();
        parked = car.isParked();
        assertFalse(parked);
    }

    @Test /* Testcase [12]: the car is not parked already, return false eitherway */
    public void TestUnPark_CarNotParked_DoNothing(){
        car.isParked = false; //the car is not parked
        car.UnPark();
        assertFalse(car.isParked);
    }

    //ISEMPTY TEST, TESTING SENSORS NOISY USING MOCKITO

    /*  Testcase [13]: Sensor 1 is noisy, therefore the values from Sensor 2 are
        the only ones that should be used.
    */
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

    /* Testcase [14]: Sensor 2 is noisy, therefore the values from Sensor 1 are
        the only ones that should be used. */
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

    /*  Testcase [15]: Both sensors are noisy, therefore no sensors can be used.
        An exceptions should be thrown. */
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


    /*  Testcase [16]: isEmpty should return and average < 100.
    *   This means that the space is occupied */
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

    /*  Testcase [17]: isEmpty should return and average > 100.
     *   This means that the space is free */
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

    /* Testcase [18]: Sensor 2 was broken but worked 5 times after it broke.
    * Therefore sensor 2's values should be usable again. */
    @Test
    public void Sensor2BreakDownedGenerate5GoodValues(){
        assertNotNull(sensorMock);
        car.sensor2working = false;
        car.sensor2WorkingCounter = 4;
        when(sensorMock.readSensor2()).thenReturn(50);
        car.isEmpty();
        assertTrue(car.sensor2working);
    }


}
