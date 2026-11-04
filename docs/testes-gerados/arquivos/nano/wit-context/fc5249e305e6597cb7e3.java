/* @test    @summary Tests for java.net.ServerSocket constructor with port, backlog, and InetAddress    @run main ServerSocketWitupTest */

import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorValidParameters();
        testConstructorInvalidPortBelowRange();
        testConstructorInvalidPortAboveRange();
    }

    private static void testConstructorValidParameters() throws IOException {
        InetAddress localAddress = InetAddress.getLoopbackAddress();
        ServerSocket serverSocket = new ServerSocket(0, 50, localAddress);
        try {
            if (serverSocket.getLocalPort() == 0) {
                throw new AssertionError("Expected assigned port, but got 0");
            }
        } finally {
            serverSocket.close();
        }
    }

    private static void testConstructorInvalidPortBelowRange() {
        try {
            new ServerSocket(-1, 50, InetAddress.getLoopbackAddress());
            throw new AssertionError("Expected IllegalArgumentException for port -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testConstructorInvalidPortAboveRange() {
        try {
            new ServerSocket(65536, 50, InetAddress.getLoopbackAddress());
            throw new AssertionError("Expected IllegalArgumentException for port 65536");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
