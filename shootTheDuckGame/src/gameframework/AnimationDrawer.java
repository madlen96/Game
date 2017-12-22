package gameframework;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
Klasa odpowiadająca za rysowanie ekranu gry, rozszerza ona AnimationTimer, silnik javy FX woła około 60 razy na sekundę metodę handle
przez co pozwala nam odrysować ekran
* */
public class AnimationDrawer extends AnimationTimer {

    // maksymalna liczba kaczek na ekranie
    private static final int MAX_DUCKS_COUNT = 15;
    //czas pomiedzy pojawieniem sie kolejnej kaczki
    private static final long TIME_BETWEEN_DUCKS = TimeUnit.MILLISECONDS.toNanos(500);

    private final DuckPositionCalculator duckPositionCalculator;
    private final Canvas canvas;
    private final List<Duck> ducks;
    private final Image duckImage;
    private final GameResultHolder gameResultHolder;
    private final DuckFactory duckFactory;

    private long lastAddDuckTime = 0;

    public AnimationDrawer(Canvas canvas, GameResultHolder gameResultHolder, List<Duck> ducks) {
        this.canvas = canvas;
        this.gameResultHolder = gameResultHolder;
        this.ducks = ducks;
        this.duckPositionCalculator = new DuckPositionCalculator();
        this.duckImage = ImageProvider.getDuckImage();
        this.duckFactory = new DuckFactory(canvas, duckImage);

    }
    /* nadpisanie metody z interfejsu AnimationTimer*/
    @Override 
    public void handle(long now) {
        if (canvasIsInitialized()) {
            if (shouldAddNewDuck(now)) {
                ducks.add(duckFactory.createDuck());
                lastAddDuckTime = now;
            }
            /*
            * używam iteratora zamiast petli for żeby móc usuwać kaczki, nie można usuwać z pętli for
            * */
            Iterator<Duck> duckIterator = ducks.iterator();
            /*
            * czyścimy ekran z kaczek
            * */
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            while (duckIterator.hasNext()) {
                Duck duck = duckIterator.next();
                updateDuckPosition(duck);
                /*
                * sprawdzamy czy kaczka jest poza ekranem
                * */
                if (duckPositionCalculator.duckIsOutOfTheScreen(duck)) {
                    /*
                    * uciekla wiec aktualizujemy wynik
                    * */
                    gameResultHolder.duckMissed();
                    /*
                    * i ja usuwamy
                    * */
                    duckIterator.remove();
                }
            }
        }
    }

    /*
    * metoda sprawdza czy ramka z grą się już załadowała
    * */
    private boolean canvasIsInitialized() {
        return canvas.getHeight() > 0 && canvas.getWidth() > 0;
    }

    /*
    * sprawdza czy dodać nową kaczkę
     * doda nową jeżeli jest ich aktualnie za mało na ekranie i minął odpowiedni czas
    * */
    private boolean shouldAddNewDuck(long now) {
        return ducks.size() < MAX_DUCKS_COUNT && (now - lastAddDuckTime) > TIME_BETWEEN_DUCKS;
    }

    /*
    * aktualizuje kaczkę na ekranie
    * oblicza nową pozycję i ją rysuje
    * */
    private void updateDuckPosition(Duck duck) {
        duckPositionCalculator.moveDuck(duck);
        canvas.getGraphicsContext2D().drawImage(duckImage, duck.getPositionX(), duck.getPositionY());
    }
}
