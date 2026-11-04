/* @test    @summary Test ServerSocket(int port) constructor with valid port and default parameters    @run main ServerSocketWitupTest */

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) {
        testValidPort();
        testPortZero();
        testInvalidNegativePort();
    }

    static void testValidPort() {
        try {
            int port = 12345;
            ServerSocket ss = new ServerSocket(port);
            assert ss.getLocalPort() == port : "Expected port " + port + " but got " + ss.getLocalPort();
            ss.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create ServerSocket with valid port", e);
        }
    }

    static void testPortZero() {
        try {
            ServerSocket ss = new ServerSocket(0);
            int port = ss.getLocalPort();
            assert port > 0 : "Port should be greater than 0, but was " + port;
            ss.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create ServerSocket with port 0", e);
        }
    }

    static void testInvalidNegativePort() {
        try {
            new ServerSocket(-1);
            throw new AssertionError("Expected IOException for negative port but none was thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
