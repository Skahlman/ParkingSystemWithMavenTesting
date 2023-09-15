import java.util.Random;
import java.util.Arrays;

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean parked;
    boolean[] parking_situation;


    public Volvo(){
        this.position = 0;
        this.parking_situation = new boolean[500];
    }

    @Override
    public boolean[] MoveForward() {
        if(position != 500) // if the car is not at the end of the street -> move forward
            this.position++;
        else // don't move forward, just return the same parking situation
            return parking_situation;

        parking_situation[position] = isEmpty(); // sets the position to empty or not empty
        return parking_situation;
    }

    @Override
    public Boolean isEmpty() {

        double [] sensorvalues;
        double average;
        boolean freeparking = false;
        sensorvalues = sensorReadings();

        if(sensorvalues[0] < 75 && sensorvalues[1] < 75){

            average = sensorvalues[0] + sensorvalues[1] / 2;

            if(average < 100){
                freeparking = true;
            }else{
                freeparking = false;
            }


        }

        return freeparking;



    }



    @Override
    public int MoveBackward() {
        this.position = this.position - 1;
        return this.position;
    }

    @Override
    public void Park() {
        parked = true;
    }

    @Override
    public void UnPark() {
        parked = false;
    }
    public double readSensor(){
        Random random = new Random();
        double SensorValue = random.nextInt(200);
        return SensorValue;


    }
    public double[] sensorReadings(){
        double[] sensor_values = new double[2];
        double[] data1 = new double[5];
        double[] data2 = new double[5];
        double var1 = 0.0;
        double var2 = 0.0;


        for(int i = 0; i<5; i++){
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
    public String WhereIs() {
        return "The position of the car is: " + position;
    }

    public boolean isParked(){

        return parked;
    }
}
