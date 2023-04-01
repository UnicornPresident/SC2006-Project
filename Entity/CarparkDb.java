import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;


public class CarparkDb {
    private ArrayList<Carpark> CarparkList;

    public CarparkDb() {
        CarparkList = new ArrayList<Carpark>();
    }
    /* 
    public void writeToDb(ArrayList<Carpark> carparkList) {
        File csvFile = new File(DataBaseFilePath);
        FileWriter fileWriter = new FileWriter(csvFile);
         
    }
    */

    public void readFromFile() {
        String line = " ";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("hdb-carpark-information.csv"));
            while((line = br.readLine()) != null) {
                String[] carpark = line.split(splitBy);
                String number = carpark[0];
                String address = carpark[1];
                String x_coord = carpark[2];
                String y_coord = carpark[3];
                String restricted = carpark[4];
                Carpark c = new Carpark(number, address, x_coord, y_coord, restricted);
                CarparkList.add(c);
            
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Carpark> getCarparkList() {
        return this.CarparkList;
    }

    

    public static void main(String[] args) {
        CarparkDb carparkDb = new CarparkDb();
        carparkDb.readFromFile();
    }

}
