/**
 * @author Stavros Nikolaidis
 * AEM: 3975
 */
public class Message implements java.io.Serializable {
    boolean isRead;
    String sender;
    String receiver;
    String body;
    int id;

    /**
     * Message Constructor
     * @param sender must be string and the name of the message sender.
     * @param receiver must be string and the name of the message receiver.
     * @param body must be the content of the message.
     * @param id must be a distinct message id.
     */
    Message(String sender, String receiver, String body, int id){
        isRead = false;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.id = id;
    }

    /**
     * Setter to change the isRead variable to true.
     */
    public void setRead() {
        isRead = true;
    }
}
