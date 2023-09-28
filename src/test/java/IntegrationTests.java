import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Before;
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

        analyser_mock = Mockito.mock(ParkingAnalyser.class);
        actuator_mock = Mockito.mock(VolvoActuators.class);
        sensor_mock = Mockito.mock(SensorClass.class);
        car = new Volvo(sensor_mock);
        analyser = new ParkingAnalyser();

    }

    @Test
    /*  Starts at the beginning of the street,
        Moves along the street and scan the available parking places,
        Moves backwards until the most efficient parking place (the smallest available parking where it can still park safely),
        Parks the car,
        Unparks the car and drive to the end of the street. */
    public void integrationTest1(){

        car.position = 0; //Starts at the beginning of the street
        int length = car.parking_situation.length;
      
        /* scans the whole street for avaliable parking spots. 
         * the values from the sensors are mocked to indicate free spots at position 5-9 and position 100-119 and position 300-310.
         */
        for(int i = 0; i < length; i++) 
        {
            Mockito.when(actuator_mock.insideLimits(i,true)).thenReturn(true); // actuators should always say its okay to move forward here

        
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
        assertEquals(expected_position, car.position); //check if the car is at position 5

        car.UnPark(); //unparks the car

        assertFalse(car.isParked); //check is car unparked successfully


        while(car.position <499)
            car.MoveForward(); //moves the car to the end of the street

        assertEquals(499,car.position); //check if car is at the end of the street



    }

    @Test
    /* starts at beginning of street
       
     * sensor 1 breaks down at position 250, there has not been any free parking spots up until now
     * there are one free parking spot right after 250, the car parks there.
     * 
     * 
     */
    public void integrationTest2()
    {
         car.position = 0; //Starts at the beginning of the street
        int length = car.parking_situation.length;
      
        //sensor values for broken sensor:
        int sensorValue1 = 100;
        int sensorValue2 = 159;
        int sensorValue3 = 201;
        int sensorValue4 = 134;
        int sensorValue5 = 500;

        for(int i = 0; i < length; i++) 
        {
            Mockito.when(actuator_mock.insideLimits(i,true)).thenReturn(true); // actuators should always say its okay to move forward here

        
            if(i == 250) //sensor 1 should break
            {
                when(sensor_mock.readSensor2()).thenReturn(sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5);
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
        assertEquals(expected_position, car.position);


    }
}