import java.util.Random;

public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;
    boolean sensor1working = true;
    boolean sensor2working = true;
    int sensor1WorkingCounter, sensor2WorkingCounter = 0;


    public Volvo() {
        this.position = 0;
        this.parking_situation = new boolean[500];
    }


    @Override
    public MoveReturnStruct MoveForward() { //AdamsMoveForward

        if (this.isParked) //if the car is parked, it can't move forward
            return new MoveReturnStruct(position, parking_situation);

        if (position != 500) // if the car is not at the end of the street -> move forward
            this.position++;
        else // don't move forward, just return the same parking situation
            return new MoveReturnStruct(position, parking_situation); // the car is at the end of the street

        parking_situation[position] = isEmpty(); // sets the position to empty or not empty
        return new MoveReturnStruct(position, parking_situation);
    }

    @Override
    public Boolean isEmpty() {
        return parking_situation[position]; //Returns true if position is empty
    }


    @Override
    public MoveReturnStruct MoveBackward() {
        if (position == 0) //Fulfills the testcase in which we can't go back if we're at the beginning of the street
            return new MoveReturnStruct(0, parking_situation); //can't move backwards if beginning of street
        this.position = this.position - 1;
        return new MoveReturnStruct(position, parking_situation);
    }

    @Override
    public boolean Park() {

        if (isParked) //if it is already parked, then return
            return false; //did not park because it is already parked

        while (this.position < 499) { //
            boolean canPark = checkIfFreeParkingSpot(); //check if the latest 5 metres are free, fulfills the testcase "can park"

            if (canPark) { //Fulfills the testcase where the car is able to park
                isParked = true;
                return true; //succeded to park
            }
            MoveForward();
        }
        return false;
    }

    @Override
    public void UnPark() {
        isParked = false;
    }



    public double readSensor() {
        Random random = new Random();
        return random.nextInt(200);
    }

    //For phase 2

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


    public static double calculateVariance(int[] data) {
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

        return squaredDifferencesSum / data.length;
    }

    public void setSensors(int value) {
        sensors_result = value;
    }


    @Override
    public whereIsReturnStruct whereIs() {
        return new whereIsReturnStruct(position, isParked);
    }

    public boolean isParked() {

        return isParked;
    }

    public boolean checkIfFreeParkingSpot() {
        //boolean free = true;
        if (position < 5)
            return false;
        for (int i = position; i > position - 5; i--) {
            if (!parking_situation[i]) // if one of the 5 meters is occupied, return false
                return false;

        }

        return true; // there is a free parking spot
    }


    //Phase two
    @Override
    public Boolean isEmpty() {

        double[] sensorVariance;
        double average;
        boolean freeparking = false;
        sensorVariance = sensorReadings();

        if (sensorVariance[0] < 75 && sensorVariance[1] < 75) {

            average = (sensorVariance[0] + sensorVariance[1]) / 2;

            if (average < 100.0) {
                freeparking = true;
            } else {
                freeparking = false;
            }
        }
        return freeparking;
    }
    public int isEmpty2() {
        SensorClass sensorClass = new SensorClass();
        int[] sensorValues1 = new int[5];
        int[] sensorValues2 = new int[5];
        double average;


        for (int i = 0; i < 5; i++) {
            sensorValues1[i] = sensorClass.readSensor()[0];
            sensorValues2[i] = sensorClass.readSensor()[1];
        }
        double sensor1Variance = calculateVariance(sensorValues1);
        double sensor2Variance = calculateVariance(sensorValues2);

        if ((sensor1working && sensor2working) && (sensor1Variance < 75 && sensor2Variance < 75)) {
            average = (sensor1Variance + sensor2Variance) / 2;
        }
        if (!sensor1working && sensor1Variance < 75) {
            sensor1WorkingCounter++;
            if (sensor1WorkingCounter >= 5) // The sensor has worked 5 times in a row
                sensor1working = true;
        }
        if (!sensor2working && sensor2Variance < 75) {
            sensor2WorkingCounter++;
            if (sensor2WorkingCounter >= 5) // The sensor has worked 5 times in a row
                sensor2working = true;
        }
        if (sensor1Variance > 75) { // Sensor 1 gives values upp åt väggarna
            sensor1WorkingCounter = 0;
        }
        if (sensor2Variance > 75) { // Sensor 2 gives values upp åt väggarna
            sensor2WorkingCounter = 0;
        }
        if (sensor1working && !sensor2working) {
            average = calculateAverage(sensorValues1);
        }
        if (!sensor1working && sensor2working) {
            average = calculateAverage(sensorValues2);
        }
        if (!sensor1working && !sensor2working) {
            throw new IllegalStateException("No sensor working");
        }
    }
}