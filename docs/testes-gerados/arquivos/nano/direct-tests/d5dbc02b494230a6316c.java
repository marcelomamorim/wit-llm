/*
 * @test
 * @summary Test startServer method of sun.net.NetworkServer
 * @run main NetworkServerWitupTest
 */
import java.io.*;
import java.net.*;
import sun.net.NetworkServer;

public class NetworkServerWitupTest {
    public static void main(String[] args) throws Exception {
        testStartServerCreatesServerSocket();
        testStartServerThrowsOnInvalidPort();
    }

    static void testStartServerCreatesServerSocket() throws Exception {
        NetworkServer server = new NetworkServer();
        int port = 0;
        try {
            server.startServer(0); // 0 is valid, system assigns port
            // Wait briefly to ensure thread starts
            Thread.sleep(100);
            // Verify serverSocket is initialized
            java.lang.reflect.Field serverSocketField = NetworkServer.class.getDeclaredField("serverSocket");
            serverSocketField.setAccessible(true);
            ServerSocket ss = (ServerSocket) serverSocketField.get(server);
            assert ss != null : "serverSocket should be initialized";
            assert ss.isBound() : "serverSocket should be bound";
            ss.close();
        } catch (IOException e) {
            throw new AssertionError("startServer threw IOException", e);
        }
    }

    static void testStartServerThrowsOnInvalidPort() throws Exception {
        NetworkServer server = new NetworkServer();
        int invalidPort = -1;
        boolean thrown = false;
        try {
            server.startServer(invalidPort);
        } catch (IOException e) {
            thrown = true;
        }
        assert thrown : "startServer should throw IOException on invalid port";
    }
}
