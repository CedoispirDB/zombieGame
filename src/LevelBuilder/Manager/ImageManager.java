package LevelBuilder.Manager;

import Game.Render.BufferedImageLoader;
import Game.Render.CreateImages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class ImageManager {
    private final BufferedImage texture, playerSprite, zombieSprite, openedDoor, coin, pistol, health;
    private final CreateImages createImages;

    public ImageManager() {
        createImages = new CreateImages();

        BufferedImageLoader loader = new BufferedImageLoader();
        texture = loader.loadImage("/texture.png");
        playerSprite = loader.loadImage("/playerSprite.png");
        zombieSprite = loader.loadImage("/basicZombieSprite.png");
        openedDoor = loader.loadImage("/openedDoor.png");
        coin = loader.loadImage("/coin.png");
        pistol = loader.loadImage("/pistol_5.png");
        health = loader.loadImage("/health.png");


    }

    public BufferedImage getTexture(String opt) {
        BufferedImage image = null;
        switch (opt) {
            case "w" -> image = texture.getSubimage(0, 0, 32, 32);
            case "p" -> image = texture.getSubimage(96, 0, 32, 64);
            case "b" -> image = texture.getSubimage(64, 0, 32, 32);
            case "f" -> image = texture.getSubimage(32, 0, 32, 32);
            case "od" -> image = openedDoor;
            case "m" -> image = playerSprite.getSubimage(0, 0, 32, 32);
            case "z" -> image = zombieSprite.getSubimage(0, 0, 32, 32);
            case "c" -> image = coin;
            case "g" -> image = pistol;
            case "h" -> image = health;
        }

        return image;
    }

    public void loadImages(Graphics g) {
        LinkedList<BufferedImage> frames = createImages.createFrames(playerSprite, 0, 3, 1);
        for (int i = 0; i < frames.size(); i++) {
            g.drawImage(frames.get(i), 32 * i, 64, null);
        }
    }
}
