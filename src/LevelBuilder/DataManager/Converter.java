package LevelBuilder.DataManager;


import Game.DataManager.DataManager;
import Game.DataManager.Deserializer;
import Game.DataManager.Serializer;
import Game.Main.Game;

import java.util.LinkedList;

public class Converter {
    public static void convert() {
        BuilderManager builderManager = new BuilderDeserializer().loadData();
        DataManager dataManager = new Deserializer().loadData();

        LinkedList<String> data = builderManager.getLevelData();
        String str = "";

        for (int i = 1; i < data.size(); i++) {
            if (i != data.size() - 1) {
                str += data.get(i) + " ";
            } else {
                str += data.get(i);
            }
        }

        System.out.println(str);
        dataManager.deleteData();
        dataManager.saveLevel(str);

        new Serializer().saveData(dataManager);

    }

    public static void main(String[] args) {
        convert();
    }

}
