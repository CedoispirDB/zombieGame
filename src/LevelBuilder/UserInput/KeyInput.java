package LevelBuilder.UserInput;

import LevelBuilder.Builder.LevelBuilder;
import LevelBuilder.DataManager.DataManager;
import LevelBuilder.Main.GamePanel;
import LevelBuilder.UI.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private LinkedList<String> options = new LinkedList<>();
    private int typeIndex;
    private boolean one, two;


    private LevelBuilder levelBuilder;
    private DataManager dataManager;

    public KeyInput(LevelBuilder levelBuilder, DataManager dataManager) {
        this.levelBuilder = levelBuilder;
        this.dataManager = dataManager;
        options = new LinkedList<>();
        options.add("w");
        options.add("p");
        options.add("b");
        options.add("m");
        options.add("z");
        options.add("c");
        options.add("g");
        options.add("h");

        typeIndex = 1;

        one = false;
        two = false;

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER) {
            GamePanel.canInput = !GamePanel.canInput;
        }

        if (!GamePanel.canInput){
        switch (code) {
            case KeyEvent.VK_V -> {
                levelBuilder.setType(options.get(typeIndex));
                typeIndex++;

                if (typeIndex == options.size()) {
                    typeIndex = 0;
                }
            }
            case KeyEvent.VK_B -> levelBuilder.toggleDrag();
            case KeyEvent.VK_J -> levelBuilder.reset();
            case KeyEvent.VK_Z -> levelBuilder.undo();
            case KeyEvent.VK_K -> levelBuilder.save();
            case KeyEvent.VK_P -> dataManager.printLevelData();
            case KeyEvent.VK_L -> levelBuilder.load(Input.getNum());

        }

        // 157 16 68
        if (code == 157) {
            one = true;

        }

        if (one && code != 157 && code != 68) {
            if (code == 16 && !two) {
                two = true;
            } else {
                one = false;
                two = false;
            }
        }

        if (two && code != 16) {
            if (code == 68) {
                dataManager.deleteData();
            }
            one = false;
            two = false;
        }
    }
    }
}
