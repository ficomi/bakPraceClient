/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexesoclient;

import Logic.Game;
import Network.Network;
import UI.UI;
import UI.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Main třída.
 *
 * @author Miloslav Fico
 */
public class PexesoClient extends Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PexesoClient.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        VBox root = loader.load();
        Game game = new Game(16);
        MainController controller = loader.getController();
        try {
            Network network = createNetwork(game);
            UI ui = new UI(game, network);
            network.setUI(ui);
            controller.setMainController(game, network, ui, primaryStage);
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Pexeso");
            primaryStage.show();
        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Chyba připojení k serveru");
            alert.setContentText("Aplikaci se nepodařilo připojit se k serveru. Zkuste to prosím znovu!");
            alert.showAndWait();
            System.exit(1);
        }
    }

    private Network createNetwork(Game game) throws Exception {
        return new Network(game);
    }
}
