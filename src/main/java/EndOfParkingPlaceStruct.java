//public record EndOfParkingPlaceStruct(int position, int length) { }
public class EndOfParkingPlaceStruct
{
    public int position;
    public int length;
    // private EndOfParkingPlaceStruct[] arr;

    public EndOfParkingPlaceStruct(int position, int length)
    {
        this.position = position;
        this.length = length;
        //this.arr = new EndOfParkingPlaceStruct[500];
        // for(int i = 0; i < arr.length; i++)
        // {
        //     arr[i] = new EndOfParkingPlaceStruct(0, 0);
        // }
    }

    // public EndOfParkingPlaceStruct[] getArray()
    // {
    //     return arr;
    // }

    // public void setArray(int i, int position, int length)
    // {
    //     arr[i] = new EndOfParkingPlaceStruct(position, length);
    // }


}