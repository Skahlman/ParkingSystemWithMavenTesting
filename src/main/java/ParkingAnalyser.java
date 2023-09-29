import java.util.ArrayList;

public class ParkingAnalyser {

    public ArrayList<EndOfParkingPlaceStruct> parkingSpots(boolean[] parking_situation)
    {
        ArrayList<EndOfParkingPlaceStruct> list = new ArrayList<EndOfParkingPlaceStruct>();
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

        return list;
    }

    public int calculateBestParkingSpot(ArrayList<EndOfParkingPlaceStruct> list)
    {
        if(list.isEmpty()) // if list is empty
            return -1;
        
        int min_parkingspot_length = list.get(0).length;
        int min_parkingspot_position = list.get(0).position;

        for(EndOfParkingPlaceStruct obj : list)
        {
            if(obj.length <= min_parkingspot_length && obj.position < 499)  //  if the current parking spot is smaller or equal to the minimim parking spot so far
            {                                                               //  and if it is not at the end of the street
                min_parkingspot_length =  obj.length;
                min_parkingspot_position = obj.position;
            }
        }
        return min_parkingspot_position;
    }

    /* */
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
