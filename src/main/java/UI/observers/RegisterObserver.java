package UI.observers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class for observing register actions.
 */
public class RegisterObserver {
    private Stage stage;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private TextField textField;

    /**
     * RegisterObserver constructor
     *
     * @param textField
     * @param passwordField
     * @param confirmPasswordField
     * @param stage
     */
    public RegisterObserver(TextField textField, PasswordField passwordField, PasswordField confirmPasswordField, Stage stage) {
        this.stage = stage;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
        this.textField = textField;
    }

    /**
     * Closer register window and shows alert
     * about successful registration.
     */
    public void successfulRegistration() {
        Platform.runLater(
                () -> {
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFO");
                    alert.setHeaderText("Úspěšná registrace!");
                    alert.setContentText("Byl jste úspěšně zaregistrován. Nyní se můžete přihlásit.");

                    alert.showAndWait();
                }
        );
    }

    /**
     * Shows alert about failed registration.
     */
    public void failedRegistration() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("NEÚSPĚCH");
                    alert.setHeaderText("Registrace se nepovedla!");
                    alert.setContentText("Uživatel už je zaregistrovaný nebo nastala chyba, zkuste to prosím znova.");
                    alert.showAndWait();
                    textField.clear();
                    passwordField.clear();
                    confirmPasswordField.clear();
                    textField.requestFocus();
                }
        );
    }
}
