package UI.observers;

import Logic.Game;
import Network.Network;
import UI.UI;
import UI.controllers.MenuController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Class for observing game actions
 */
public class GameObserver {
    private Game game;
    private Network network;
    private UI ui;
    private Stage stage;
    private Text firstScore;
    private Text secondScore;
    private Button[] buttons;

    /**
     * Observer for game
     *
     * @param game
     * @param network
     * @param ui
     * @param stage
     * @param firstScore
     * @param secondScore
     * @param buttons
     */
    public GameObserver(Game game, Network network, UI ui, Stage stage, Text firstScore, Text secondScore, Button[] buttons) {
        this.game = game;
        this.network = network;
        this.ui = ui;
        this.stage = stage;
        this.firstScore = firstScore;
        this.secondScore = secondScore;
        this.buttons = buttons;
    }

    /**
     * Sets players game score.
     */
    public void setScores() {
        firstScore.setText(String.valueOf(game.getLocalPlayer().getScore()));
        secondScore.setText(String.valueOf(game.getSecondPlayer().getScore()));
    }

    /**
     * Sets game available cards.
     */
    public void setAvailableCards() {
        for (int i = 0; i < game.getField().getField().size(); i++) {
            if (!game.getField().getCard(i).isFlipped()) {
                buttons[i].setDisable(!ui.isPlayerTurn());
            }

        }

        for (int i = 0; i < game.getField().getField().size(); i++) {
            if (!game.getField().getCard(i).isClickable()) {
                buttons[i].setStyle("-fx-background-color: #000000");
                buttons[i].setDisable(true);
            }
        }
    }

    /**
     * Sets button cards values.
     */
    public void setButtonsValue() {
        if (!ui.isPlayerTurn()) {
            Platform.runLater(
                    () -> {
                        for (int i = 0; i < game.getField().getField().size(); i++) {
                            if (game.getField().getCard(i).isFlipped()) {
                                buttons[i].setText(String.valueOf(game.getField().getCard(i).getValue()));
                            }
                        }
                    });
        }
    }

    /**
     * Sets all cards to their back side.
     */
    public void setAllCardsToBackside() {
        for (int i = 0; i < game.getField().getField().size(); i++) {
            game.getField().getCard(i).setFlip(false);
        }
        Platform.runLater(
                () -> {
                    for (Button button : buttons) {
                        button.setText("..");
                    }
                });
    }

    private final FutureTask<MenuController> getMenuController = new FutureTask<>(new Callable<>() {
        @Override
        public MenuController call() throws Exception {
            stage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("END");
            if (ui.getWon()) {
                alert.setHeaderText("VYHRÁL JSI!");
            } else {
                alert.setHeaderText("PROHRÁL JSI!");
            }
            alert.setContentText("Otevře se herní menu.");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/menu.fxml"));
            VBox menuWindow = loader.load();
            MenuController menuController = loader.getController();
            Scene scene = new Scene(menuWindow);
            Stage menuStage = new Stage();
            menuStage.setTitle("Menu");
            menuStage.setScene(scene);
            menuStage.show();
            return menuController;
        }
    });

    /**
     * Closes game stage and creates new menu stage
     * when game has ended.
     */
    public void closeGameStartMenu() {
        try {
            Platform.runLater(getMenuController);
            MenuController menuController = getMenuController.get();
            menuController.setMenuController(game, network, ui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
