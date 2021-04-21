package UI.controllers;

import Logic.Game;
import Network.Network;
import Network.NetworkThreadName;
import Network.Send.CommandMapSend;
import Network.Send.StringCommandsSend;
import UI.UI;
import UI.observers.LoginObserver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class controlling login window
 */
public class MainController {

    private Game game;

    private Network network;

    private UI ui;

    private Stage primaryStage;

    @FXML
    public Button loginBtn;

    @FXML
    public Button registerBtn;


    @FXML
    public TextField nameField;

    @FXML
    public PasswordField passwordField;

    /**
     * Sets data for main controller.
     *
     * @param game
     * @param network
     * @param ui
     * @param stage
     */
    public void setMainController(Game game, Network network, UI ui, Stage stage) {
        this.game = game;
        this.network = network;
        this.ui = ui;
        this.primaryStage = stage;
        stage.setOnCloseRequest(e -> {
            network.setConnect(false);
            System.exit(0);
        });
        LoginObserver loginObserver = new LoginObserver(stage, nameField, passwordField, game, network, ui);
        ui.setLoginObserver(loginObserver);
    }

    /**
     * Sends request for login to server
     */
    public void login() {
        String name = nameField.getText();
        String password = passwordField.getText();
        ui.setTempName(name);
        ui.setTempPasswd(password);
        CommandMapSend.setRequiredCommand(StringCommandsSend.LOG);
        network.getNetworkThread(NetworkThreadName.SENDER).interrupt();
    }

    /**
     * Opens register window for registration
     *
     * @throws Exception
     */
    public void openRegisterWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/registrace.fxml"));
        VBox registerWindow = loader.load();
        RegisterController registerController = loader.getController();
        Scene scene = new Scene(registerWindow);

        Stage registerStage = new Stage();
        registerStage.setTitle("Registrace");
        registerStage.setScene(scene);
        registerStage.show();
        registerController.setRegisterController(game, network, ui, registerStage);

    }


}
