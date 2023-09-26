import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Math;

//test push

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;
    VolvoActuators actuator;
    SensorClass sensorClass;
    boolean sensor1working = true;
    boolean sensor2working = true;
    int sensor1WorkingCounter, sensor2WorkingCounter = 0;
    
    public static EndOfParkingPlaceStruct[] end_of_parking_place_info;  // stores the last position of a free parking place along with
                                                                        // the length of the parking spot
    Logic logic;                
    int end_of_parking_place_counter = 0; // index counter for end_of_parking_place_info array                            
    
    public Volvo(SensorClass sensor){
        this.position = 0;  //initialize the position to zero
        this.parking_situation = new boolean[500]; //initialize parking spots
        this.actuator = new VolvoActuators();
        this.sensorClass = sensor;
        
        Volvo.end_of_parking_place_info = new EndOfParkingPlaceStruct[500]; 
        for(int i = 0; i < end_of_parking_place_info.length; i++)
        {
            end_of_parking_place_info[i] = new EndOfParkingPlaceStruct(0, 0);
        }
        this.logic = new Logic();
    }


    @Override
    public MoveReturnStruct MoveForward() { 

        if(this.isParked) //if the car is parked, it can't move forward
            return new MoveReturnStruct(position, parking_situation); 
       
        boolean ok_to_move = actuator.insideLimits(position,true);
        if(ok_to_move) //move forward if the cars' position is inside limits (<500) 
            position++;
        else // don't move forward, just return the same parking situation
            return new MoveReturnStruct(position, parking_situation); // the car is at the end of the street

        int isEmptyAverage = isEmpty();

        if(isEmptyAverage>100) //compares the average distance from the sensors to decide if the position is free or occupied
            parking_situation[position] = false; //the position is occupied
        else
            parking_situation[position] = true; //the position is free

        return new MoveReturnStruct(position, parking_situation);
    }




    @Override
    public MoveReturnStruct MoveBackward() {
        // if(position == 0) //Fulfills the testcase in which we can't go back if we're at the beginning of the street
        boolean ok_to_move = actuator.insideLimits(position,false);
        if(!ok_to_move)
            return new MoveReturnStruct(0,parking_situation); //can't move backwards if beginning of street
        this.position = this.position - 1;
        return new MoveReturnStruct(position, parking_situation);
    }

    @Override
    public boolean Park() {

        if(isParked) //if it is already parked, then return
            return false; //did not park because it is already parked
    
        while(this.position < 499) { //
            boolean canPark = checkIfFreeParkingSpot(); //check if the latest 5 metres are free, fulfills the testcase "can park"
          
            if(canPark) { //Fulfills the testcase where the car is able to park
                isParked = true;
                return true; //succeded to park
            }
            MoveForward();
        }
        return false;
    }

    //  public boolean scanStreetAndPark() {

    //     if(isParked) //if it is already parked, then return
    //         return false; //did not park because it is already parked
    
    //         for(int i = 0; i < 500; i++)
    //         {
    //             MoveForward();
    //         }



    //         //position should be 500 here
    // }
    

    @Override
    public void UnPark() {
        isParked = false;   //The car is not parked
    }


    public void setSensors(int value) {
        sensors_result = value;
    }

    

    @Override
    public whereIsReturnStruct whereIs() {
        return new whereIsReturnStruct(position,isParked);
    }

    public boolean isParked(){

        return isParked;
    }

    public boolean checkIfFreeParkingSpot()
    {
        //boolean free = true;
        if(position < 5)
            return false;
        for(int i = position; i > position-5; i--) //check previous 5 meters from the position
        {
            if(!parking_situation[i]) // if one of the 5 meters is occupied, return false
                return false;
                
        }

        return true; // there is a free parking spot
    }

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
        int pos = 0;
        return pos;
    }

    
  

    @Override
    public int isEmpty() {
        int[] sensorValues1 = new int[5];
        int[] sensorValues2 = new int[5];
        double average = 0;


        for (int i = 0; i < 5; i++) {
            sensorValues1[i] = sensorClass.readSensor1();
            sensorValues2[i] = sensorClass.readSensor2();
        }
        double sensor1Deviation = logic.calculateStandardDeviation(sensorValues1);
        double sensor2Deviation = logic.calculateStandardDeviation(sensorValues2);

        if ((sensor1working && sensor2working) && (sensor1Deviation < 75 && sensor2Deviation < 75)) { // Both sensors work
            average = logic.calculateAverage(sensorValues1, sensorValues2);
        }
        if (!sensor1working && sensor1Deviation < 75) {
            sensor1WorkingCounter++;
            if (sensor1WorkingCounter >= 5) // The sensor has worked 5 times in a row
                sensor1working = true;
        }
        if (!sensor2working && sensor2Deviation < 75) {
            sensor2WorkingCounter++;
            if (sensor2WorkingCounter >= 5) // The sensor has worked 5 times in a row
                sensor2working = true;
        }
        if (sensor1Deviation > 75) { // Sensor 1 gives values upp åt väggarna
            sensor1working = false;
            sensor1WorkingCounter = 0;
        }
        if (sensor2Deviation > 75) { // Sensor 2 gives values upp åt väggarna
            sensor2working = false;
            sensor2WorkingCounter = 0;
        }
        if (sensor1working && !sensor2working) { // Only Sensor1 working
            average = logic.calculateAverage(sensorValues1);
        }
        if (!sensor1working && sensor2working) { // Only Sensor2 working
            average = logic.calculateAverage(sensorValues2);
        }
        if (!sensor1working && !sensor2working) {
            throw new NoSensorWorking("No sensor working, You are on your own");
        }
        return (int) average;
    }

   
}

     
