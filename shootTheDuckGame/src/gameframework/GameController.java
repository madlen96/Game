package gameframework;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private SplitPane splitPane;

    @FXML
    private Text shootsCount;

    @FXML
    private Text killedCount;

    @FXML
    private Text runawayCount;

    @FXML
    private Text scoreCount;

    @FXML
    private Text gameOverText;

    @FXML
    private Text gameOverInstruction;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    private Canvas canvas;

    @FXML
    private Pane canvasPane;

    private Cursor previousCursor;

    private ShootChecker shootChecker;

    private List<Duck> ducks;

    private GameResultHolder gameResultHolder;

    private AnimationDrawer animationDrawer;

    private GameConditionWatcher gameConditionWatcher;

    public GameController() {
        this.ducks = new ArrayList<>();
        this.gameResultHolder = new GameResultHolder();
        this.shootChecker = new ShootChecker(ducks, gameResultHolder);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExitHandler.addActionOnExit(this::stopRunningThreads);

        splitPane.setBackground(new Background(new BackgroundImage( ImageProvider.getBackgroundImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true))));

        initializeTextLabels();
        initializeCanvas();
        initializeButtons();
        hideGameOverLabels();
        addRestartGameKeys();
        initializeAdditionalThreads();
    }

    private void stopRunningThreads() {
        if (gameConditionWatcher != null && gameConditionWatcher.isAlive()) {
            gameConditionWatcher.shutdown();
        }
        if (animationDrawer != null) {
            animationDrawer.stop();
        }
    }

    private void initializeTextLabels() {
        shootsCount.textProperty().bind(Bindings.concat(gameResultHolder.shootsCountProperty()));
        killedCount.textProperty().bind(Bindings.concat(gameResultHolder.killedCountProperty()));
        runawayCount.textProperty().bind(Bindings.concat(gameResultHolder.runawayCountProperty()));
        scoreCount.textProperty().bind(Bindings.concat(gameResultHolder.scoreCountProperty()));
    }

    private void initializeCanvas() {
        canvas.setCursor(new ImageCursor(ImageProvider.getCursorImage()));
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> shootChecker.checkShoot(event.getX(), event.getY()));
    }

    private void initializeButtons() {
        restartButton.setOnAction(event -> restartGame()); 
        exitButton.setOnAction(event -> ExitHandler.exit());
        
        initializeMouseCursorOnButton(restartButton);
        initializeMouseCursorOnButton(exitButton);
    }

    private void hideGameOverLabels() {
        gameOverText.setVisible(false);
        gameOverInstruction.setVisible(false);
    }

    private void addRestartGameKeys() {
        splitPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) && !gameConditionWatcher.isAlive()) {
                restartGame();
            }
        });
    }
   
    private GameConditionWatcher createGameConditionWatcher() {
        return new GameConditionWatcher(animationDrawer, gameResultHolder, gameOverText, gameOverInstruction);
    }

    private void restartGame() {
        hideGameOverLabels();
        ducks.clear();
        animationDrawer.start();
        if (gameConditionWatcher.isAlive()) {
            gameConditionWatcher.shutdown();
        }
        gameConditionWatcher = createGameConditionWatcher();
        gameConditionWatcher.start();
        gameResultHolder.resetValues();
    }

    private void initializeMouseCursorOnButton(Button button) {
        button.setOnMouseEntered(event -> {
            Scene currentScene = button.getScene();
            previousCursor = currentScene.getCursor();
            currentScene.setCursor(Cursor.DEFAULT);
        });
        button.setOnMouseExited(event -> {
            Scene currentScene = button.getScene();
            currentScene.setCursor(previousCursor);
        });
    }

    private void initializeAdditionalThreads() {
        animationDrawer = new AnimationDrawer(canvas, gameResultHolder, ducks);
        animationDrawer.start();
        gameConditionWatcher = createGameConditionWatcher();
        gameConditionWatcher.start();
    }
}
