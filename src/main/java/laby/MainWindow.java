package laby;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/** Controls the main FXML view and connects it to Laby's command logic. */
public class MainWindow {
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendButton;

    private Laby laby;

    /**
     * Injects the application logic and displays the opening message.
     *
     * @param laby Application logic used to execute commands.
     */
    public void setLaby(Laby laby) {
        this.laby = laby;
        appendMessage("Hello Chief. Laby is your personal assistant.");
    }

    /**
     * Executes the command in the input field and displays the response.
     */
    @FXML
    private void handleUserInput() {
        String input = inputField.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        appendMessage("> " + input);
        appendMessage(laby.executeCommand(input));
        inputField.clear();

        if (input.equalsIgnoreCase("bye")) {
            Platform.exit();
        }
    }

    /**
     * Adds one message to the conversation area.
     *
     * @param message Message to display.
     */
    private void appendMessage(String message) {
        chatArea.appendText(message + System.lineSeparator());
    }
}
