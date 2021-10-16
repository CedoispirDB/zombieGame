package DataManager;


import Levels.LevelManager;

import java.io.*;
import java.sql.Timestamp;

public class Deserializer implements Serializable {

    public Deserializer() {
//            System.out.println(new Timestamp(System.currentTimeMillis()) + ": initializing deserializer");
    }

    public LevelManager loadData() {
        LevelManager levelManager = null;
//        System.out.println(new Timestamp(System.currentTimeMillis()) + ": loading data");

        try {
            FileInputStream inputStream = new FileInputStream("./LevelsData/data.ser");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            levelManager = (LevelManager) in.readObject();
            in.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return levelManager;
    }
}

