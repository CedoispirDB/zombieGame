//package Game.BuilderManager;
//
//import java.io.*;
//import java.net.URISyntaxException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class ScoreManager {
//
//
//    public ScoreManager() {
//    }
//
//    private void saveToFile(LinkedHashMap<String, Integer> map) {
//        LinkedList<String> keys = new LinkedList<>(map.keySet());
//
//        try {
//            FileWriter writer = new FileWriter("Data/scores.txt", false);
//
//            for (int i = 0; i < keys.size(); i++) {
//                writer.write(keys.get(i) + " ");
//                writer.write(String.valueOf(map.get(keys.get(i))));
//                writer.write("\n");
//            }
//            writer.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    private LinkedHashMap<String, Integer> readFromFile() {
//        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
//
//
//
//        try {
//            File file = new File("Data/scores.txt");
//            Scanner reader = new Scanner(file);
//            while (reader.hasNextLine()) {
//                String data = reader.nextLine();
//                if (!data.equals("") && !data.equals("\n")) {
//                    String[] arr = data.split(" ");
//                    map.put(arr[0], Integer.valueOf(arr[1]));
//                }
//            }
//
//            reader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        return map;
//    }
//
//    public boolean saveScore(int score, String name) {
//        LinkedHashMap<String, Integer> scores = readFromFile();
//        Set<String> tempKeys = scores.keySet();
//        LinkedHashMap<String, Integer> newScores = new LinkedHashMap<>();
//
//        if(scores.containsKey(name)) {
//            return false;
//        }
//
//        int index = 0;
//        int count = 0;
//        int i = 0;
//
//        LinkedList<String> keys = new LinkedList<>(tempKeys);
//
//
//        for (String key : keys) {
//            int temp = scores.get(key);
//            if (temp < score) {
//                break;
//            }
//            index++;
//        }
//
//        if (index >= keys.size()) {
//            scores.put(name, score);
//            saveToFile(scores);
//        } else {
//
//            do {
//
//
//                if (count == index) {
//                    newScores.put(name, score);
//                } else {
//                    String key = keys.get(i);
//                    int temp = scores.get(key);
//                    newScores.put(key, temp);
//                    i++;
//                }
//
//                count++;
//
//
//            } while (i < keys.size());
//
//
//            saveToFile(newScores);
//        }
//
//        return true;
//    }
//
//    public String getLeaderboard(int limit) {
//        LinkedHashMap<String, Integer> scores = readFromFile();
//        LinkedList<String> keys = new LinkedList<>(scores.keySet());
//
//        if (scores.size() < limit) {
//            limit = scores.size();
//        }
//
//        String board = "";
//
//        for (int i = 0; i < limit; i++) {
//            int temp = scores.get(keys.get(i));
//            board = board + (i + 1) + ") " + keys.get(i) + " - " + temp + " points\n";
//        }
//
//        return board;
//    }
//
//    public String getUpdatedLeaderboard() {
//        return null;
//    }
//
//    public boolean newHighest(int score) {
//        String data = "0";
//
//        try {
//            File file = new File("Data/scores.txt");
//            Scanner reader = new Scanner(file);
//            data = reader.nextLine();
//
//            reader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//
//        return score > Integer.parseInt(data.substring(data.indexOf(" ") + 1));
//    }
//
//    private void read() throws IOException, URISyntaxException {
//        String path = new File(ScoreManager.class.getProtectionDomain().getCodeSource().getLocation()
//                .toURI()).getPath();
//        System.out.println(path);
//        String actual = "." + path + "/scores.txt";
//        System.out.println(actual);
//        InputStream inputStream = ScoreManager.class.getResourceAsStream(actual);
//        System.out.println(inputStream);
//        assert inputStream != null;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String contents = reader.lines()
//                .collect(Collectors.joining(System.lineSeparator()));
//        System.out.println(contents);
//    }
//
////    public static void main(String[] args) throws IOException, URISyntaxException {
////        new ScoreManager().read();
////    }
//}
