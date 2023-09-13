public class Volvo implements Car {

    public int position;
    public int sensors_result = 0;


    public Volvo(){
        this.position = 0;
    }
    @Override
    public int MoveForward() {
        this.position++;
        return this.position;
    }

    @Override
    public Boolean isEmpty() {

        if (sensors_result == 200)
            return false;
        else
            return true;
    }



    @Override
    public int MoveBackward() {
        this.position = this.position - 1;
        return this.position;
    }

    @Override
    public void Park() {

    }

    @Override
    public void UnPark() {
    }

    public void setSensors(int value) {
        sensors_result = value;
    }
    public int readSensor() {
        return 0;
    }

    @Override
    public String WhereIs() {
        return "The position of the car is: " + position;
    }
}
