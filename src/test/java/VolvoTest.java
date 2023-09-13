import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//import org.Mockito.mockito;

public class VolvoTest {
    private Volvo volvoMock;
    @Before
    public void init() {
        volvoMock = Mockito.mock((Volvo.class));

    }


    @Test
    public void isEmptyTest_BothSensorWorking() {
        int sensorReading = 50;
        when(volvoMock.readSensor()).thenReturn(sensorReading);

        //Act – perform the actual work of the test.


        // Assert – verify the result.
        assertEquals(result,false);
    }

    public void isEmptyTest_returnsNotEmpty()
    {
        //Arrange – setup the testing objects and prepare the prerequisites for your test.
        Volvo car = new Volvo();
        car.setSensors(200);


        //Act – perform the actual work of the test.
        Boolean result = car.isEmpty();

        // Assert – verify the result.
        assertEquals(result,false);
    }



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
    public void isEmptyTest_NoisySensors_returnsError(){

        //Volvo car = mock(Volvo.class);
    }


    @Test
    public void TestwhereIs(){

        Volvo car = new Volvo();
        int position_integer = car.position;
        assertEquals("The position of the car is: " + position_integer, car.WhereIs());

    }

    @Test
    public void TestMoveForward(){

        Volvo car = new Volvo();
        int position_integer = car.position;
        position_integer = position_integer + 1;
        assertEquals(position_integer, car.MoveForward());

    }

    @Test
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
        Volvo car = new Volvo();
        car.position = 0;
        // boolean result = car.parking_situation[car.position];

        //Act
        int test = car.MoveBackward();
        assertEquals(500, car.position);

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
    public void TestUnPark(){

        Volvo car = new Volvo();
        boolean parked;
        car.UnPark();
        parked = car.isParked();
        assertEquals(parked, false);

    }
}
