import java.util.ArrayList;

public interface ParkingspotAnalyser {

    /*
     Description: Goes through the parking_situation array and returns a list with free parking spots
     along with the length of the parking spots. The car has to have passed the parking spot to be able
     to recognize it with this function
     Pre-condition: parking_situation != null
     Post-condition: returns a list of type EndOfParkingPlaceStruct filled with all free parking spots
     Test-cases:
     Testcase [1]: No found parking spots, parkingSpots() should return an empty array.
     Testcase [2]: There are three avalibale parking spots with different lengths, this
                    test checks that the list returns the correct length and position.
     Testcase [5]: insert null array inside parkingSpots() will return an empty list.
     */
    ArrayList<EndOfParkingPlaceStruct> parkingSpots(boolean[] parking_situation);

    /*
     Description: Goes thorough the list that was created/returned from parkingSpot() function
     and chooses the best parking spot. The best parking spot here has the shortest length and
     is closest to the end of the street.
     Pre-condition: list is not empty
     Post-condition: returns the position at the end of the best parking spot
     Test-cases:
     Testcase [3]: There are three free parking spots avaliable, and
                    calculateBestParingSpot() should return the parking spot at position 9.
                    The parking spot as position 455 has the same length, but it should
                    not return that one since it is at the very end of the street.
     Testcase [4]: calculateBestParkingSpot() receives an empty list, and returns -1.
    */
    int calculateBestParkingSpot(ArrayList<EndOfParkingPlaceStruct> list);

    /*
    Description: This function checks if there is a free parking spot at the position the car is
    at right at the moment. This function is useful when when the car wants to park at the first
    parking spot it reaches.
    Pre-condition: position < 5
    Post-condition: if there are five meters free behind the car (size of a parking spot), the function
    returns true so that the car knows it can park there. Otherwise, it will return false.
    Test-cases: Covered by testes for MoveForward function inside VolvoTest.java
   */
    boolean checkIfFreeParkingSpot(int position, boolean[] parking_situation);

}
