package gameframework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String GAME_TITLE = "Shoot the duck";

    //ladujemy ekran startowy
    @Override
    public void start(Stage primaryStage) throws Exception{
        // pobieram strukture interfejsu graficznego z pliku mainScene.fxml który znajduje się w projekcie
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        primaryStage.setTitle(GAME_TITLE);
        Scene scene = new Scene(root); 
        //dodanie uchwytu na wcisniecie shift + esc zeby wyjsc z gry
        //sam esc jest zarezerwowany przez jave fx do wychodzenia z full screen
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE && event.isShiftDown()) {
                ExitHandler.exit();
            }
        });
        //przy nacisnieciu x w oknie wywolaj handlera do obslugi zamkniecia
        primaryStage.setOnCloseRequest(event -> ExitHandler.exit());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(990); 
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
