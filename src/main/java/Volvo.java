import java.util.Random;
//import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;


public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;
    public boolean isParked = false;
    boolean[] parking_situation;
    boolean sensor1working = true;
    boolean sensor2working = true;
    int sensor1WorkingCounter, sensor2WorkingCounter = 0;
    public SensorClass sensorClass;
    public Logic logic = new Logic();

    public Volvo(SensorClass sensor) {
        this.position = 0;
        this.parking_situation = new boolean[500];
        this.sensorClass = sensor;
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


    public int isEmpty2() {
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