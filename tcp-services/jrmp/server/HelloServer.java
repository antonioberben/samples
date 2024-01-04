import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

public class HelloServer {
    public static void main(String args[]) {
        try {

            // Get the server port from the environment variable
            int port = System.getenv("RMI_SERVER_PORT") != null
                    ? Integer.parseInt(System.getenv("RMI_SERVER_PORT"))
                    : 1100;
            int registryPort = System.getenv("RMI_REGISTRY_PORT") != null ? Integer.parseInt(System.getenv("RMI_REGISTRY_PORT")) : 1099;
            boolean tlsEnabled = "enabled".equals(System.getenv("TLS"));
            System.out.println("TLS is " + (tlsEnabled? "enabled" : "disabled") + ". RMI Registry on port " + registryPort + ". RMI Server on port " + port);
            
            Hello stub;

            // Create and export the remote object
            HelloImpl obj = new HelloImpl();

            // Create SSL socket factory if TLS is enabled
            RMIClientSocketFactory csf = tlsEnabled? new SslRMIClientSocketFactory() : null;
            RMIServerSocketFactory ssf = tlsEnabled? new SslRMIServerSocketFactory() : null;
            try {
                stub = (Hello) UnicastRemoteObject.exportObject(obj, port, csf, ssf);
            } catch (ExportException e) {
                UnicastRemoteObject.unexportObject(obj, true);
                stub = (Hello) UnicastRemoteObject.exportObject(obj, port, csf, ssf);
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