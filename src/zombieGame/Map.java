package zombieGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map {

    private int i = 0;
    private final Handler handler;
    private final HUD hud;
    private final Game game;
    private final Passages passages;
    private Game.STATE firstState;

    private final BufferedImage garden;
    private final BufferedImage floor;
    private final BufferedImage castle;

    public final SpriteSheet spriteSheet;
    private final SpriteSheet santaSkin;

    public float castleWidth = 1367;
    public float castleHeight = 731;

    public Map(Game game, Handler handler, HUD hud) {
        this.game = game;
        this.handler = handler;
        this.hud = hud;

        passages = new Passages(game, this, handler, hud);

        BufferedImageLoader loader = new BufferedImageLoader();

        // Load castle
        castle = loader.loadImage("/D.png");

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
            loadLevel(garden);
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

            passages.check();
            g.drawImage(castle, 0, 0, null);

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
                if (game.gameState == Game.STATE.Garden) {
                    if (red == 255) {
                        handler.addObject(new Block(game, x * 32, y * 32, ID.Block, handler, hud, spriteSheet));
                    }
                } else if (game.gameState == Game.STATE.Castle) {
                    if (blue == 255 && green == 0) {
//                        handler.addObject(new DemonKing(game, x * 20, y * 20, ID.Player, handler, hud, null, this));
                    }
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
