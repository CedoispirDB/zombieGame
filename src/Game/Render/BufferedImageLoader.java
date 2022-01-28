package Game.Render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BufferedImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path) {
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
