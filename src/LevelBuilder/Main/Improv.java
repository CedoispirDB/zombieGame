package LevelBuilder.Main;

import LevelBuilder.DataManager.Deserializer;
import LevelBuilder.DataManager.DataManager;

import java.util.LinkedList;

public class Improv {
    public static void main(String[] args) {
        DataManager dataManager = new Deserializer().loadData();
        LinkedList<String> data = dataManager.getLevelData();
        for (int i = 0; i < data.size(); i++) {
            System.out.print(data.get(i) + " ");
        }

    }
}
