package LevelBuilder.UserInput;

import LevelBuilder.Builder.LevelBuilder;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter {

    private LinkedList<String> options = new LinkedList<>();
    private int typeIndex;

    private LevelBuilder levelBuilder;

    public KeyInput(LevelBuilder levelBuilder) {
        this.levelBuilder = levelBuilder;
        options = new LinkedList<>();
        options.add("w");
        options.add("cd");
        options.add("b");
        options.add("p");
        options.add("z");
        options.add("c");

        typeIndex = 1;
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_V -> {
                levelBuilder.setType(options.get(typeIndex));
                typeIndex++;

                if (typeIndex == options.size()) {
                    typeIndex = 0;
                }
            }
            case KeyEvent.VK_B -> levelBuilder.toggleDrag();
        }
    }
}
