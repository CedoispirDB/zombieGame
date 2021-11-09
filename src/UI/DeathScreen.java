package UI;

import DataManager.ScoreManager;
import Main.Game;
import Main.GamePanel;
import Manager.STATE;
import Player.Interface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class DeathScreen extends MouseAdapter {

    private int gap = 100;
    private int buttonWidth = 243;
    private int buttonHeight = 78;
    private int ox = (GamePanel.SCREEN_WIDTH - buttonWidth) / 2;
    private int oy = (GamePanel.SCREEN_HEIGHT - buttonHeight) / 2;
    private final Interface anInterface;
    private final ScoreManager scoreManager;
    private final GamePanel gamePanel;
    private String input;
    private Color inputColor;
    private boolean canWrite;
    private Color submitColor;
    private String leaderboard;
    private boolean nameAvailable;

    public DeathScreen(Interface anInterface, ScoreManager scoreManager, GamePanel gamePanel) {
        this.anInterface = anInterface;
        this.scoreManager = scoreManager;
        this.gamePanel = gamePanel;
        input = "";
        inputColor = Color.WHITE;
        submitColor = Color.WHITE;
        canWrite = false;
    }

    public void reloadString(String c, String opt) {
        if (canWrite) {
            if (opt.equals("a")) {
                input = input + c;
            } else if (opt.equals("r")) {
                if (!input.equals("")) {
                    input = input.substring(0, input.length() - 1);
                }
            } else {
                System.out.println("Option not valid");
            }
        }
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if (GamePanel.gameState == STATE.DEATH) {
            if (mouseOver(mx, my, 673, 170, 50, 45)) {
                submitColor = Color.GRAY;
                nameAvailable = scoreManager.saveScore(anInterface.getScore(), input);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (GamePanel.gameState == STATE.DEATH) {
            int mx = e.getX();
            int my = e.getY();


            if (mouseOver(mx, my, 363, 170, 300, 45)) {
                inputColor = Color.red.darker();
                canWrite = true;
            }

            if (mouseOver(mx, my, 673, 170, 50, 45)) {
                submitColor = Color.WHITE;
                if (nameAvailable) {
                    inputColor = Color.WHITE;
                    scoreManager.saveScore(anInterface.getScore(), input);
                    leaderboard = scoreManager.getLeaderboard(10);
                    input = "";
                }
            }
        }

    }

    public void render(Graphics g) {
        Font fnt = new Font("Serif", Font.BOLD, 60);
        Font fnt2 = new Font("Arial", Font.BOLD, 30);
        Font fnt3 = new Font("Arial", Font.BOLD, 25);
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2;
        g2d.setStroke(new BasicStroke(thickness));
        FontRenderContext frc = g2d.getFontRenderContext();

        // Painting background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        // Death message
        g.setColor(Color.red);
        g.setFont(fnt);
        g.drawString("YOU DIED", ox + ((buttonWidth - (int) fnt.getStringBounds("YOU DIED", frc).getWidth()) / 2), 100);

        // Rest
        int pos = ox + ((buttonWidth - (int) fnt.getStringBounds("YOU DIED", frc).getWidth()) / 2);
        g.setColor(new Color(1f, 1f, 1f, 0.9f));
        g.fillRect(pos, 170, 300, 45);
        g.setColor(inputColor);
        g.drawRect(pos, 170, 300, 45);
        g.setColor(Color.BLACK);
        g.setFont(fnt3);
        g.drawString(input, pos + 10 , 203);

        // Submit button
        g.setColor(submitColor);
        g.fillRect(pos + 310, 170, 50 ,45);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("Add", pos + 315, 200);

        // Total score
        g.setColor(Color.white);
        g.setFont(fnt2);
        String text = "Total Score: " + anInterface.getScore();
        g.drawString(text, ox + ((buttonWidth - (int) fnt2.getStringBounds(text, frc).getWidth()) / 2), 150);


        // scoreboard
        leaderboard = scoreManager.getLeaderboard(10);
        int y = 260;
        for (String line : leaderboard.split("\n")) {
            g.drawString(line, ox + ((buttonWidth - (int) fnt.getStringBounds("YOU DIED", frc).getWidth()) / 2), y);
            y = y  + 50;
        }


//        System.out.println(input);
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }
}
