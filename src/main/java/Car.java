
public interface Car {

    /**

     Description: Moves the car forward 1 meter and informs of the situation of the detected parking places up to now.

     Pre-condition: ProductionCode.Car is not at the end of the street

     Post-condition: The car has moved 1 meter forward

     Test-cases: If the car is at the end of the street

     */
    boolean[] MoveForward();

    /**

     Description: This method queries the two ultrasound sensors at least 5 times and filters the noise
     in their results and returns the distance to the nearest object on the right hand side.

     Pre-condition: At least one sensor is operable

     Post-condition: Knowledge of the distance to the nearest object

     Test-cases: Both sensors are operable, one sensor is inoperable, both sensors are inoperable
     When the results are adjacent to the threshold.

     */
    Boolean isEmpty();

    /**

     Description: Moves the car 1 meter backwards

     Pre-condition: Must not be in the beginning of the street

     Post-condition: The car is now one meter behind its previous position

     Test-cases: The car's position is at the beginning of the street and at another, unspecified, position in the street

     */
    int MoveBackward();

    /**

     Description: Moves the car forward until the parking-conditions are met and then performs a parking sequence.

     Pre-condition: The car is not parked.

     Post-condition: The car is parked

     Test-cases: The car is/isn't parked, the car is within the boundaries of the street

     */
    void Park();

    /**

     Description: It moves the car forward (and to left) to front of the parking place, if it is parked.

     Pre-condition: The car must be parked

     Post-conditioned: the car is un-parked and parallel to the car in front

     Test-cases: The car is/isn't parked.

     */

    void UnPark();

    /**

     Description: This method returns the current position of the car in the street as well as its situation (whether it is parked or not).

     Pre-condition:

     Post-conditioned: the car is un-parked and parallel to the car in front

     Test-cases: The car is/isn't parked.x

     */






}
