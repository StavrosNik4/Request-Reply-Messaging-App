import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            MessagingServer stub = new MessagingServer();
            // create the RMI registry on given port
            Registry rmiRegistry = LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            // path to access is rmi://localhost:5000/rrma
            rmiRegistry.rebind("rrma", stub);
            System.out.println("Server is ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
