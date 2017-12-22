package gameframework;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

/*
* klasa odpowiedzialna za tworzenie nowych kaczek
* */
public class DuckFactory {

    private final Canvas canvas;
    private final Image duckImage;
    private final Random random;

    public DuckFactory(Canvas canvas, Image duckImage) {
        this.canvas = canvas;
        this.duckImage = duckImage;
        this.random = new Random();

    }

    public Duck createDuck() {
        // zaczynamy od prawej strony ekranu
        double positionX = canvas.getWidth();
        double positionY = getRandomYPosition(duckImage.getHeight());
        return new Duck(positionX, positionY, getRandomSpeed());
    }

    //losujemy predkosc kaczki, mozliwe wartosci 3, 4, 5
    private int getRandomSpeed() {
        return random.nextInt(3) + 3;
    }

    //losujemy pozycje Y kaczki
    private double getRandomYPosition(double duckImageHeight) {
        // losujemy linie
        int lineNumber = random.nextInt(6) +6;
        //pozycja y to linia razy wysokosc obrazka
        return lineNumber * duckImageHeight;
 
    }
}
