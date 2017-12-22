package gameframework;

import javafx.scene.image.Image;

public class DuckPositionCalculator {

    private final Image duckImage = ImageProvider.getDuckImage();

    public void moveDuck(Duck duck) {
        double positionX = duck.getPositionX() - duck.getSpeed();
        duck.setPositionX(positionX);
    }

    public boolean duckIsOutOfTheScreen(Duck duck) {
        return duck.getPositionX() < 0 && Math.abs(duck.getPositionX())  > (duckImage.getWidth() / 2);
    }
}
