public class VolvoActuators implements Actuator{

  public int position;

    public VolvoActuators()
    {
        this.position = 0;
    }

    @Override
    public boolean moveIfAllowed(boolean forward){

        //pre-condition
        if(position < 0) //fulfills testcase [5]
            return false;

        if((position == 0 && !forward ) || (position == 499 && forward )) // Fulfills testcase [1] & [2]
        {
            return false;
        }

        if(forward)
            position++; //Testcase [3]
        else
            position--; // Testcase [4]

        return true; //Fulfills testcase [3] & [4]
    }



}
