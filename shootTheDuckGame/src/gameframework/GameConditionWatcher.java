package gameframework;

import javafx.scene.text.Text;

/*
* klasa sprawdzajaca czy powinnismy zakonczyc gre
* */
public class GameConditionWatcher extends Thread {
    private final AnimationDrawer animationDrawer;
    private final GameResultHolder gameResultHolder;
    private final Text gameOverText;
    private final Text gameOverInstruction;
    private boolean shutdown;

    public GameConditionWatcher(AnimationDrawer animationDrawer, GameResultHolder gameResultHolder, Text gameOverText, Text gameOverInstruction) {
        this.animationDrawer = animationDrawer;
        this.gameResultHolder = gameResultHolder;
        this.gameOverText = gameOverText;
        this.gameOverInstruction = gameOverInstruction;
        this.shutdown = false;
    }

    @Override
    public void run() {
        // konczymy gre na zadanie lub gdy ucieknie nam 20 kaczek
        while (!shutdown && gameResultHolder.getRunawayCount() < 20) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        // jezeli koniec gry a nie zadanie zamkniecia
        if (!shutdown) {
            // zatrzymaj rysowanie gry
            animationDrawer.stop();
            // uwidocznij napisy o koncu gry z instrukcja
            gameOverText.setVisible(true);
            gameOverInstruction.setVisible(true);
        }
    }

    //metoda pozwalajaca zakonczyc gre na zadanie
    public void shutdown() {
        this.shutdown = true;
    }
}
