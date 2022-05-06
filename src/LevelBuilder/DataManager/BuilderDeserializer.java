package LevelBuilder.DataManager;


import java.io.*;

public class BuilderDeserializer implements Serializable {

    public BuilderDeserializer() {
//            System.out.println(new Timestamp(System.currentTimeMillis()) + ": initializing deserializer");
    }

    public BuilderManager loadData() {
        BuilderManager dataManager = null;
//        System.out.println(new Timestamp(System.currentTimeMillis()) + ": loading data");

        try {
//            FileInputStream inputStream = new FileInputStream("Data/data.ser");
            InputStream inputStream = this.getClass().getResourceAsStream("/builder.ser");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            dataManager = (BuilderManager) in.readObject();
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

