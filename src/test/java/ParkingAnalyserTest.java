import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mockito;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

 public class ParkingAnalyserTest{
 
    private ParkingAnalyser analyser;

    @Before
    public void init(){
       analyser = new ParkingAnalyser();
    }

    // PARKINGSPOTS TESTS

     /*Testcase [1]: No found parking spots, parkingSpots() should return an empty array */
    @Test 
    public void TestparkingSpots_NoAvaliableParkingSpots()
    {
        boolean[] full_parking_sitation = new boolean[500];

        int expected_length = 0;

        ArrayList<EndOfParkingPlaceStruct> list = analyser.parkingSpots(full_parking_sitation);
        int actual_length = list.size();

        assertEquals(expected_length,actual_length);

    }

    /*Testcase [2]: There are three avalibale parking spots with different lengths, this
    * test checks that the list returns the correct length and position */
    @Test
    public void TestparkingSpots_ThreeAvaliableParkingSpots_listReturnsCorrectValues()
    {
           boolean[] full_parking_sitation = new boolean[500];

        //fill full_parking_sitation with parking spots
        for(int pos = 0; pos < 500; pos++)
        {
            if(pos >= 5 && pos <= 9) //free parking spot between 5 and 10 meters (a)
                full_parking_sitation[pos] = true;
            else if(pos >= 100 && pos <= 119) //free parking spot between 100 and 120 meters (b)
                full_parking_sitation[pos] = true;
            else if(pos >= 450 && pos <= 455) //free parking spot between 450 and 456 meters (c)
                full_parking_sitation[pos] = true;
        }

        ArrayList<EndOfParkingPlaceStruct> list = analyser.parkingSpots(full_parking_sitation);
        EndOfParkingPlaceStruct wanted_a = new EndOfParkingPlaceStruct(9,5 ); // parking spot (a)
        EndOfParkingPlaceStruct wanted_b = new EndOfParkingPlaceStruct(119, 20); // parking spot (b)
        EndOfParkingPlaceStruct wanted_c = new EndOfParkingPlaceStruct(455, 6); // parking spot(c)
        
        assertEquals(wanted_a.position,list.get(0).position);
        assertEquals(wanted_a.length,list.get(0).length);

        assertEquals(wanted_b.position,list.get(1).position);
        assertEquals(wanted_b.length,list.get(1).length);

        assertEquals(wanted_c.position,list.get(2).position);
        assertEquals(wanted_c.length,list.get(2).length);
    }

    // CALCULATEBESTPARKINGSPOT TEST

     /* Testcase [3]: There are three free parking spots avaliable, and
     * calculateBestParingSpot() should return the parking spot at position 9.
     * The parking spot as position 455 has the same length, but it should
     * not return that one since it is at the very end of the street. */
    @Test
    public void TestcalculateBestParkingSpot_returnPosition9()
    {
        
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>();
        EndOfParkingPlaceStruct a = new EndOfParkingPlaceStruct(9,5 ); // parking spot (a)
        EndOfParkingPlaceStruct b = new EndOfParkingPlaceStruct(119, 20); // parking spot (b)
        EndOfParkingPlaceStruct c = new EndOfParkingPlaceStruct(455, 5); // parking spot(c)
        list.add(a);
        list.add(b);
        list.add(c);

        int expected = 455;
        int result = analyser.calculateBestParkingSpot(list);
        
        assertEquals(expected, result);
    }

    /*Testcase [4]: calculateBestParkingSpot() receives an empty list, and returns -1. */
    @Test
    public void TestcalculateBestParkingSpot_emptyList_returnMinusOne()
    {
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>(); // no added items to the list
        int result = analyser.calculateBestParkingSpot(list);
        assertEquals(-1,result);
    }





 }
 
