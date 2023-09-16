import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;



    public Volvo(){
        this.position = 0;
        this.parking_situation = new boolean[500];
    }

    // @Override
    // public boolean[] MoveForward() { //gammal MoveForward
    //     if(position != 499) // if the car is not at the end of the street -> move forward
    //         this.position++;
    //     else // don't move forward, just return the same parking situation
    //         return parking_situation; //Maybe generate an error message

    //     parking_situation[position] = isEmpty(); // sets the position to empty or not empty
    //     return parking_situation;
    // }

    @Override
    public MoveReturnStruct MoveForward() { //AdamsMoveForward

        if(this.isParked) //if the car is parked, it can't move forward
            return new MoveReturnStruct(position, parking_situation); 

        if(position != 500) // if the car is not at the end of the street -> move forward 
            this.position++;
        else // don't move forward, just return the same parking situation
            return new MoveReturnStruct(position, parking_situation); // the car is at the end of the street

        parking_situation[position] = isEmpty(); // sets the position to empty or not empty
        return new MoveReturnStruct(position, parking_situation);
    }

        @Override
    public Boolean isEmpty() {

        if(parking_situation[position] == true)  //if free place
        {
            return true;
        }
        return false; //change this so that park() tests would work

    }

    /* Phase two
    @Override
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

     */


    
    // public int MoveBackward() { //gammal MoveBackwards

    //     if(position == 0)
    //         return 0; //can't move backwards if beginning of street
    //     this.position = this.position - 1;
    //     return this.position;
    // }

    @Override
    public MoveReturnStruct MoveBackward() {
        if(position == 0)
            return new MoveReturnStruct(0,parking_situation); //can't move backwards if beginning of street
        this.position = this.position - 1;
        return new MoveReturnStruct(position, parking_situation);
    }

    @Override
    public boolean Park() {

    if(isParked) //if it is already parked, then return
        return false; //did not park because it is already parked
    

    while(this.position < 499)
    {
        boolean canPark = checkIfFreeParkingSpot(); //check if the latest 5 metres are free
      
        if(canPark)
        {
            isParked = true;
            return true; //succeded to park
        }
        
       MoveReturnStruct move_fwd =  MoveForward();

    }

    return false;
        
    }

    @Override
    public void UnPark() {
        isParked = false;
    }



    public double readSensor(){
        Random random = new Random();
        return random.nextInt(200);
    }

    public double[] sensorReadings(){
        double[] sensor_values = new double[2];
        double[] data1 = new double[5];
        double[] data2 = new double[5];
        double var1 = 0.0;
        double var2 = 0.0;


        for(int i = 0; i < 5; i++){
            data1[i] = readSensor();
            data2[i] = readSensor();

        }
        var1 = calculateVariance(data1);
        var2 = calculateVariance(data2);

        sensor_values[0] = var1;
        sensor_values[1] = var2;


        return sensor_values;

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
        double variance = squaredDifferencesSum / data.length;

        return variance;
    }

    public void setSensors(int value) {
        sensors_result = value;
    }

    @Override
    public whereIsReturnStruct whereIs() {
        return new whereIsReturnStruct(position,isParked);
    }
    // public Map<Integer, Boolean> whereIs(){
    //     Map<Integer, Boolean> whereIs = new HashMap<>(2);
    //     whereIs.put(position, isParked);
    //     return whereIs;
    // }

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
}
