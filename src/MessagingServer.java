import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Stavros Nikolaidis
 * AEM: 3975
 */
public class MessagingServer extends UnicastRemoteObject implements Inter{

    static List<Account> accountList = new ArrayList<>();
    int idCounter = 0;

    // super constructor
    protected MessagingServer() throws RemoteException {
        super();
    }

    /**
     * Method to add a new user
     * @param tmp must be the new user's object.
     */
    public static void addUser(Account tmp){
        accountList.add(tmp);
    }

    /**
     * Method to get user's username if you know their token.
     * @param token must be the user's authToken.
     * @return user's username.
     */
    public static String getNameByToken(int token){
        for (Account account: accountList) {
            if(token == account.authToken){
                return account.username;
            }
        }
        return null;
    }

    /**
     * Method to check if there is a username in the server.
     * @param name must be a username.
     * @return true/false.
     */
    public static boolean checkName(String name){
        for (Account acc : accountList) {
            if (Objects.equals(acc.username, name))
                return true;
        }
        return false;
    }

    /**
     * Method to check if there is a token in the server.
     * @param token must be an authToken.
     * @return true/false.
     */
    public static boolean checkAuth(int token){
        for (Account acc : accountList) {
            if (acc.authToken == token)
                return true;
        }
        return false;
    }

    /**
     * Creates a new user.
     * @param username must be a username.
     * @return the user's token.
     * @throws RemoteException exception handling.
     */
    @Override
    public String createAccount(String username) throws RemoteException {

        // checking username availability
        if(checkName(username))
            return "Sorry, the user already exists";

        // checking username validity
        if(!username.matches("^[a-zA-Z0-9_]+$"))
            return "Invalid Username";

        Random rn = new Random(); // generating random number until we get a token that it doesn't exists.
        int authToken = rn.nextInt(5000 - 1 + 1) + 1;
        while(checkAuth(authToken)) {
            authToken = rn.nextInt(5000 - 1 + 1) + 1;
        }

        Account tmp = new Account(username, authToken); // creating the account object
        addUser(tmp); // adding the user object in the list

        return String.valueOf(authToken);
    }

    /**
     * Show all usernames of the server.
     * @param authToken must be a user's authToken for authentication.
     * @return a string to show to the user. It can be a list of usernames or an error message.
     * @throws RemoteException exception handling.
     */
    @Override
    public String showAccounts(int authToken) throws RemoteException {
        if(checkAuth(authToken)) // authentication process
        {   // creating the list
            StringBuilder tmp = new StringBuilder();
            int i = 1;
            for (Account a: accountList) {
                tmp.append(i).append(". ").append(a.username).append("\n");
                i++;
            }
            return tmp.toString();
        }
        else
            return "Invalid Auth Token";  // error message
    }

    /**
     * Method to send message to a user.
     * @param authToken must be a user's authToken for authentication.
     * @param recipient must be a string of the username of the recipient.
     * @param messageBody must be a string of the message content.
     * @return a string to show to the user. It can be a success or an error message.
     * @throws RemoteException exception handling.
     */
    @Override
    public String sendMessage(int authToken, String recipient, String messageBody) throws RemoteException {
        if(checkAuth(authToken)){
            for (Account account: accountList) {
                if(Objects.equals(account.username, recipient))
                {
                    String sender = getNameByToken(authToken);
                    Message tmp = new Message(sender, recipient, messageBody, ++idCounter);
                    int indx = accountList.indexOf(account);
                    accountList.get(indx).messageBox.add(tmp);
                    return "OK";
                }
            }
            return "User does not exist"; // error message
        }
        else
            return "Invalid Auth Token"; // error message
    }

    /**
     * Shows the mailbox of a user.
     * @param authToken must be a user's authToken for authentication.
     * @return a string to show to the user. It can be a list of the user's mailbox or an error message.
     * @throws RemoteException exception handling.
     */
    @Override
    public String showInbox(int authToken) throws RemoteException {

        if(checkAuth(authToken)){
            int indx = 0;
            for (Account account: accountList) {
                if(account.authToken == authToken){
                    indx = accountList.indexOf(account);
                    break;
                }
            }
            StringBuilder tmp = new StringBuilder();
            for (Message message : accountList.get(indx).getMessageBox()) {
                tmp.append(message.id).append(". from: ").append(message.sender);
                if(message.isRead) tmp.append("\n");
                else tmp.append("*\n");
            }
            return tmp.toString();
        }
        else
            return "Invalid Auth Token"; // error message
    }

    /**
     * Reads a user's message.
     * @param authToken must be a user's authToken for authentication.
     * @param messageId must be a message id.
     * @return a string with the sender and the content of the message.
     * @throws RemoteException exception handling.
     */
    @Override
    public String readMessage(int authToken, int messageId) throws RemoteException {
        if (checkAuth(authToken)){
            int indx = 0;
            for (Account account: accountList) {
                if(account.authToken == authToken){
                    indx = accountList.indexOf(account);
                    break;
                }
            }
            StringBuilder tmp = new StringBuilder();
            for (Message message : accountList.get(indx).getMessageBox()) {
                if(message.id == messageId){
                    tmp.append("(").append(message.sender).append(")").append(message.body);
                    message.setRead();
                    return tmp.toString();
                }
            }
            return "Message ID does not exist"; // error message

        }
        else
            return "Invalid Auth Token"; // error message
    }

    /**
     * Deletes a message.
     * @param authToken must be a user's authToken for authentication.
     * @param messageId must be a message id.
     * @return a string to show to the user. It can be a success or an error message.
     * @throws RemoteException exception handling.
     */
    @Override
    public String deleteMessage(int authToken, int messageId) throws RemoteException {
        if (checkAuth(authToken)){
            int indx;
            for (Account account: accountList) {
                if(account.authToken == authToken){
                    indx = accountList.indexOf(account);
                    accountList.get(indx).getMessageBox().removeIf(message -> message.id == messageId);
                    return "OK"; // success message
                }
            }
            return "Message does not exist"; // error message
        }
        else
            return "Invalid Auth Token"; // error message
    }
}
