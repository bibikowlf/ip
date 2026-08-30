package laby;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/** Represents one chat message with a blank profile-picture placeholder. */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 48.0;

    private final Label text;
    private final Region avatarPlaceholder;

    /**
     * Creates a chat message with a blank profile-picture placeholder.
     *
     * @param message Message to display.
     */
    public DialogBox(String message) {
        text = new Label(message);
        avatarPlaceholder = new Region();

        text.setWrapText(true);
        text.setMaxWidth(520.0);
        text.getStyleClass().add("chat-label");

        avatarPlaceholder.setMinSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarPlaceholder.setPrefSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarPlaceholder.setMaxSize(AVATAR_SIZE, AVATAR_SIZE);
        avatarPlaceholder.getStyleClass().add("avatar-placeholder");

        setSpacing(10.0);
        setPadding(new Insets(8.0, 12.0, 8.0, 12.0));
        getStyleClass().add("dialog");
        getChildren().addAll(text, avatarPlaceholder);
    }

    /**
     * Creates a dialog aligned as a user message.
     *
     * @param message User message to display.
     * @return User message dialog.
     */
    public static DialogBox getUserDialog(String message) {
        DialogBox dialogBox = new DialogBox(message);
        dialogBox.setAlignment(Pos.TOP_RIGHT);
        dialogBox.getStyleClass().add("user-dialog");
        return dialogBox;
    }

    /**
     * Creates a dialog aligned as a Laby message.
     *
     * @param message Laby message to display.
     * @return Laby message dialog.
     */
    public static DialogBox getLabyDialog(String message) {
        DialogBox dialogBox = new DialogBox(message);
        dialogBox.flip();
        dialogBox.getStyleClass().add("laby-dialog");
        return dialogBox;
    }

    /** Places the blank placeholder before the message for left-aligned dialogs. */
    private void flip() {
        getChildren().setAll(avatarPlaceholder, text);
        setAlignment(Pos.TOP_LEFT);
    }
}
