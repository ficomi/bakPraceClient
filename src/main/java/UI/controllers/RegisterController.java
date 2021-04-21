package UI.controllers;

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import UI.UI;
import UI.observers.RegisterObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class controlling register window.
 */
public class RegisterController {

    /**
     * Instance of game
     */
    private Game game;

    /**
     * Instance of network
     */
    private Network network;

    /**
     * Instance of ui
     */
    private UI ui;

    /**
     * Instance of stage
     */
    private Stage stage;

    @FXML
    public TextField registerNameField;

    @FXML
    public PasswordField registerPasswordField;

    @FXML
    public PasswordField registerPasswordFieldConfirm;

    @FXML
    public Button registerConfirmBtn;

    /**
     * Sets register controller data
     *
     * @param game
     * @param network
     * @param ui
     * @param stage
     */
    public void setRegisterController(Game game, Network network, UI ui, Stage stage) {
        this.game = game;
        this.network = network;
        this.stage = stage;
        this.ui = ui;
        stage.setOnCloseRequest(e -> {
            network.setConnect(false);
            System.exit(0);
        });
        RegisterObserver registerObserver = new RegisterObserver(registerNameField, registerPasswordField, registerPasswordFieldConfirm, stage);
        ui.setRegisterObserver(registerObserver);
    }

    /**
     * Sends register request to server
     */
    public void register() {
        String name = registerNameField.getText();
        String password = registerPasswordField.getText();
        String passwordConfirm = registerPasswordFieldConfirm.getText();
        if (!password.equals(passwordConfirm)) {
            registerPasswordField.clear();
            registerPasswordFieldConfirm.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("VAROVÁNÍ");
            alert.setHeaderText("Špatné ověřovací hesla. Hesla se musí shodovat!");

            alert.showAndWait();
            registerPasswordField.requestFocus();
        } else if (name.isEmpty()) {
            registerNameField.clear();
            registerPasswordField.clear();
            registerPasswordFieldConfirm.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("VAROVÁNÍ");
            alert.setHeaderText("Jméno musí být vyplněno!");

            alert.showAndWait();
            registerPasswordField.requestFocus();
        } else if (password.isEmpty()) {
            registerPasswordField.clear();
            registerPasswordFieldConfirm.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("VAROVÁNÍ");
            alert.setHeaderText("Heslo musí být vyplněno!");

            alert.showAndWait();
            registerPasswordField.requestFocus();
        } else {
            ui.setTempName(name);
            ui.setTempPasswd(password);
            CommandMapSend.setRequiredCommand(StringCommandsSend.ADDUSER);
            network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
        }
    }
}
