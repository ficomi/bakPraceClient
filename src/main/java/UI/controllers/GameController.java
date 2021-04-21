package UI.controllers;

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import UI.UI;
import UI.observers.GameObserver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class controlling game window
 */
public class GameController {
    private Game game;
    private Network network;
    private UI ui;
    private Stage stage;
    private Button[] buttons;
    private int position;
    private int[] buttonsPosition;
    private GameObserver gameObserver;

    /**
     * First player name
     */
    @FXML
    public Text firstName;

    /**
     * Second player name
     */
    @FXML
    public Text secondName;

    /**
     * First player elo
     */
    @FXML
    public Text firstElo;

    /**
     * Second player elo
     */
    @FXML
    public Text secondElo;

    /**
     * First player score
     */
    @FXML
    public Text firstScore;

    /**
     * Second player score
     */
    @FXML
    public Text secondScore;

    /**
     * AnchorPane for gameGrid
     */
    @FXML
    public AnchorPane gameAnchor;

    /**
     * GridPane for game cards
     */
    @FXML
    public GridPane gameGrid;

    /**
     * Sets data for game controller
     *
     * @param game
     * @param network
     * @param ui
     */
    public void setGameController(Game game, Network network, UI ui) {
        this.game = game;
        this.network = network;
        this.ui = ui;
        this.stage = (Stage) firstName.getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            network.setConnect(false);
            System.exit(0);
        });
        buttonsPosition = new int[2];
    }

    /**
     * Sets players for game controller
     *
     * @param firstPlayer
     * @param secondPlayer
     * @param firstRating
     * @param secondRating
     */
    public void setPlayers(String firstPlayer, String secondPlayer, String firstRating, String secondRating) {
        buttons = new Button[16];
        firstName.setText(firstPlayer);
        secondName.setText(secondPlayer);
        firstElo.setText(firstRating);
        secondElo.setText(secondRating);
        firstScore.setText(String.valueOf(game.getLocalPlayer().getScore()));
        secondScore.setText(String.valueOf(game.getSecondPlayer().getScore()));

        gameObserver = new GameObserver(game, network, ui, stage, firstScore, secondScore, buttons);
        ui.setGameObserver(gameObserver);
    }

    /**
     * Sets card field
     */
    public synchronized void setGameCards() {
        for (int i = 0; i < 16; i++) {
            buttons[i] = new Button("..");
            buttons[i].setId(String.valueOf(i));
            buttons[i].setDisable(!ui.isPlayerTurn());
            buttons[i].setPrefSize(200, 200);
            buttons[i].setFont(Font.font("Calibri", 50));
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button button = (Button) event.getSource();
                    position = Integer.parseInt(button.getId());
                    cardClicked();
                }
            });
        }
        Platform.runLater(
                () -> {
                    int fico = 0;
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            gameGrid.add(buttons[fico++], i, j);
                        }
                    }
                });
    }

    /**
     * Updates javafx on card click
     */
    public void cardClicked() {
        buttons[position].setDisable(true);
        System.out.println(game.getField().getMove());
        if (game.getField().getMove() == 2) {
            game.getField().moveReset();
            if (game.getField().compareTwoCards(game.getField().getCard(buttonsPosition[0]), game.getField().getCard(buttonsPosition[1]))) {
                game.getField().getCard(buttonsPosition[0]).setClickable(false);
                game.getField().getCard(buttonsPosition[1]).setClickable(false);
            } else {
                ui.setPlayerTurn(false);
                gameObserver.setAllCardsToBackside();
            }
        } else {
            buttons[position].setText(String.valueOf(game.getField().getCard(position).getValue()));
            buttonsPosition[game.getField().getMove()] = position;
            game.getField().getCard(position).setFlip(true);
            game.getField().nextMove();
        }
        gameObserver.setAvailableCards();
        CommandMapSend.setRequiredCommand(StringCommandsSend.FIELD);
        network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
        if (game.getLocalPlayer().getScore() > 0) {
            CommandMapSend.setRequiredCommand(StringCommandsSend.EGAME);
            network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
        }
    }
}
