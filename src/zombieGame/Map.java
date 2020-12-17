package zombieGame;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Map {

    private final Handler handler;
    private final HUD hud;
    private final Game game;
    private Game.STATE firstState;

    private final BufferedImage castle;
    private final BufferedImage garden;
    private final BufferedImage floor;
    private BufferedImage c = null;


    public final SpriteSheet spriteSheet;
    private final SpriteSheet santaSkin;

    public float castleWidth = 1367;
    public float castleHeight = (30 * 64);

    public Map(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;

        BufferedImageLoader loader = new BufferedImageLoader();

        // Load castle
        castle = loader.loadImage("/CA.png");
        c = loader.loadImage("M.png");

        // Load garden level
        garden = loader.loadImage("/M.png");

        //load spriteSheet
        BufferedImage spriteSheetLayout = loader.loadImage("/B.png");
        spriteSheet = new SpriteSheet(spriteSheetLayout);

        //Load Santa
        BufferedImage santa = loader.loadImage("/C.png");
        santaSkin = new SpriteSheet(santa);

        firstState = game.gameState;

        floor = spriteSheet.grabImage(4, 2, 32, 32);


    }

    public void chooseLevel() {
        if (firstState != game.gameState) {
            firstState = game.gameState;
            if (game.gameState == Game.STATE.Garden) {
                loadLevel(garden);
            }
        }
    }

    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Garden || game.gameState == Game.STATE.Menu) {
            for (int x = 0; x < 30 * 72; x += 32) {
                for (int y = 0; y < 30 * 72; y += 32) {
                    g.drawImage(floor, x, y, null);

                }
            }
        } else if (game.gameState == Game.STATE.Castle) {
            for (int x = 0; x <  castleWidth; x += 32) {
                for (int y = 0; y < castleHeight; y += 32) {
                    g.drawImage(castle, x, y, null);

                }
            }

            g.setColor(Color.YELLOW);
            g.drawRect((Game.WIDTH / 2) - 100, (Game.HEIGHT / 2) - (int)199.99, 200,600);

            g.setColor(Color.RED);
            g.fillRect((Game.WIDTH / 2) - 100, (Game.HEIGHT / 2) - 200, 200,600);

        }

    }

    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pixel = image.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255) {
                    handler.addObject(new Block(game, x * 32, y * 32, ID.Block, handler, hud, spriteSheet));
                }

                if (blue == 255 && green == 0) {
                    handler.addObject(new Santa(game, x * 32, y * 32, ID.Santa, handler, hud, santaSkin, spriteSheet));
//                    handler.addObject(new Enemy(this, x * 32, y * 32, ID.Enemy, handler, hud, ss));

                }

                if (green == 255 && blue == 0) {
//                    handler.addObject(new Enemy(this, x * 32, y * 32, ID.Enemy, handler, hud, ss));
                }

                if (green == 255 && blue == 255) {
//                    handler.object.add(new Crate(this, x * 32, y * 32, ID.Crate, handler, hud, ss));
                }

            }
        }

    }

}
