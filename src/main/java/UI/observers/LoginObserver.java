package UI.observers;

import Logic.Game;
import Network.Network;
import UI.UI;
import UI.controllers.MenuController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Class for observing login actions.
 */
public class LoginObserver {
    private Stage stage;
    private TextField textField;
    private PasswordField passwordField;
    private Game game;
    private Network network;
    private UI ui;

    /**
     * LoginObserver constructor
     *
     * @param stage
     * @param textField
     * @param passwordField
     * @param game
     * @param network
     * @param ui
     */
    public LoginObserver(Stage stage, TextField textField, PasswordField passwordField, Game game, Network network, UI ui) {
        this.stage = stage;
        this.textField = textField;
        this.passwordField = passwordField;
        this.game = game;
        this.network = network;
        this.ui = ui;
    }


    private final FutureTask<MenuController> getMenuController = new FutureTask<>(new Callable<>() {
        @Override
        public MenuController call() throws Exception {
            stage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ÚSPĚCH");
            alert.setHeaderText("Uspěšně přihlášen!");
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
     * Creates menu controller after successful login.
     */
    public void successfulLogin() {
        try {
            Platform.runLater(getMenuController);
            MenuController menuController = (MenuController) getMenuController.get();
            menuController.setMenuController(game, network, ui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows info about failed login.
     */
    public void failedLogin() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("NEÚSPĚCH");
                    alert.setHeaderText("Přihlášení se nepovedlo!");
                    alert.showAndWait();
                    textField.clear();
                    passwordField.clear();
                    textField.requestFocus();
                }
        );
    }
}
