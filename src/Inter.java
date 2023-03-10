import java.rmi.*;
/**
 * @author Stavros Nikolaidis
 * AEM: 3975
 */
public interface Inter extends Remote{

    // The definitions of the app's services. For the implementations reference the MessagingServer class.

    /**
     * Creates a new user.
     * @param username must be a username.
     * @return the user's token.
     * @throws RemoteException exception handling.
     */
    String createAccount(String username) throws RemoteException;

    /**
     * Show all usernames of the server.
     * @param authToken must be a user's authToken for authentication.
     * @return a string to show to the user. It can be a list of usernames or an error message.
     * @throws RemoteException exception handling.
     */
    String showAccounts(int authToken) throws RemoteException;

    /**
     * Method to send message to a user.
     * @param authToken must be a user's authToken for authentication.
     * @param recipient must be a string of the username of the recipient.
     * @param messageBody must be a string of the message content.
     * @return a string to show to the user. It can be a success or an error message.
     * @throws RemoteException exception handling.
     */
    String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException;

    /**
     * Shows the mailbox of a user.
     * @param authToken must be a user's authToken for authentication.
     * @return a string to show to the user. It can be a list of the user's mailbox or an error message.
     * @throws RemoteException exception handling.
     */
    String showInbox(int authToken) throws RemoteException;

    /**
     * Reads a user's message.
     * @param authToken must be a user's authToken for authentication.
     * @param messageId must be a message id.
     * @return a string with the sender and the content of the message.
     * @throws RemoteException exception handling.
     */
    String readMessage(int authToken, int messageId) throws RemoteException;

    /**
     * Deletes a message.
     * @param authToken must be a user's authToken for authentication.
     * @param messageId must be a message id.
     * @return a string to show to the user. It can be a success or an error message.
     * @throws RemoteException exception handling.
     */
    String deleteMessage(int authToken, int messageId) throws RemoteException;

}