import java.util.ArrayList;

public class Volvo implements Car {

    /* Testcases for Volvo.java class are located in VolvoTest.java and are
        numbered [1], [2],... [n].
     */

    public boolean isParked = false;
    boolean[] parking_situation;
    public VolvoActuators actuator;
    SensorClass sensorClass;
    boolean sensor1working = true, sensor2working= true;
    int sensor1WorkingCounter, sensor2WorkingCounter = 0;
    LogicClass logic;

    public Volvo(SensorClass sensor){
        this.parking_situation = new boolean[500]; //initialize parking spots
        this.actuator = new VolvoActuators();
        this.sensorClass = sensor;
        this.logic = new LogicClass();
    }

    @Override
    public MoveReturnStruct MoveForward() { 

        if(this.isParked) // Fulfills testcase [4]: if the car is parked, it can't move forward
            return new MoveReturnStruct(actuator.position, parking_situation);
       
        boolean ok_to_move = actuator.moveIfAllowed(true); //move forward if the cars' position is inside limits (<500)

        if(!ok_to_move) // Fulfills Testcase [3]: don't move forward, just return the same parking situation
            return new MoveReturnStruct(actuator.position, parking_situation); // the car is at the end of the street

        int isEmptyAverage = isEmpty();

        if(isEmptyAverage<100) //compares the average distance from the sensors to decide if the position is free or occupied
            parking_situation[actuator.position] = false; //Testcase [16]: the position is occupied
        else
            parking_situation[actuator.position] = true; //Testcase [17]: the position is free

        return new MoveReturnStruct(actuator.position, parking_situation); //Fulfills testcase [2]
    }


    @Override
    public MoveReturnStruct MoveBackward() {

        boolean ok_to_move = actuator.moveIfAllowed(false);
        if(!ok_to_move) //Fulfills testcase [5]
            return new MoveReturnStruct(0,parking_situation); //can't move backwards if beginning of street

        return new MoveReturnStruct(actuator.position, parking_situation); //Fulfills testcase [6], it is ok to move backwards
    }

    @Override
    public boolean Park() {

        if(isParked) // Fulfills testcase [9]: if it is already parked, then return
            return false; //did not park because it is already parked

        ParkingAnalyser analyser = new ParkingAnalyser();

        while(actuator.position < 499) { //
            boolean canPark = analyser.checkIfFreeParkingSpot(actuator.position, this.parking_situation); //check if the latest 5 metres are free, fulfills the testcase "can park"
          
            if(canPark) { //Fulfills testcase [7] where the car is able to park
                isParked = true;
                return true; //succeded to park
            }
            MoveForward();
        }
           /*  if the car is at the end of the street, call the ParkingAnalyser class and analyse the
            parking_situation array and look for the best parking spot */

            /*  goes through the parking_sitation array and stores the position at the end of each parkingspot
                along with the length of the parking spot in a list */
        ArrayList<EndOfParkingPlaceStruct> list = analyser.parkingSpots(parking_situation);
        if(list.isEmpty())
            return false; // Fulfills Testcase [8]: no parking spots available. Also fulfills testcase [10]
        int park_position = analyser.calculateBestParkingSpot(list)-4; //park_position stores the position at the beginning of the best parking spot.
        if(park_position < 0 )
            return false;
        for(int i = 0; i < this.parking_situation.length-park_position; i++)
        {
            MoveBackward(); //move back to the best parking position
        }
        isParked = true; //park the car
        return true;

    }

    @Override
    public void UnPark() {
        isParked = false;   //The car is not parked anymore. Fulfills testcase [11] and [12]
    }

    @Override
    public whereIsReturnStruct whereIs() {
        return new whereIsReturnStruct(actuator.position,isParked);
    }

    public boolean isParked(){

        return isParked;
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
            if (sensor2WorkingCounter >= 5) // Fulfills testcase [18]: The sensor has worked 5 times in a row
                sensor2working = true;
        }
        if (sensor1Deviation > 75) { // Fulfills testcase [13]: Sensor 1 gives noisy values.
            sensor1working = false;
            sensor1WorkingCounter = 0;
        }
        if (sensor2Deviation > 75) { // Fulfills testcase [14]: Sensor 2 gives noisy values.
            sensor2working = false;
            sensor2WorkingCounter = 0;
        }
        if (sensor1working && !sensor2working) { // Only Sensor1 working
            average = logic.calculateAverage(sensorValues1);
        }
        if (!sensor1working && sensor2working) { // Only Sensor2 working
            average = logic.calculateAverage(sensorValues2);
        }
        if (!sensor1working && !sensor2working) { //Fulfills testcase [15]
            throw new NoSensorWorking("No sensor working, You are on your own");
        }
        return (int) average;
    }

   
}

     
