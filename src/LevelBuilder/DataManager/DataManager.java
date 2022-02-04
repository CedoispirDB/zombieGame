package LevelBuilder.DataManager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

public class DataManager implements Serializable {

    private final LinkedList<String> levelData;


    public DataManager() {
        levelData = new LinkedList<>();
    }

    public void saveData(LinkedList<String> newData) {

        for (int i = 1; i < newData.size(); i++) {
            levelData.add(newData.get(i));
        }

        new Serializer().saveData(this);
    }

    public void saveData(String str) {
        String sub;
        int b = 0;
        int e = str.indexOf(" ");
        do {
            if (e == -1) {
                levelData.add(str);
                break;
            }
            sub = str.substring(b, e);
            levelData.add(sub);
            str = str.substring(e + 1);
            e = str.indexOf(" ");
        } while (true);
        new Serializer().saveData(this);
    }

    public LinkedList<String> loadData(int lvl) {
        LinkedList<String> data = new LinkedList<>();
        int count = 1;
        String d;
        int b = 0;
        int e = 0;
        boolean found = false;

        for (int i = 0; i < levelData.size(); i++) {
            d = levelData.get(i);
            if (d.equals("*")) {
                if (count == lvl) {
                    b = i;
                    found = true;
                } else {
                    if (found) {
                        e = i;
                        break;
                    }
                }
                count++;
            }
        }

        for (int i = b + 1; i < e; i++) {
            data.add(levelData.get(i));
        }

        return data;
    }

    public void insert(LinkedList<String> newLevel, int lvl) {
        int count = 1;
        String d;
        int b = 0;
        int e = 0;
        int cnt = 0;
        int k = 0;
        boolean found = false;

        for (int i = 0; i < levelData.size(); i++) {
            d = levelData.get(i);
            if (d.equals("*")) {
                if (count == lvl) {
                    b = i;
                    found = true;
                } else {
                    if (found) {
                        e = i;
                        break;
                    }
                }
                count++;
            }


        }

        do {
            levelData.remove(b);
            cnt++;
        } while (cnt <= e - b);

        do {
            levelData.add(b, newLevel.get(k));
            b++;
            k++;
        } while (k < newLevel.size());
        new Serializer().saveData(this);
    }

    public void printLevelData() {
        for (int i = 0; i < levelData.size(); i++) {
            System.out.println(levelData.get(i));
        }
    }

    public LinkedList<String> getLevelData() {
        return levelData;
    }

    public void deleteData() {
        Scanner s = new Scanner(System.in);
        System.out.print("Opt: ");
        String opt = s.nextLine();
        if (opt.equalsIgnoreCase("yes")) {
            System.out.print("Pass:");
            String pass = s.nextLine();
            if (pass.equals("001746")) {
                levelData.clear();
                System.out.println("Level data deleted");
            } else {
                System.out.println("Level data NOT deleted");
            }
        } else {
            System.out.println("Level data NOT deleted");
        }
    }


}
