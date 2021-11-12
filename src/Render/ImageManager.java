package Render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class ImageManager {

    private BufferedImage texture;
    private BufferedImage playerSprite;
    private BufferedImage explosionSprite;
    private BufferedImage zombieSprite;
    private BufferedImage bulletSprite;
    private CreateImages createImages;
    private Animation animation;

    public ImageManager() {
        createImages = new CreateImages();
        animation = new Animation();

        BufferedImageLoader loader = new BufferedImageLoader();
        texture = loader.loadImage("/texture.png");
        playerSprite = loader.loadImage("/playerSprite.png");
        explosionSprite = loader.loadImage("/explosion.png");
        zombieSprite = loader.loadImage("/basicZombie.png");
        bulletSprite = loader.loadImage("/bullet.png");



    }

    public BufferedImage getTexture(String opt) {
        BufferedImage image = null;
        switch (opt) {
            case "w" -> image = texture.getSubimage(0,0,32,32);
            case "p" -> image = texture.getSubimage(96,0,32,64);
            case "b" -> image = texture.getSubimage(64,0,32,32);
            case "f" -> image = texture.getSubimage(32, 0, 32, 32);
        }

        return image;
    }

    public BufferedImage getSprite(String opt) {
        BufferedImage image = null;
        switch (opt) {
            case "p" -> image = playerSprite;
            case "bz" -> image = zombieSprite;
            case "b" -> image = bulletSprite;
            case "e" -> image = explosionSprite;
        }

        return image;
    }


    public void loadImages(Graphics g) {
        LinkedList<BufferedImage> frames = createImages.createFrames(playerSprite, 0,3, 1);
        for (int i = 0; i < frames.size(); i++) {
            g.drawImage(frames.get(i), 32 * i, 64, null);
        }
    }



}
