import java.util.ArrayList;
import java.util.List;

/**
 * @author Stavros Nikolaidis
 * AEM: 3975
 */
public class Account implements java.io.Serializable {
    String username;
    int authToken;
    List<Message> messageBox;

    /**
     * Account Constructor
     * @param username must be string and the name of the user.
     * @param authToken must be integer and the identical token of the user.
     */
    Account(String username, int authToken){
        this.username = username;
        this.authToken = authToken;
        messageBox = new ArrayList<>(); // initializing the message box list.
    }

    /**
     * Getter for the user's mailbox.
     * @return a list of the user's mailbox messages.
     */
    public List<Message> getMessageBox() {
        return messageBox;
    }
}
