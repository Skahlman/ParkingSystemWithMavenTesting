import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;



    public Volvo(){
        this.position = 0;  //initialize the position to zero
        this.parking_situation = new boolean[500]; //initialize parking spots
    }

    @Override
    public boolean[] MoveForward() {
        if(position != 499) // if the car is not at the end of the street -> move forward
            this.position++;
        else // don't move forward, just return the same parking situation
            return parking_situation;

        parking_situation[position] = isEmpty(); // sets the position to empty or not empty
        return parking_situation;
    }

    public Map<Integer, boolean[]> AdamsMoveForward() {
        Map<Integer, boolean[]> returnvalue = Map.of(position, parking_situation);
        if (position!= 500) {
            position++;
        }
        parking_situation[position] = isEmpty();
        return  returnvalue;
    }

        @Override
    public Boolean isEmpty() {
        return false;
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


    @Override
    public int MoveBackward() {

        if(position == 0)
            return 0; //can't move backwards if beginning of street
        this.position = this.position - 1;  //Moves the car backward with one step
        return this.position;   //Returns the position of the car
    }

    @Override
    public boolean Park() {

    if(isParked) //if it is already parked, then return
        return false; //did not park because it is already parked
    

    while(this.position < 499)
    {
        //boolean canPark = checkIfFreeParkingSpot(); //check if the latest 5 metres are free
        boolean canPark = false;
        if(canPark)
        {
            isParked = true;
            return true; //succeded to park
        }
        
        //this.position++;
       boolean[] move_fwd =  MoveForward();
    }

    return false;
        
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
        double variance = squaredDifferencesSum / data.length;

        return variance;
    }

    public void setSensors(int value) {
        sensors_result = value;
    }
/*
    private static Pair<String, String> methodWithPairResult() {
        //...
        return new ImmutablePair<>("valueA", "valueB");
    }

    private static void usingPairResultTest() {
        Pair<String, String> result = methodWithPairResult();
        System.out.println();
        System.out.println("A = " + result.getKey());
        System.out.println("B = " + result.getValue());
    }
 */


    public Map<Integer, Boolean> whereIs(){
        Map<Integer, Boolean> whereIs = new HashMap<>(2);
        whereIs.put(position, isParked);
        return whereIs;
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
}
