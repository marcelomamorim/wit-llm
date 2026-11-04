/* @test    @summary Test ServerSocket constructor with port, backlog, and bind address    @run main ServerSocketWitupTest */

import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidParams();
        testConstructorWithInvalidPort();
        testConstructorWithInvalidBacklog();
    }

    static void testConstructorWithValidParams() throws IOException {
        InetAddress localAddress = InetAddress.getLoopbackAddress();
        ServerSocket serverSocket = new ServerSocket(0, 50, localAddress);
        assert serverSocket != null : "ServerSocket should be created";
        assert serverSocket.getLocalPort() > 0 : "Port should be assigned";
        serverSocket.close();
    }

    static void testConstructorWithInvalidPort() {
        InetAddress localAddress;
        try {
            localAddress = InetAddress.getLoopbackAddress();
        } catch (Exception e) {
            localAddress = null;
        }
        try {
            new ServerSocket(-1, 50, localAddress);
            assert false : "Expected IllegalArgumentException for negative port";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Port value out of range") : "Exception message should mention port out of range";
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
        try {
            new ServerSocket(70000, 50, localAddress);
            assert false : "Expected IllegalArgumentException for port > 65535";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Port value out of range") : "Exception message should mention port out of range";
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }

    static void testConstructorWithInvalidBacklog() throws IOException {
        InetAddress localAddress = InetAddress.getLoopbackAddress();
        ServerSocket ss = new ServerSocket(0, -10, localAddress);
        assert ss != null : "ServerSocket should be created with default backlog";
        ss.close();
    }
}
