 
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mockito;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

 public class ParkingAnalyserTest{
 
    VolvoActuators actuator_mock;
    Volvo car;
    private SensorClass sensorMock;

    @Before
    public void init(){

        actuator_mock = Mockito.mock(VolvoActuators.class);
        sensorMock = Mockito.spy(SensorClass.class);
        car = new Volvo(sensorMock);

    }

 }
 
