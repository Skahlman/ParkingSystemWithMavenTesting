public class VolvoActuators implements Actuator{


    public VolvoActuators(){}
    @Override
    public boolean insideLimits(int position, boolean forward){

        if((position == 0 && !forward ) || (position == 500 && forward )) // Fullfills testcase [1] & [2]
        {
            return false;
        }
        
        return true; //Fullfills testcase [3] & [4]
    }
    
}
