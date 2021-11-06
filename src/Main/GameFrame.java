package Main;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public static void showOnScreen( int screen, JFrame frame, int w, int h) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();

        int x;

        if (screen > -1 && screen < gd.length) {
            x = gd[screen].getDefaultConfiguration().getBounds().x + (gd[screen].getDisplayMode().getWidth() - w) / 2;
            frame.setLocation(x, frame.getY());
        } else if (gd.length > 0) {
            x = gd[screen - 1].getDefaultConfiguration().getBounds().x + (gd[screen - 1].getDisplayMode().getWidth() - w) / 2;
            frame.setLocation(x, frame.getY());
        } else {
            throw new RuntimeException("No Screens Found");
        }
    }

    public GameFrame(){
        GamePanel gamePanel = new GamePanel();


        this.add(gamePanel);
        this.setTitle("Main.Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Used if have another monitor
        showOnScreen(1, this, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
//        this.setResizable(false);

        this.pack();
        this.setVisible(true);
//        this.setLocationRelativeTo(null);

    }

}
