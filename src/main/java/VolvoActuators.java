public class VolvoActuators implements Actuator{

    public VolvoActuators(){}

     /*
    pre-condotion: position > 0

    post-condition: returns true if the next position of the car would be okay to move to 

    properties: if position < 500 and forward = true, insideLimits(position, forward) = true
                if the car want to move backward when it's at position 0 or moveforward at position 500, 
                insideLimits will return false.

    test-cases: Unit tests of VolvoActuators are located and described in VolvoActuatorstest.java test class.
                insideLimits is also mocked using mockito inside the integrationsTests.java class
    */ 
    @Override
    public boolean insideLimits(int position, boolean forward){

        //pre-condition
        if(position < 0) 
            return false;

        if((position == 0 && !forward ) || (position == 499 && forward )) // Fullfills testcase [1] & [2]
        {
            return false;
        }
        
        return true; //Fullfills testcase [3] & [4]
    }
    
}
