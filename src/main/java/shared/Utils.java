package shared;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Utils {
    public static Image loadImageBijou(String path) {
        try {
            InputStream input = Utils.class.getClassLoader().getResourceAsStream(path);
            if (input != null) {
                BufferedImage image = ImageIO.read(input);
                return image;
            } else {
                throw new RuntimeException("icon " + path + " not found in the classpath!");
            }
        } catch (IOException e) {
            throw new RuntimeException("image " + path + " not found in the classpath!");
        }
    }
}
