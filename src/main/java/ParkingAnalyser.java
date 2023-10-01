import java.util.ArrayList;

public class ParkingAnalyser implements ParkingspotAnalyser {
     /* Testcases for ParkingAnalyser.java class are located in ParkingAnalyserTest.java and are
        numbered [1], [2],... [n].
     */
    @Override
    public ArrayList<EndOfParkingPlaceStruct> parkingSpots(boolean[] parking_situation)
    {
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>();

        if(parking_situation == null) //Fulfills testcase [5]
            return list;

        int length = 0;
        int min_parking_length = 5;

        for(int pos = 0; pos < parking_situation.length-1; pos++)
        {
            if(parking_situation[pos]) //if that position is free, add to the length +1
            {
                length++;
            }   
            else if(length > 0 && !parking_situation[pos]) // if there was an end to a parking spot
            {
                /*  if the length is long enough to be a parking spot, store end end-position
                    of that parking spot along with the length of the parking spot in list*/
                if(length >= min_parking_length) 
                {
                    list.add(new EndOfParkingPlaceStruct(pos-1, length)); 
                }

                length = 0; // reset the length
            }
            
        }

        return list; //Testcase [2] covers the whole function
    }

    @Override
    public int calculateBestParkingSpot(ArrayList<EndOfParkingPlaceStruct> list)
    {
        if(list.isEmpty()) // Fulfills Testcase [4]: if list is empty
            return -1;
        
        int min_parkingspot_length = list.get(0).length;
        int min_parkingspot_position = list.get(0).position;

        for(EndOfParkingPlaceStruct obj : list)
        {
            /*  if the current parking spot is smaller or equal to the minimim parking spot so far
             and if the spot is not at the end of the street <-- Fulfills testcase[10] inside VolvoTest.java
             This is also tested in testcase [3] inside ParkingAnalyserTest.
             */

            if(obj.length <= min_parkingspot_length && obj.position < 499)
            {
                min_parkingspot_length =  obj.length;
                min_parkingspot_position = obj.position;
            }
        }
        return min_parkingspot_position;
    }

    @Override
    public boolean checkIfFreeParkingSpot(int position, boolean[] parking_situation)
    {
        if(position < 5)
            return false;
        for(int i = position; i > position-5; i--) //check previous 5 meters from the position
        {
            if(!parking_situation[i]) // if one of the 5 meters is occupied, return false
                return false;
        }

        return true; // there is a free parking spot
    }
}
