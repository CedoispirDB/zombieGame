package DataManager;

public class Test {
    public static void main(String[] args) {
        DataManager dataManager = new Deserializer().loadData();
        System.out.println(dataManager.getScores());
        dataManager.sort(dataManager.getScores());
    }
}
