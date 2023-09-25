import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Math;

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;
    VolvoActuators actuator;



    public Volvo(){
        this.position = 0;  //initialize the position to zero
        this.parking_situation = new boolean[500]; //initialize parking spots
        this.actuator = new VolvoActuators();
    }


    @Override
    public MoveReturnStruct MoveForward() { //AdamsMoveForward

        if(this.isParked) //if the car is parked, it can't move forward
            return new MoveReturnStruct(position, parking_situation); 

        // if(position != 500) // if the car is not at the end of the street -> move forward 
        //     this.position++;
       
        boolean ok_to_move = actuator.insideLimits(position,true);
        if(ok_to_move)
            position++;
        else // don't move forward, just return the same parking situation
            return new MoveReturnStruct(position, parking_situation); // the car is at the end of the street

        parking_situation[position] = isEmpty(); // sets the position to empty or not empty
        return new MoveReturnStruct(position, parking_situation);
    }
/* 
    @Override
    public Boolean isEmpty() {
        return parking_situation[position]; //Returns true if position is empty
    }
*/



    @Override
    public MoveReturnStruct MoveBackward() {
        // if(position == 0) //Fulfills the testcase in which we can't go back if we're at the beginning of the street
        boolean ok_to_move = actuator.insideLimits(position,true);
        if(!ok_to_move)
            return new MoveReturnStruct(0,parking_situation); //can't move backwards if beginning of street
        this.position = this.position - 1;
        return new MoveReturnStruct(position, parking_situation);
    }

    @Override
    public boolean Park() {
        int counter = 0;
        boolean limits = false;
        VolvoActuators actuator = new VolvoActuators();
        Volvo car = new Volvo();
        MoveReturnStruct status;
        int [] parkingspots_positions = new int[5];
        int [] minimum_to_closest_parking_spot = new int[5];
        int j  = 0;
        int position_of_lowest_value_to_parking_spot;
        int lowest_value_of_parking_spot;
        int difference;
        //boolean forwad_backward = true;
        
        if(isParked) //if it is already parked, then return
            return true; //did not park because it is already parked
    
        while(true){
            for(boolean parking_spot : parking_situation){
                if(parking_spot == true){
                    counter++;
                }
            }
            if(counter>=5){
                counter = 0;
                break;
            }
            counter = 0;
            
            parking_situation[position] = isEmpty();
            status = MoveForward();            

            /*
            while(this.position < 499) { //
                boolean canPark = checkIfFreeParkingSpot(); //check if the latest 5 metres are free, fulfills the testcase "can park"
            
                if(canPark) { //Fulfills the testcase where the car is able to park
                    isParked = true;
                    return true; //succeded to park
                }
                MoveForward();
            }*/
        }
        for(int i = 0; i<500; i++){
            if(parking_situation[i]==true){
                parkingspots_positions[j] = i;
                j++;
            }
        }
        for(int i = 0; i < 5; i++){
            minimum_to_closest_parking_spot[i] = Math.abs(position - parkingspots_positions[i]);
        }
        lowest_value_of_parking_spot = minimum_to_closest_parking_spot[0];
        position_of_lowest_value_to_parking_spot = 0;
        for(int i = 1; i < 5; i++){
            
            if(minimum_to_closest_parking_spot[i]<lowest_value_of_parking_spot){
                lowest_value_of_parking_spot = minimum_to_closest_parking_spot[i];
                position_of_lowest_value_to_parking_spot = i;

            }
        }
        difference = position - position_of_lowest_value_to_parking_spot;
        if(difference>=0){
            for(int i = position; i > (position - difference); i--){
                status = MoveBackward();
            }
            isParked = true;
            System.out.println("Your car is parked at position: " + position);
        }
        else if(difference <0){
            for(int i = position; i < position + Math.abs(difference); i++){
                status = MoveForward();
            }
            isParked = true;
            System.out.println("Your car is parked at position: " + position);
        }


        return isParked;
    }

    @Override
    public void UnPark() {
        isParked = false;   //The car is not parked
    }



    public double readSensor(){
        Random random = new Random();   //Generates a random object 
        return random.nextInt(200); //Generates a random number
    }

    
    public double[] sensorReadings(){
        double[] sensor_values = new double[2];
        double[] data1 = new double[5];
        double[] data2 = new double[5];
        double var1 = 0.0;
        double var2 = 0.0;


        for(int i = 0; i < 5; i++){
            data1[i] = readSensor();    //Reads sensors to the left
            data2[i] = readSensor();    //Reads sensors to the right

        }
        var1 = calculateVariance(data1);    //Calculate variance for left senor
        var2 = calculateVariance(data2);    //Calculate variance for right sensor

        sensor_values[0] = var1;    //End value of variance for sensor 1
        sensor_values[1] = var2;    //End value of variance for sensor 2


        return sensor_values;   //Returns an array of sensor values; variance

    }

    
    public static double calculateVariance(double[] data) {
        // Step 1: Calculate the mean
        double sum = 0;
        for (double num : data) {
            sum += num;
        }
        double mean = sum / data.length;

        // Step 2: Calculate the squared differences
        double squaredDifferencesSum = 0;
        for (double num : data) {
            double difference = num - mean;
            squaredDifferencesSum += difference * difference;
        }

        // Step 3: Calculate the variance
        double variance = squaredDifferencesSum / (data.length-1);

        return variance;
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
        for(int i = position; i > position-5; i--)
        {
            if(!parking_situation[i]) // if one of the 5 meters is occupied, return false
                return false;
                
        }

        return true; // there is a free parking spot
    }



    public Boolean isEmpty() {

        double [] sensorVariance;
        double average;
        boolean freeparking = false;
        sensorVariance = sensorReadings();

        if(sensorVariance[0] < 75 && sensorVariance[1] < 75){

            average = (sensorVariance[0] + sensorVariance[1]) / 2;

            if(average < 100.0){
                freeparking = true;
            }else{
                freeparking = false;
            }
        }
        return freeparking;
    }
}

     
