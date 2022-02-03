package LevelBuilder.DataManager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Scanner;

public class DataManager implements Serializable {

    private final LinkedList<String> levelData;


    public DataManager() {
        levelData = new LinkedList<>();
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

    public void insert(String str, int lvl) {
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
    }

    public void getLevelData() {
        for (int i = 0; i < levelData.size(); i++) {
            System.out.println(levelData.get(i));
        }
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

    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        dataManager.saveData("* 128 192 32 384 w 480 64 32 32 b 960 320 32 32 p 160 352 256 32 w 864 192 32 384 w 608 352 256 32 w 352 512 320 32 w 352 224 320 32 w 64 320 32 32 g 608 672 32 32 c 384 672 32 32 c 608 64 32 32 c 384 64 32 32 c 480 128 32 32 z 512 640 32 32 z 480 352 32 32 m * 480 352 32 32 b 384 256 224 32 w 640 672 32 32 g 576 288 32 192 w 256 448 320 32 w 256 160 32 288 w 288 160 416 32 w 672 192 32 384 w 160 544 512 32 w 160 64 32 480 w 192 64 608 32 w 768 96 32 480 w 480 640 32 32 p 512 352 32 32 c 512 320 32 32 c 512 384 32 32 c 448 352 32 32 c 448 320 32 32 c 448 384 32 32 c 480 320 32 32 c 480 384 32 32 c 384 352 32 32 z 640 384 32 32 z 224 288 32 32 z 608 128 32 32 z 736 416 32 32 z 288 640 32 32 m * 640 256 32 32 w 320 256 32 32 w 320 352 32 32 w 480 320 32 32 p 480 0 32 224 w 320 352 32 128 w 640 352 32 128 w 352 448 288 32 w 320 224 320 32 w 640 224 32 32 w 480 480 32 192 w 512 640 160 32 w 320 640 160 32 w 96 544 32 224 w 128 544 256 32 w 896 544 32 224 w 608 544 288 32 w 64 64 320 32 w 64 96 32 224 w 96 288 160 32 w 608 64 352 32 w 928 96 32 224 w 736 288 192 32 w 192 160 64 32 w 768 160 64 32 w 480 704 32 32 b 832 640 32 32 c 160 640 32 32 c 128 128 32 32 c 864 128 32 32 c 960 704 32 32 c 32 704 32 32 c 544 64 32 32 h 416 64 32 32 h 32 32 32 32 g 128 416 32 32 z 832 416 32 32 z 800 640 32 32 z 192 640 32 32 z 192 224 32 32 z 800 224 32 32 z 544 320 32 32 m * 160 0 32 32 w 160 32 32 32 w 160 64 32 32 w 160 96 32 32 w 160 128 32 32 w 160 160 32 32 w 192 160 32 32 w 224 160 32 32 w 256 160 32 32 w 288 160 32 32 w 288 288 32 32 w 288 320 32 32 w 288 352 32 32 w 288 384 32 32 w 288 416 32 32 w 256 416 32 32 w 224 416 32 32 w 192 416 32 32 w 288 448 32 32 w 288 480 32 32 w 288 512 32 32 w 288 544 32 32 w 320 544 32 32 w 352 544 32 32 w 384 544 32 32 w 704 192 32 32 w 672 192 32 32 w 608 192 32 32 w 640 192 32 32 w 576 192 32 32 w 544 192 32 32 w 544 160 32 32 w 544 160 32 32 w 544 128 32 32 w 544 96 32 32 w 544 64 32 32 w 544 32 32 32 w 544 0 32 32 w 512 64 32 32 w 480 64 32 32 w 448 64 32 32 w 416 64 32 32 w 416 160 32 32 w 416 192 32 32 w 416 224 32 32 w 416 256 32 32 w 416 288 32 32 w 0 640 32 32 w 0 640 32 32 w 32 640 32 32 w 64 640 32 32 w 96 640 32 32 w 128 640 32 32 w 128 608 32 32 w 128 576 32 32 w 128 672 32 32 w 32 32 32 32 b 992 384 32 32 p 704 224 32 32 w 704 288 32 32 w 704 256 32 32 w 576 512 32 32 w 608 512 32 32 w 640 512 32 32 w 672 512 32 32 w 704 512 32 32 w 736 512 32 32 w 672 480 32 32 w 672 448 32 32 w 672 416 32 32 w 608 544 32 32 w 608 576 32 32 w 608 608 32 32 w 736 704 32 32 w 800 704 32 32 w 768 704 32 32 w 864 704 32 32 w 832 704 32 32 w 736 704 32 32 w 736 672 32 32 w 736 608 32 32 w 736 640 32 32 w 864 384 32 32 w 864 416 32 32 w 864 416 32 32 w 864 448 32 32 w 864 480 32 32 w 896 480 32 32 w 928 480 32 32 w 960 480 32 32 w 992 480 32 32 w 864 0 32 32 w 864 32 32 32 w 864 64 32 32 w 864 96 32 32 w 864 128 32 32 w 832 128 32 32 w 800 128 32 32 w 896 128 32 32 w 896 160 32 32 w 896 192 32 32 w 896 224 32 32 w 736 224 32 32 w 768 224 32 32 w 640 160 32 32 w 640 128 32 32 w 832 32 32 32 w 768 32 32 32 w 736 32 32 32 w 704 32 32 32 w 800 32 32 32 w 320 672 32 32 w 352 672 32 32 w 384 672 32 32 w 416 672 32 32 w 448 672 32 32 w 480 672 32 32 w 384 416 32 32 w 416 416 32 32 w 480 416 32 32 w 448 416 32 32 w 512 416 32 32 w 512 320 32 32 w 576 320 32 32 w 544 320 32 32 w 0 256 32 32 w 32 256 32 32 w 64 256 32 32 w 96 256 32 32 w 96 288 32 32 w 96 288 32 32 w 96 320 32 32 w 96 320 32 32 w 96 352 32 32 w 96 384 32 32 w 96 384 32 32 w 0 480 32 32 w 32 480 32 32 w 96 480 32 32 w 64 480 32 32 w 192 64 32 32 w 224 64 32 32 w 256 64 32 32 w 288 64 32 32 w 608 32 32 32 h 32 352 32 32 h 672 576 32 32 h 640 64 32 32 z 864 320 32 32 z 512 608 32 32 z 96 96 32 32 z 160 544 32 32 z 288 224 32 32 z 352 352 32 32 m *");
    }

}
