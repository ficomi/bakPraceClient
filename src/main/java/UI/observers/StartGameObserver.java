package UI.observers;

import Logic.Game;
import Network.Network;
import UI.UI;
import UI.controllers.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Class for observing server actions when starting new game.
 */
public class StartGameObserver {

    private Game game;
    private Network network;
    private UI ui;
    private Stage stage;


    /**
     * StartGameObserver constructor
     *
     * @param game
     * @param network
     * @param ui
     * @param stage
     */
    public StartGameObserver(Game game, Network network, UI ui, Stage stage) {
        this.game = game;
        this.network = network;
        this.ui = ui;
        this.stage = stage;
    }

    /**
     * Future task to get gameController from javafx thread
     */
    private final FutureTask<GameController> getGameController = new FutureTask<>(new Callable<>() {
        @Override
        public GameController call() throws Exception {
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/game.fxml"));
            VBox gameWindow = loader.load();
            GameController gameController = loader.getController();
            Scene scene = new Scene(gameWindow);
            Stage gameStage = new Stage();
            gameStage.setTitle("Hra");
            gameStage.setScene(scene);
            gameStage.show();
            return gameController;


        }
    });

    /**
     * Javafx action on game start, creates gameController
     * and new stage for game.
     */
    public void startGame() {
        try {
            Platform.runLater(getGameController);
            GameController gameController = getGameController.get();
            gameController.setGameController(game, network, ui);
            gameController.setPlayers(game.getLocalPlayer().getName(), game.getSecondPlayer().getName(), String.valueOf(game.getLocalPlayer().getElo()), String.valueOf(game.getSecondPlayer().getElo()));
            gameController.setGameCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
