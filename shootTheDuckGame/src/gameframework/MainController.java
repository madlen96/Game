package gameframework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String GAME_INSTRUCTION = "Use left mouse button to shot the duck.\n" +
            "Click START button to start the game.\n" +
            "Press ESC + Shift any time to exit the game.";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView descriptionImageView;

    @FXML
    private Text descriptionText;

    @FXML
    private Button exitButton;

    @FXML
    private Button startGameButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borderPane.setBackground(new Background(new BackgroundImage(ImageProvider.getStartBackgroundImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(50, 100, true, true, true, true))));
        descriptionImageView.setImage(ImageProvider.getDescriptionBackground());
        descriptionText.setText(GAME_INSTRUCTION);
        descriptionText.setFill(Color.BLACK);
        startGameButton.setOnAction(event -> startGame());
        exitButton.setOnAction(event -> ExitHandler.exit());
    }

    //przy starcie gry zmieniamy scene na ta z gra
    private void startGame() {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("gameScene.fxml"));
            Scene scene = new Scene(root,1280, 960);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
