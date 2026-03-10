import java.util.ArrayList;
import java.util.List;

public class Account {

    private String username;
    private int authToken;
    private List<Message> messagesBox;

    public Account(String username, int authToken) {
        this.username = username;
        this.authToken = authToken;
        messagesBox = new ArrayList<>();
    }

    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }

    public void setMessagesBox(List<Message> messagesBox) {
        this.messagesBox = messagesBox;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public List<Message> getMessagesBox() {
        return messagesBox;
    }

    //method to add a message to messages array
    public void addMessage(Message message) {
        messagesBox.add(message);
    }

    //method to get a message from messages array using specific ID
    public Message getMessage(int messageID) {
        try {
            return messagesBox.get(messageID);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    //method to remove a message from messages array using specific ID
    public boolean removeMessage(int messageID) {
        try {
            if (messagesBox.remove(messageID) != null)
                return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}