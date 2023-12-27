import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloClient {
    public static void main(String args[]) {
        try {

            // Get the server hostname and port from the environment variables
            String hostname = System.getenv("RMI_HOST") != null ? System.getenv("RMI_HOST") : "localhost";
            int port = System.getenv("RMI_PORT") != null ? Integer.parseInt(System.getenv("RMI_PORT")) : 1100;
    
            System.out.println("Hello Client is ready. Connected to " + hostname + ":" + port);
            // Get the registry
            Registry registry = LocateRegistry.getRegistry(hostname, port);

            // Look up the remote object
            Hello obj = (Hello) registry.lookup("Hello");
            while (true) {
                // Call the remote method
                String message = obj.sayHello("PING");
                System.out.println("Message from the server: " + message);
        
                // Sleep for 2 seconds
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}