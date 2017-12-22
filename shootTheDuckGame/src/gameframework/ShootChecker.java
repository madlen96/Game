package gameframework;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

//klasa sprawdza czy strzal byl celny
public class ShootChecker {

    private final List<Duck> ducks;
    private final GameResultHolder gameResultHolder;
    private final Image cursorImage = ImageProvider.getCursorImage();
    private final BufferedImage duckImage = ImageProvider.getBufferedDuckImage();

    public ShootChecker(List<Duck> ducks, GameResultHolder gameResultHolder) {
        this.ducks = ducks;
        this.gameResultHolder = gameResultHolder;
    }

    // dostaje x i y klikniecia
    public void checkShoot(double shootX, double shootY) {
        //zwraca lewy gorny rog obrazka z kursorem wiec dodajemy odpowiednie czesci obrazka aby ten strzal wysrodkowac
        double x = shootX + (cursorImage.getWidth() / 4);
        double y = shootY + (cursorImage.getHeight() / 4);
        Iterator<Duck> duckIterator = ducks.iterator();
        while (duckIterator.hasNext()) {
            Duck duck = duckIterator.next();
            //sprawdzamy strzal dla kazdej kaczki
            if (isGoodShoot(x, y, duck)) {
                //jak byl dobry do update wyniku
                gameResultHolder.duckKilled(duck);
                //kaczce mowimy papa
                duckIterator.remove();
                //break po to zeby w sytuacji gdy w 1 miejscu byly 2 kaczki zabil tylko 1
                break;
            } else {
                //strzal chybiony, update wyniku
                gameResultHolder.shootMissed();
            }
        }
    }

    private boolean isGoodShoot(double x, double y, Duck duck) {
        //sprawdzamy czy trafil w obrazek kaczki
        if (isGoodShootWithX(x, duck.getPositionX()) && isGoodShootWithY(y, duck.getPositionY())) {
            //obrazek kaczki zawiera puste piksele, kaczka nie wypelnia calego prostokata
            int pixel = duckImage.getRGB((int) (x - duck.getPositionX()), (int) (y - duck.getPositionY()));
            // sprawdzamy czy pixel ktory trafilismy nie jest pusty - transparentny
            return (pixel >> 24 != 0x00);
  
        }
        return false;
    }

    //czy trafil w obrazek wzgledem x?
    private boolean isGoodShootWithX(double shootX, double duckXPosition) {
        return isBetween(shootX, duckXPosition, duckXPosition + duckImage.getWidth());
    }

    //czy trafil w obrazek wzgledem y?
    private boolean isGoodShootWithY(double shootY, double duckYPosition) {
        return isBetween(shootY, duckYPosition, duckYPosition + duckImage.getHeight());
    }
    
    // metoda pomocnicza
    // uzywam double compare, jest bezpieczniejsze bo to liczby zmiennoprzecinkowe
    private boolean isBetween(double value, double start, double stop) {
        return Double.compare(value, start) == 1 && Double.compare(stop, value) == 1;
    }
}
