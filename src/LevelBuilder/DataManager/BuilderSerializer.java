package LevelBuilder.DataManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class BuilderSerializer {


    public BuilderSerializer() {
    }

    public void saveData(BuilderManager dataManager) {

        try {
            FileOutputStream outputStream = new FileOutputStream("Data/builder.ser");
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(dataManager);
            out.close();
            outputStream.close();

//            System.out.println("Data saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
