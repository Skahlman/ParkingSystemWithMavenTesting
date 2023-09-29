public interface Logic {
    /**

     Description: Calculates the standard deviation of all elements in an array

     Pre-condition: array != null and array.length != 0

     Post-condition: The returned value contains the standard deviation originating from the elements of the array

     Test-cases:
     Fill an array with values of 1,2,3,4 and double-check with a calculator that the deviation is correct.
     Fill an array with value of 5 and assert that the returned value is 0;

     */
    double calculateStandardDeviation(int[] arr);
    /**

     Description: Calculates the average(mean) of all elements in two arrays.

     Pre-condition: array != null and array.length != 0

     Post-condition: The returned value contains the average of all elements in the arrays

     Test-cases: Fill an array with values of 1,3,9,8 and the other with 3,7,4,6, then assert that the mean is 5 .

     */

    int calculateAverage (int[] sensor1Readings, int[] sensor2Readings);
    /**

     Description: Calculates the average(mean) of all elements in two arrays.

     Pre-condition: array != null and array.length != 0

     Post-condition: The returned value contains the average of all elements in the arrays

     Test-cases: Fill an array with values of 3,7,4,6, then assert that the mean is 5 .

     */

    int calculateAverage(int[] sensorReadings);
}
