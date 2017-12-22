package gameframework;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

// klasa do obslugi zamykania aplikacji
public class ExitHandler {

    private final static List<Runnable> ACTIONS_ON_EXIT = new ArrayList<>();

    // tutaj mozemy dodac akcje ktore maja byc wywolane przed zamknieciem aplikacji
    public static void addActionOnExit(Runnable runnable) {
        ACTIONS_ON_EXIT.add(runnable);
    }

    //zamykamy aplikacje
    public static void exit() {
        //wywolujemy wszystkie akcje
        ACTIONS_ON_EXIT.forEach(Runnable::run);
        //zamykamy jave fx
        Platform.exit();
        //zamykamy program
        System.exit(0);
    }
}
