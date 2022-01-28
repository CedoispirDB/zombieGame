package LevelBuilder.DataManager;


import Game.DataManager.DataManager;

import java.io.*;

public class Deserializer implements Serializable {

    public Deserializer() {
//            System.out.println(new Timestamp(System.currentTimeMillis()) + ": initializing deserializer");
    }

    public DataManager loadData() {
        DataManager dataManager = null;
//        System.out.println(new Timestamp(System.currentTimeMillis()) + ": loading data");

        try {
//            FileInputStream inputStream = new FileInputStream("Data/data.ser");
            InputStream inputStream = this.getClass().getResourceAsStream("/builder.ser");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            dataManager = (DataManager) in.readObject();
            in.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dataManager;
    }
}

