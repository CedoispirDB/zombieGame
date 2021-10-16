package DataManager;

import Levels.LevelManager;

import java.io.*;


public class Serializer {


    public Serializer() {
    }

    public void saveData(LevelManager levelManager) {

        try {
            FileOutputStream outputStream = new FileOutputStream("./LevelsData/data.ser");
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(levelManager);
            out.close();
            outputStream.close();

            System.out.println("Data saved");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
