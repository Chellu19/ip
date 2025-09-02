package bazinga.ui;

import bazinga.BazingaBot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private BazingaBot bazinga;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image bazingaImage = new Image(this.getClass().getResourceAsStream("/images/bazinga.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Bazinga instance
     */
    public void setBazinga(BazingaBot b) {
        bazinga = b;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        String bazingaText = bazinga.getResponse(userText);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBazingaDialog(bazingaText, bazingaImage));
        userInput.clear();
        if (userText.equalsIgnoreCase("bye")) {
            javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(
                    javafx.util.Duration.millis(1000));
            delay.setOnFinished(event -> System.exit(0));
            delay.play();
        }
    }
}

