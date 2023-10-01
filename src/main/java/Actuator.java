public interface Actuator {

    /*
  pre-condition: position > 0
  post-condition: returns true if the next position of the car would be okay to move to
  properties: if position < 500 and forward = true, insideLimits(position, forward) = true
              if the car want to move backward when it's at position 0 or moveforward at position 500,
              insideLimits will return false.
  test-cases: Unit tests of VolvoActuators are located and described in VolvoActuatorstest.java test class.
              insideLimits is also mocked using mockito inside the integrationsTests.java class
  */
    boolean moveIfAllowed(boolean forward);
    
}
