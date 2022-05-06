package LevelBuilder.Main;

import LevelBuilder.DataManager.BuilderDeserializer;
import LevelBuilder.DataManager.BuilderManager;

import java.util.LinkedList;

public class Improv {
    public static void main(String[] args) {
        BuilderManager dataManager = new BuilderDeserializer().loadData();
        LinkedList<String> data = dataManager.getLevelData();
        for (int i = 0; i < data.size(); i++) {
            System.out.print(data.get(i) + " ");
        }

    }
}
