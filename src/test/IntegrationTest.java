import org.junit.Test;
import org.mockito.Mockito;

import net.bytebuddy.asm.Advice.AssignReturned.ToArguments;

import org.mockito.Mockito;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    Volvo car_mock;
    VolvoActuators actuator_mock;
   

    @Before
    public void init(){

        car_mock = Mockito.mock(Volvo.class);
        actuator_mock = Mockito.mock(VolvoActuators.class);

    }

    @Test
    /*  Starts at the beginning of the street,
        Moves along the street and scan the available parking places,
        Moves backwards until the most efficient parking place (the smallest available parking where it can still park safely),
        Parks the car,
        Unparks the car and drive to the end of the street. */
    public void integrationTest1(){

        int start_position = 0;


        boolean[] full_parking_sitation = new boolean[500];

        //fill full_parking_sitation with parking spots
        for(int pos = 0; pos < 500; pos++)
        {
            if(pos >= 5 && pos <= 10) //free parking spot between 5 and 10 meters
                full_parking_sitation[pos] = true;
            else if(pos >= 100 && pos <= 120) //free parking spot between 100 and 120 meters
                full_parking_sitation[pos] = true;
            else if(pos >= 450 && pos <= 456) //free parking spot between 450 and 456 meters
                full_parking_sitation[pos] = true;
        }



        int i = 0;
        Mockito.when(car_mock.MoveForward()).thenReturn(i,full_parking_sitation);
   

    }
}
