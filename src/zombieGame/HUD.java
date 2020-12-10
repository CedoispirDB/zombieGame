package zombieGame;


import java.awt.*;

public class HUD {

    public static float HEALTH = 100;
    public static float santaHP = 500;
    public static float healthColor = santaHP;
    public static int redValue;
    public static int manaSize = 200;

    private float greenValue = 102;

    private final Color manaColor = new Color(0, 255, 255);

    private int score;
    private final Game game;

    public HUD(Game game) {
        this.game = game;


    }

    public void tick() {

        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);
        greenValue = HEALTH * 2;

        redValue = (int) (healthColor - 245);

    }

    public void render(Graphics g) {
        // Health bar
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, (int) greenValue, 0).darker());
        g.fillRect(15, 15, (int) (HEALTH * 2), 32);
        g.setColor(Color.black);
        g.drawRect(15, 15, 200, 32);

        // Mana
        g.setColor(Color.gray);
        g.fillRect(15, 50, 200, 16);
        g.setColor(manaColor);
        g.fillRect(15, 50,  manaSize, 16);
        g.setColor(Color.black);
        g.drawRect(15, 50, 200, 16);


        // Boss fight
        if (Game.started) {
            Font fnt = new Font("arial", Font.BOLD, 15);
            g.setColor(Color.gray);
            g.fillRect((Game.WIDTH / 2) - 95, 20, 200, 32);
            g.setColor(new Color(redValue, 0, 0));
            g.fillRect((Game.WIDTH / 2) - 95, 20, (int) healthColor - 300, 32);
            g.setColor(Color.black);
            g.drawRect((Game.WIDTH / 2) - 95, 20, 200, 32);
            g.setFont(fnt);
            g.drawString("Santa, Lord of Coal", (Game.WIDTH / 2) - 60, 15);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
