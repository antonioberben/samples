import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServer {
    public static void main(String args[]) {
        try {

            // Get the server port from the environment variable
            int port = System.getenv("RMI_SERVER_PORT") != null
                    ? Integer.parseInt(System.getenv("RMI_SERVER_PORT"))
                    : 1100;
            Hello stub;

            // Create and export the remote object
            HelloImpl obj = new HelloImpl();
            try {
                stub = (Hello) UnicastRemoteObject.exportObject(obj, port);
            } catch (ExportException e) {
                UnicastRemoteObject.unexportObject(obj, true);
                stub = (Hello) UnicastRemoteObject.exportObject(obj, port);
            }

            // Get the registry and bind the name and object.
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.out.println("Hello Server is ready");
        } catch (Exception e) {
            System.out.println("Hello Server failed: " + e);
        }
    }
}