package UI.observers;

import javafx.scene.control.TextArea;

/**
 * Class for observing chat actions
 */
public class ChatObserver {
    private TextArea chat;

    /**
     * ChatObserver constructor
     *
     * @param chat
     */
    public ChatObserver(TextArea chat) {
        this.chat = chat;
    }

    /**
     * Adds text to chat.
     *
     * @param text
     */
    public void updateChat(String text) {
        chat.appendText(text + "\n");
    }
}
