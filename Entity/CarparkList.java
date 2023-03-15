import java.util.ArrayList;


public class CarparkList {
    private ArrayList<Carpark> carparkList;
    private int size;

    public CarparkList() {
        carparkList = new ArrayList<Carpark> (1);
        size = 1;
    }

    public CarparkList(int num) {
        carparkList = new ArrayList<Carpark> (num);
        size = num;
    }

    public void displayList(ArrayList<Carpark> carparkList, int size) {
        for(int i = 0; i < size; i++) {
            System.out.print("SOMETHING");
        }
    }

    public void setCarparkList(ArrayList<Carpark> carparkList) {
        this.carparkList = carparkList;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<Carpark> getCarparks() {
        return this.carparkList;
    }

    public int getSize() {
        return this.size;
    }

    public void addCarpark(Carpark carpark) {
        this.size++;
        this.carparkList.add(carpark);
    }

}