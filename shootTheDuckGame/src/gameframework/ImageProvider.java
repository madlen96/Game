package gameframework;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// klasa odpowiedzialna za dostarczanie obrazkow
// pola static final zeby ladowaly sie tylko raz
public class ImageProvider {

    private static final Image DUCK_IMAGE = new Image(getResourceAsStream("resources/duck.png"));
    private static final Image CURSOR_IMAGE = new Image(getResourceAsStream("resources/cursor.png"));
    private static final Image BACKGROUND_IMAGE = new Image(getResourceAsStream("resources/background.jpg"));
    private static final Image START_BACKGROUND_IMAGE = new Image(getResourceAsStream("resources/startBackground.jpg"));
    private static final Image DESCRIPTION_BACKGROUND_IMAGE = new Image(getResourceAsStream("resources/descriptionBackground.png"));

    public static Image getDuckImage() {
        return DUCK_IMAGE;
    }

    public static Image getCursorImage() {
        return CURSOR_IMAGE;
    }

    public static Image getBackgroundImage() {
        return BACKGROUND_IMAGE;
    }

    public static Image getStartBackgroundImage() {
        return START_BACKGROUND_IMAGE;
    }

    public static Image getDescriptionBackground() {
        return DESCRIPTION_BACKGROUND_IMAGE;
    }

    public static BufferedImage getBufferedDuckImage() {
        try {
            return ImageIO.read(getResourceAsStream("resources/duck.png"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static InputStream getResourceAsStream(String fileName) {
        return ImageProvider.class.getClassLoader().getResourceAsStream(fileName);
    }

}
