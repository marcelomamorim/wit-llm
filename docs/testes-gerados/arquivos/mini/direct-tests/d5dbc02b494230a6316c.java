/*
 * @test
 * @summary Test sun.net.NetworkServer.startServer(int) basic functionality and error handling
 * @run main sun.net.NetworkServerWitupTest
 */

import sun.net.NetworkServer;
import java.io.IOException;
import java.net.ServerSocket;

public class NetworkServerWitupTest {
    public static void main(String[] args) throws Exception {
        testStartServerValidPort();
        testStartServerInvalidPort();
    }

    private static void testStartServerValidPort() throws Exception {
        NetworkServer server = new NetworkServer();
        // Use port 0 to let system pick an available port
        server.startServer(0);
        // Wait briefly to allow server thread to start
        Thread.sleep(200);
        // Check that serverSocket is non-null and bound
        ServerSocket ss = getServerSocket(server);
        if (ss == null) {
            throw new AssertionError("ServerSocket should not be null after startServer");
        }
        if (!ss.isBound()) {
            throw new AssertionError("ServerSocket should be bound after startServer");
        }
        // Interrupt server thread to stop it
        Thread serverThread = getServerThread(server);
        if (serverThread != null) {
            serverThread.interrupt();
            serverThread.join(1000);
        }
    }

    private static void testStartServerInvalidPort() {
        NetworkServer server = new NetworkServer();
        try {
            // Use invalid port number -1, expect IOException
            server.startServer(-1);
            throw new AssertionError("Expected IOException for invalid port -1");
        } catch (IOException expected) {
            // Expected
        } catch (IllegalArgumentException expected) {
            // Some implementations may throw IllegalArgumentException for invalid port
        }
    }

    // Reflection helpers to access private fields
    private static ServerSocket getServerSocket(NetworkServer server) {
        try {
            java.lang.reflect.Field f = NetworkServer.class.getDeclaredField("serverSocket");
            f.setAccessible(true);
            return (ServerSocket) f.get(server);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Thread getServerThread(NetworkServer server) {
        try {
            java.lang.reflect.Field f = NetworkServer.class.getDeclaredField("serverInstance");
            f.setAccessible(true);
            return (Thread) f.get(server);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
