import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // connect to the RMI registry
            Registry rmiRegistry = LocateRegistry.getRegistry(Integer.parseInt(args[1]));
            // get reference for remote object and use its add function
            Inter stub = (Inter) rmiRegistry.lookup("rrma");
            if (Integer.parseInt(args[2]) == 1)
                System.out.println(stub.createAccount(args[3]));
            if (Integer.parseInt(args[2]) == 2)
                System.out.println(stub.showAccounts(Integer.parseInt(args[3])));
            if (Integer.parseInt(args[2]) == 3)
                System.out.println(stub.sendMessage(Integer.parseInt(args[3]), args[4], args[5]));
            if (Integer.parseInt(args[2]) == 4)
                System.out.println(stub.showInbox(Integer.parseInt(args[3])));
            if (Integer.parseInt(args[2]) == 5)
                System.out.println(stub.readMessage(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
            if (Integer.parseInt(args[2]) == 6)
                System.out.println(stub.deleteMessage(Integer.parseInt(args[3]), Integer.parseInt(args[4])));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
