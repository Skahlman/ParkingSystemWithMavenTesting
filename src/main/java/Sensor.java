public interface Sensor {
    /*

     Description: Reads the value from sensor 1 and returns it in form of an int 0 <= x <= 200

     Pre-condition: Sensor 1 exists

     Post-condition: The sensor returned a value within the threshold if working optimal.

     Test-cases: Because the sensors are not physically implemented, they are mocked and simulated which is done in the
     volvoTest class. The tests consist of simulating values within the threshold and out of bounds, and they also
     include testing if a sensor breakdowns and simulating it working 5 times in a row which should result taking that
     sensor into account when calculating the average value of sensors.
     */
    int readSensor1();

    /*

    Description: Reads the value from sensor 1 and returns it in form of an int 0 <= x <= 200

    Pre-condition: Sensor 1 exists

    Post-condition: The sensor returned a value within the threshold if working optimal.

    Test-cases: Because the sensors are not physically implemented, they are mocked and simulated which is done in the
    volvoTest class. The tests consist of simulating values within the threshold and out of bounds, and they also
    include testing if a sensor breakdowns and simulating it working 5 times in a row which should result taking that
    sensor into account when calculating the average value of sensors.
            */

    int readSensor2();
}
