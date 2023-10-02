import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class IntegrationTests {

    Volvo car;
    VolvoActuators actuator_mock;
    SensorClass sensor_mock;
    ParkingAnalyser analyser_mock;
    ParkingAnalyser analyser;

    @Before
    public void init(){
        analyser_mock = Mockito.spy(ParkingAnalyser.class);
        actuator_mock = Mockito.spy(VolvoActuators.class);
        sensor_mock = Mockito.mock(SensorClass.class);
        car = new Volvo(sensor_mock,actuator_mock, analyser_mock);
        analyser = new ParkingAnalyser();
    }

    @Test
    /*  Starts at the beginning of the street,
        Moves along the street and scan the available parking places,
        Moves backwards until the most efficient parking place (the smallest available parking where it can still park safely),
        Parks the car,
        Unparks the car and drive to the end of the street. */
    public void integrationTest1(){

        actuator_mock.position = 0; //Starts at the beginning of the street
        int length = car.parking_situation.length;
      
        /* scans the whole street for avaliable parking spots. 
         * the values from the sensors are mocked to indicate free spots at position 5-9 and position 100-119 and position 300-310.
         */
        //Mockito.when(actuator_mock.moveIfAllowed(true)).thenReturn(true); // actuators should always say its okay to move forward here
        //Mockito.when(actuator_mock.moveIfAllowed(true)).thenCallRealMethod();
        for(int i = 0; i < length; i++) 
        {
            if(i >= 5 && i <= 9 || i >= 100 && i <= 119 || i >= 300 && i <= 310 ) //free parking spot between 5 and 9 meters, 100 and 119 meters, 300 and 310 meters
            {
                Mockito.when(sensor_mock.readSensor1()).thenReturn(150); 
                Mockito.when(sensor_mock.readSensor2()).thenReturn(150); 
            }
            else //both sensors saying it is occupied
            {
                Mockito.when(sensor_mock.readSensor1()).thenReturn(10); 
                Mockito.when(sensor_mock.readSensor2()).thenReturn(10); 
            }

            car.MoveForward(); //move forward until it reaches end of the street
        }

        car.Park(); // Parks the car, should park at position 5-9
        assertTrue(car.isParked);
        int expected_position = 5;
        assertEquals(expected_position,  car.actuator.position); //check if the car is at position 5

        car.UnPark(); //unparks the car

        assertFalse(car.isParked); //check is car unparked successfully


        while( car.actuator.position <499)
            car.MoveForward(); //moves the car to the end of the street

        assertEquals(499, car.actuator.position); //check if car is at the end of the street

    }

    @Test
    /* starts at beginning of street
       
     * sensor 1 breaks down at position 250, there has not been any free parking spots up until now
     * there are one free parking spot right after 250, the car parks there.
     * then, it unparks and moves forward.
        after 20 meters both sensors breaks down
        the park function should not work after this
     * 
     */
    public void integrationTest2()
    {
        car.actuator.position = 0; //Starts at the beginning of the street
        int length = car.parking_situation.length;
      
        //sensor values for broken sensor:
        int sensorValue1 = 100;
        int sensorValue2 = 159;
        int sensorValue3 = 201;
        int sensorValue4 = 134;
        int sensorValue5 = 500;

        //Mockito.when(actuator_mock.moveIfAllowed(true)).thenReturn(true); // actuators should always say its okay to move forward here

        for(int i = 0; i < length; i++) 
        {
            if(i == 250) //sensor 1 should break
            {
                when(sensor_mock.readSensor1()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
                Mockito.when(sensor_mock.readSensor2()).thenReturn(150); 
                assertEquals(150, car.isEmpty());
                car.MoveForward(); //move forward
            }
            else if (i < 250) //all parking spots are occupied
            {
                Mockito.when(sensor_mock.readSensor1()).thenReturn(150); 
                Mockito.when(sensor_mock.readSensor2()).thenReturn(150); 
                car.MoveForward(); //move forward
            }
            else if(i > 250 ) //parking spots are avaliable
            {
                Mockito.when(sensor_mock.readSensor1()).thenReturn(10); 
                Mockito.when(sensor_mock.readSensor2()).thenReturn(10); 
                car.Park();
            }
            if(car.isParked)
                break;
            
        }

        int expected_position = 251; //the car is expected to park right after the sensor broke down
        assertEquals(expected_position,  car.actuator.position); //check if car parked at the position right after sensor1 broke down
        assertTrue(car.isParked); // doublecheck that it really is parked

        car.UnPark(); //unparks the car

        for(int i = 0; i < 20; i++)
        {
            car.MoveForward(); //move forward 20 steps
        }

        //both sensors should stop working here
        when(sensor_mock.readSensor2()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
        when(sensor_mock.readSensor1()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);

        Throwable exception = assertThrows(
                NoSensorWorking.class, () -> {
                   car.Park(); //try to park the car
                }
        );

        assertEquals("No sensor working, You are on your own", exception.getMessage());

    }

    @Test
    /* The street has three parking places of mutually different sizes, one is not enough for safe parking and
    the other two enough for parking. Moreover, one of the sensors break down
    halfway in the middle of the scenario (i.e., when the car has reached the middle
    of the street while moving forward) so that it constantly produces recognizably
    out-of-bound values. The Parking System should still work since the other sensor is still functioning.
    At the end, the car parks the car. */
    public void integrationTest3()
    {
        car.actuator.position = 0; //Starts at the beginning of the street
        int length = car.parking_situation.length;

        /* scans the whole street for avaliable parking spots.
         * the values from the sensors are mocked to indicate free spots at position 5-9 and position 260-266.
         */
        //Mockito.when(actuator_mock.moveIfAllowed(true)).thenReturn(true); // actuators should always say its okay to move forward here
        for(int i = 0; i < length; i++)
        {
            if(i >= 5 && i <= 9 || i >= 260 && i <= 266 || i >= 300 && i <= 303 ) //free parking spot between 5 and 9 meters, 100 and 119 meters. too small parking spot between 300 and 303
            {
                if(i >= 250) //  sensor 1 should break down
                {
                    Mockito.when(sensor_mock.readSensor1()).thenReturn(500); // broken sensor
                    Mockito.when(sensor_mock.readSensor2()).thenReturn(150); // good sensor
                }
                else
                {
                    Mockito.when(sensor_mock.readSensor1()).thenReturn(150);
                    Mockito.when(sensor_mock.readSensor2()).thenReturn(150);
                }
            }
            else //both sensors saying it is occupied
            {
                if(i >= 250) //  sensor 1 should break down
                {
                    Mockito.when(sensor_mock.readSensor1()).thenReturn(500); // broken sensor
                    Mockito.when(sensor_mock.readSensor2()).thenReturn(150); // good sensor
                }
                else
                {
                    Mockito.when(sensor_mock.readSensor1()).thenReturn(10);
                    Mockito.when(sensor_mock.readSensor2()).thenReturn(10);
                }
            }
            car.MoveForward(); //move forward until it reaches end of the street
        }

        car.Park(); // park the car

        int expected_position = 5;

        assertEquals(expected_position,  car.actuator.position); //check if the car is at position 5, (the best parking spot)

    }

    @Test
    /* The car is at the end of the street. the list with parking spots is not empty, but the calculateBestParkingSpot
    function returns a negative value (a position below 0). therefore the car did not park, and car.Park() returned false.
     */
    public void integrationTest4_achievesFullTestCoverage()
    {
        actuator_mock.position = 499;
        Arrays.fill(car.parking_situation, true);
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>(); // no added items to the list
        EndOfParkingPlaceStruct a = new EndOfParkingPlaceStruct(0,0 ); // parking spot (a)
        list.add(a); // add 'a' to the list so it does not say that the list is empty
        Mockito.when(analyser_mock.parkingSpots(car.parking_situation)).thenReturn(list);
        Mockito.when(analyser_mock.calculateBestParkingSpot(list)).thenReturn(-1);

        assertFalse(car.Park());
    }
}