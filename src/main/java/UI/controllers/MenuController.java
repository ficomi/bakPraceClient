package UI.controllers;

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import UI.UI;
import UI.observers.ChatObserver;
import UI.observers.StartGameObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class controlling menu window
 */
public class MenuController {

    private Game game;

    private Network network;

    private UI ui;

    private Stage stage;

    @FXML
    public Text playerName;

    @FXML
    public Text playerElo;

    @FXML
    public TextField message;

    @FXML
    public TextArea chat;

    @FXML
    public Button findBtn;

    /**
     * Sets menu controller data.
     *
     * @param game
     * @param network
     * @param ui
     */
    public void setMenuController(Game game, Network network, UI ui) {
        this.game = game;
        this.network = network;
        this.ui = ui;
        this.stage = (Stage) playerName.getScene().getWindow();
        chat.setEditable(false);

        ChatObserver chatObserver = new ChatObserver(chat);
        ui.setChatObserver(chatObserver);
        StartGameObserver startGameObserver = new StartGameObserver(game, network, ui, stage);
        ui.setStartGameObserver(startGameObserver);
        setNameAndElo();
    }

    /**
     * Sets player name and elo.
     */
    public void setNameAndElo() {
        playerName.setText(game.getLocalPlayer().getName());
        playerElo.setText(String.valueOf(game.getLocalPlayer().getElo()));
    }

    /**
     * Send players chat message to server.
     */
    public void send() {
        String text = message.getText();
        if (text.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("VAROVÁNÍ");
            alert.setHeaderText("Zpráva nesmí být prázdná!");

            alert.showAndWait();
        } else {
            ui.setMessage(text);
            CommandMapSend.setRequiredCommand(StringCommandsSend.SENDMSG);
            network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
            message.clear();
            message.requestFocus();
        }
    }

    /**
     * Starts finding a game.
     */
    public void findGame() {
        if (game.isIsSearching()) {
            findBtn.setText("Hledat hru");
        } else {
            findBtn.setText("Hledá se hra");
        }
        CommandMapSend.setRequiredCommand(StringCommandsSend.SSEARCH);
        network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
    }
}
