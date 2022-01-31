package LevelBuilder.Objects;

import LevelBuilder.Manager.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private BufferedImage image;
    private int x,y, w,h;

    public Entity(String type, int x, int y, int w, int h, ImageManager manager) {
        image = manager.getTexture(type);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    public void render(Graphics g) {

        if(w == 32 && h == 32) {
            g.drawImage(image, x, y, null);
        } else {

            if (w != 32 ) {

                for (int i = x; i <= x + w - 32; i += 32) {
                    g.drawImage(image,  i,  y, null);
                }
            }

            if (h != 32) {
                for (int i = y; i <= y + h - 32; i += 32) {
                    g.drawImage(image, x,  i, null);
                }
            }
        }
    }
}
