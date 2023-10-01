public interface Actuator {

    /*
  pre-condition: position > 0
  post-condition: returns true if the next position of the car would be okay to move to
  properties: if position < 500 and forward = true, moveIfAllowed(forward) = true
              if the car want to move backward when it's at position 0 or moveforward at position 500,
              moveIfAllowed will return false.
  test-cases: Unit tests of VolvoActuators are located and described in VolvoActuatorstest.java test class.
              moveIfAllowed is also mocked using mockito inside the IntegrationsTests.java class
              Testcase [1]: car is at position 500 and wants to move forward, the actuators should return false,
              since it should not move forward in this case.
              Testcase [2]: car is at position 0 and wants to move backward, the actuators should return false,
              since it should not move backward in this case.
              Testcase [3]: car is at a position > 0 and < 500 and wants to move forward, the actuators should return true,
              since it should not be problem moving forward in this case.
              Testcase [4]: car is at a position > 0 and < 500 and wants to move backward, the actuators should return true,
              since it should not be problem moving backward in this case.
              Testcase [5]: car is at negative position, moveIfAllowed() should return false.
  */
    boolean moveIfAllowed(boolean forward);
    
}
