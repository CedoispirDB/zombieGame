package Game.DataManager;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class SaveData {

    public void saveToFile(LinkedList<String> data) {
        try {
            FileWriter writer = new FileWriter("Data/level.txt", true);

            for (int i = 0; i < data.size(); i++) {
                if (i != data.size() - 1) {
                    writer.write(data.get(i) + " ");
                } else {
                    writer.write(data.get(i));
                }
            }
            writer.write("\n");
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public LinkedList<String> readFromFile(int level) {

        int count = 0;

        try {
            File file = new File("Data/level.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                if (count == level) {
                    return formatData(data);
                }

                count++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return null;
    }

    private LinkedList<String> formatData(String data) {

        LinkedList<String> info = new LinkedList<>();
        String newStr;
        String oldStr = data;
        int index;

        do {
            index = oldStr.indexOf(" ");
            if (index != -1) {
                newStr = oldStr.substring(0, index);
                oldStr = oldStr.substring(index + 1);
                info.add(newStr);
            } else {
                newStr = oldStr;
                info.add(newStr);
                break;
            }

        } while (oldStr.length() > 0);

        return info;
    }


}
