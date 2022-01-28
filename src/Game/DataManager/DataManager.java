package Game.DataManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DataManager implements Serializable {

//    * means the start of a new level;

    private  LinkedHashMap<String, Integer> scores;
    private  LinkedList<String> levelData;

    public DataManager() {
        scores = new LinkedHashMap<>();
        levelData = new LinkedList<>();
    }

    public  LinkedList<String> loadLevel(int l) {
        LinkedList<String> data = new LinkedList<>();
        int count = 1;
        int index = 0;
        String c;

        do {
            c = levelData.get(index);
//            System.out.println("c: " + c);

            if (count == l) {
                if (!c.equals("*")) {
                    data.add(c);
                } else {
                    break;
                }
            }

            if (c.equals("*")) {
                count++;
            }

            index++;

        } while (true);

        return data;
    }

    public  void saveLevel(String str) {
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

    }

    public  boolean saveScore(String name, int s) {
        if (!scores.containsKey(name)) {
            scores.put(name, s);
            sort(scores);
            new Serializer().saveData(this);
            return true;
        } else {
            return false;
        }
    }

    public void clearScore() {
        scores.clear();
    }


    public boolean removeScore(String name) {
        if (scores.containsKey(name)) {
            scores.remove(name);
            return true;
        } else {
            return false;
        }
    }

    public  int getHighestScore() {
        return scores.get(new LinkedList<>(scores.keySet()).get(0));

    }

    public String getLeaderboard(int limit) {
        LinkedList<String> keys = new LinkedList<>(scores.keySet());

        if (scores.size() < limit) {
            limit = scores.size();
        }

        String board = "";
        String key;
        int points;

        for (int i = 0; i < limit; i++) {
            key = keys.get(i);
            points = scores.get(key);
            board = board + (i + 1) + ") " + key + " - " + points + " points\n";
        }

        return board;
    }

    public void printData() {
        printLevelData();
        printScores();

    }

    public void printLevelData() {
        System.out.println("Level Data: ");
        levelData.forEach(System.out :: println);
    }
    public void printScores() {
        System.out.println("Scores: " + scores);
    }

    public LinkedHashMap<String, Integer> getScores() {
        return scores;
    }

    public LinkedList<String> getLevelData() {
        return levelData;
    }

    public HashMap<String, Integer> sort(HashMap<String, Integer> map) {
        LinkedList<String> keys = new LinkedList<>(map.keySet());
        LinkedList<String> names = new LinkedList<>();
        LinkedList<Integer> scores = new LinkedList<>();
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();

        String name;
        int score;
        int index = 0;

        for (int i = 0; i < keys.size(); i++) {
            name = keys.get(i);
            score = map.get(name);

            while (true) {
                if (index >= scores.size())  {
                    scores.add(score);
                    names.add(name);
                    index = 0;
                    break;
                }

                if (score > scores.get(index)) {
                    scores.add(index, score);
                    names.add(index, name);
                    index = 0;
                    break;
                } else {
                    index++;
                }
            }

        }

        for (int i = 0; i < scores.size(); i++) {
            result.put(names.get(i), scores.get(i));
        }


        this.scores = result;

        return result;
    }

    public static class run {
        public static void main(String[] args) {
//            DataManager dataManager = new DataManager();
//            dataManager.saveLevel("128 192 32 384 w 480 64 32 32 b 960 320 32 32 p 160 352 256 32 w 864 192 32 384 w 608 352 256 32 w 352 512 320 32 w 352 224 320 32 w 64 320 32 32 g 608 672 32 32 c 384 672 32 32 c 608 64 32 32 c 384 64 32 32 c 480 128 32 32 z 512 640 32 32 z 480 352 32 32 m * 480 352 32 32 b 384 256 224 32 w 640 672 32 32 g 576 288 32 192 w 256 448 320 32 w 256 160 32 288 w 288 160 416 32 w 672 192 32 384 w 160 544 512 32 w 160 64 32 480 w 192 64 608 32 w 768 96 32 480 w 480 640 32 32 p 512 352 32 32 c 512 320 32 32 c 512 384 32 32 c 448 352 32 32 c 448 320 32 32 c 448 384 32 32 c 480 320 32 32 c 480 384 32 32 c 384 352 32 32 z 640 384 32 32 z 224 288 32 32 z 608 128 32 32 z 736 416 32 32 z 288 640 32 32 m * 640 256 32 32 w 320 256 32 32 w 320 352 32 32 w 480 320 32 32 p 480 0 32 224 w 320 352 32 128 w 640 352 32 128 w 352 448 288 32 w 320 224 320 32 w 640 224 32 32 w 480 480 32 192 w 512 640 160 32 w 320 640 160 32 w 96 544 32 224 w 128 544 256 32 w 896 544 32 224 w 608 544 288 32 w 64 64 320 32 w 64 96 32 224 w 96 288 160 32 w 608 64 352 32 w 928 96 32 224 w 736 288 192 32 w 192 160 64 32 w 768 160 64 32 w 480 704 32 32 b 832 640 32 32 c 160 640 32 32 c 128 128 32 32 c 864 128 32 32 c 960 704 32 32 c 32 704 32 32 c 544 64 32 32 h 416 64 32 32 h 32 32 32 32 g 128 416 32 32 z 832 416 32 32 z 800 640 32 32 z 192 640 32 32 z 192 224 32 32 z 800 224 32 32 z 544 320 32 32 m * 160 0 32 32 w 160 32 32 32 w 160 64 32 32 w 160 96 32 32 w 160 128 32 32 w 160 160 32 32 w 192 160 32 32 w 224 160 32 32 w 256 160 32 32 w 288 160 32 32 w 288 288 32 32 w 288 320 32 32 w 288 352 32 32 w 288 384 32 32 w 288 416 32 32 w 256 416 32 32 w 224 416 32 32 w 192 416 32 32 w 288 448 32 32 w 288 480 32 32 w 288 512 32 32 w 288 544 32 32 w 320 544 32 32 w 352 544 32 32 w 384 544 32 32 w 704 192 32 32 w 672 192 32 32 w 608 192 32 32 w 640 192 32 32 w 576 192 32 32 w 544 192 32 32 w 544 160 32 32 w 544 160 32 32 w 544 128 32 32 w 544 96 32 32 w 544 64 32 32 w 544 32 32 32 w 544 0 32 32 w 512 64 32 32 w 480 64 32 32 w 448 64 32 32 w 416 64 32 32 w 416 160 32 32 w 416 192 32 32 w 416 224 32 32 w 416 256 32 32 w 416 288 32 32 w 0 640 32 32 w 0 640 32 32 w 32 640 32 32 w 64 640 32 32 w 96 640 32 32 w 128 640 32 32 w 128 608 32 32 w 128 576 32 32 w 128 672 32 32 w 32 32 32 32 b 992 384 32 32 p 704 224 32 32 w 704 288 32 32 w 704 256 32 32 w 576 512 32 32 w 608 512 32 32 w 640 512 32 32 w 672 512 32 32 w 704 512 32 32 w 736 512 32 32 w 672 480 32 32 w 672 448 32 32 w 672 416 32 32 w 608 544 32 32 w 608 576 32 32 w 608 608 32 32 w 736 704 32 32 w 800 704 32 32 w 768 704 32 32 w 864 704 32 32 w 832 704 32 32 w 736 704 32 32 w 736 672 32 32 w 736 608 32 32 w 736 640 32 32 w 864 384 32 32 w 864 416 32 32 w 864 416 32 32 w 864 448 32 32 w 864 480 32 32 w 896 480 32 32 w 928 480 32 32 w 960 480 32 32 w 992 480 32 32 w 864 0 32 32 w 864 32 32 32 w 864 64 32 32 w 864 96 32 32 w 864 128 32 32 w 832 128 32 32 w 800 128 32 32 w 896 128 32 32 w 896 160 32 32 w 896 192 32 32 w 896 224 32 32 w 736 224 32 32 w 768 224 32 32 w 640 160 32 32 w 640 128 32 32 w 832 32 32 32 w 768 32 32 32 w 736 32 32 32 w 704 32 32 32 w 800 32 32 32 w 320 672 32 32 w 352 672 32 32 w 384 672 32 32 w 416 672 32 32 w 448 672 32 32 w 480 672 32 32 w 384 416 32 32 w 416 416 32 32 w 480 416 32 32 w 448 416 32 32 w 512 416 32 32 w 512 320 32 32 w 576 320 32 32 w 544 320 32 32 w 0 256 32 32 w 32 256 32 32 w 64 256 32 32 w 96 256 32 32 w 96 288 32 32 w 96 288 32 32 w 96 320 32 32 w 96 320 32 32 w 96 352 32 32 w 96 384 32 32 w 96 384 32 32 w 0 480 32 32 w 32 480 32 32 w 96 480 32 32 w 64 480 32 32 w 192 64 32 32 w 224 64 32 32 w 256 64 32 32 w 288 64 32 32 w 608 32 32 32 h 32 352 32 32 h 672 576 32 32 h 640 64 32 32 z 864 320 32 32 z 512 608 32 32 z 96 96 32 32 z 160 544 32 32 z 288 224 32 32 z 352 352 32 32 m *");
//            dataManager.saveScore("Leo", 40);
//            dataManager.saveScore("Marco", 350);
//            dataManager.saveScore("Bia", 500);
//            dataManager.saveScore("Lili", 150);
//            dataManager.sort(dataManager.getScores());
//            new Serializer().saveData(dataManager);
            DataManager dataManager = new Deserializer().loadData();
            dataManager.printLevelData();
        }
    }

}
