public interface Actuator {

    /*
    pre-condotion: position > 0
    post-condition: returns true if the next position of the car would be okay to move to 
    properties: if position < 500 and forward = true, insideLimits(position, forward) = true
                if the car want to move backward when it's at position 0 or moveforward at position 500,

    */ 
    boolean insideLimits(int position, boolean forward);
    
}
