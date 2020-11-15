package zombieGame;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {


    public void setSize(int width, int height) {

    }

    public void setSize(Dimension d) {
    }

    public void setBounds(int x, int y, int width, int height) {
    }

    public void setLocationRelativeTo(Component component) {
    }

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,screenSize.width, screenSize.height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
}
