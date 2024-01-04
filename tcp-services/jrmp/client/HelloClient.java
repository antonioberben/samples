import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class HelloClient {
    public static void main(String args[]) {
        try {

            // Get the server hostname and port from the environment variables
            String hostname = System.getenv("RMI_HOST") != null ? System.getenv("RMI_HOST") : "localhost";
            int port = System.getenv("RMI_PORT") != null ? Integer.parseInt(System.getenv("RMI_PORT")) : 1100;
            boolean tlsEnabled = "enabled".equals(System.getenv("TLS"));

            System.out.println("Hello Client is ready. TLS is " + (tlsEnabled ? "enabled" : "disabled")
                    + ". Connected to RMI Registry at " + hostname + ":" + port);
            // Get the registry
            Registry registry;
            Hello stub;
            if (tlsEnabled) {
                RMIClientSocketFactory csf = new SslRMIClientSocketFactory();
                registry = LocateRegistry.getRegistry(hostname, port, csf);
                stub = (Hello) registry.lookup("Hello");
            } else {
                registry = LocateRegistry.getRegistry(hostname, port);
                stub = (Hello) registry.lookup("Hello");
            }

            // Look up the remote object
            while (true) {
                // Call the remote method
                String message = stub.sayHello("PING");
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