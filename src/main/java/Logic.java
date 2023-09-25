public class Logic {
    double calculateStandardDeviation(int[] arr) {
        int n = arr.length;
        double mean = calculateAverage(arr);
        double variance = 0.0;

        for (int num : arr) {
            variance += Math.pow(num - mean, 2);
        }

        variance /= n;

        return Math.sqrt(variance);
    }

    int calculateAverage (int[] sensor1Readings, int[] sensor2Readings) {
        int average = 0;
        for (int i = 0; i < sensor1Readings.length; i++) {
            average += sensor1Readings[i];
            average += sensor2Readings[i];
        }
        return average / (sensor1Readings.length * 2);

    }
    int calculateAverage(int[] sensorReadings) {
        int average = 0;
        int counter = 0;
        for (int sensorReading : sensorReadings) {
            average += sensorReading;
            counter++;
        }
        return average / counter;
    }

}
