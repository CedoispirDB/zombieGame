package UI;

import DataManager.DataManager;
import Main.Game;
import Main.GamePanel;
import Manager.STATE;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class Leaderboard extends MouseAdapter {
    private final int gap = 100;
    private final int buttonWidth = 243;
    private final int buttonHeight = 60;
    private final int ox = (GamePanel.SCREEN_WIDTH - buttonWidth) / 2;
    private final int oy = (GamePanel.SCREEN_HEIGHT - buttonHeight) / 2;
    private Color backColor;
//    private ScoreManager scoreManager;
    private DataManager dataManager;

    public Leaderboard(DataManager dataManager) {
//        this.scoreManager = scoreManager;
        this.dataManager = dataManager;
        backColor = Color.WHITE;
    }

    public void mousePressed(MouseEvent e) {
        if (GamePanel.gameState == STATE.LEADERBOARD) {
            int mx = e.getX();
            int my = e.getY();


            if (mouseOver(mx, my, ox, GamePanel.SCREEN_HEIGHT - buttonHeight - 20, buttonWidth, buttonHeight )) {
                backColor = Color.GRAY;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (GamePanel.gameState == STATE.LEADERBOARD) {
            int mx = e.getX();
            int my = e.getY();


            if (mouseOver(mx, my, ox, GamePanel.SCREEN_HEIGHT - buttonHeight - 20, buttonWidth, buttonHeight)) {
                GamePanel.gameState = STATE.MENU;
                backColor = Color.WHITE;
            }
        }
    }

    public void render(Graphics g) {

        Font fnt2 = new Font("arial", Font.BOLD, 35);


        Graphics2D g2d = (Graphics2D) g;

        float thickness = 5;
        g2d.setStroke(new BasicStroke(thickness));
        Font fnt = new Font("Serif", Font.BOLD, 60);

        g.setColor(new Color(90, 90, 90).darker());
        g.fillRect(0, 20, GamePanel.SCREEN_WIDTH, 95);

        String title = "Leaderboard";
        FontRenderContext frc = g2d.getFontRenderContext();
        int titleWidth = (int) fnt.getStringBounds(title, frc).getWidth();
        int titleHeight = (int) fnt.getStringBounds(title, frc).getHeight();

        g.setColor(Color.white);
        g.setFont(fnt);
        g.drawString(title, (GamePanel.SCREEN_WIDTH / 2) - (titleWidth / 2), 115 - (115 - (titleHeight)) / 2);

        g.fillRect(ox - 100, 150, buttonWidth + 200,   588 - buttonHeight);
        g.setColor(backColor);
        g.fillRect(ox, GamePanel.SCREEN_HEIGHT - buttonHeight - 20, buttonWidth, buttonHeight);

        g.setColor(Color.black);
        g.drawRect(ox - 100, 150, buttonWidth + 200,   588 - buttonHeight);
        g.setFont(fnt2);

        int yi = oy + (int) fnt2.getStringBounds("Play", frc).getHeight() - 5 + ((buttonHeight - (int) fnt2.getStringBounds("Play", frc).getHeight()) / 2) - 1;


        g.drawRect(ox, GamePanel.SCREEN_HEIGHT - buttonHeight - 20, buttonWidth, buttonHeight);
        g.drawString("Back", ox + ((buttonWidth - (int) fnt2.getStringBounds("Back", frc).getWidth()) / 2), GamePanel.SCREEN_HEIGHT - buttonHeight  + 23);

        // scoreboard
        String leaderboard = dataManager.getLeaderboard(10);
        int y = 200;
        for (String line : leaderboard.split("\n")) {
            g.drawString(line, (GamePanel.SCREEN_WIDTH / 2) - (titleWidth / 2), y);
            y = y  + 50;
        }

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }
}
