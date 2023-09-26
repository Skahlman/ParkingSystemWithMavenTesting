import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;
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
      
        /* scans the whole street for avaliable parking spots. 
         * the values from the sensors are mocked to indicate free spots at position 5-9 and position 100-119 and position 300-310.
         */
        for(int i = 0; i < 499; i++) 
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

            car.MoveForward(); 
        }

        car.Park(); // Parks the car, should park at position 5-9
        int expected_position = 5;
        assertEquals(expected_position, car.position); //check if the car is at position

        car.UnPark(); //unparks the car

        assertFalse(car.isParked); //check is car unparked successfully


        while(car.position <499)
            car.MoveForward(); //moves the car to the end of the street

        assertEquals(499,car.position); //check if car is at the end of the street



    }
}