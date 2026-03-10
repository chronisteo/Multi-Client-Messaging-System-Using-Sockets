public class Message {

    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;

    public Message(String sender, String receiver, String body) {
        this.isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    public String getReceiver() {
        return receiver;
    }


}