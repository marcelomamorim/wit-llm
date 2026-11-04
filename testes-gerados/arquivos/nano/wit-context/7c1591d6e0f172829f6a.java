/* @test    @summary Tests for java.net.ServerSocket.setReceiveBufferSize(int)    @run main ServerSocketWitupTest */

import java.net.SocketException;
import java.net.ServerSocket;

public class ServerSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizeNegative();
        testSetReceiveBufferSizeZero();
        testSetReceiveBufferSizePositive();
        testSetReceiveBufferSizeWhenClosed();
    }

    private static void testSetReceiveBufferSizeNegative() {
        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket.setReceiveBufferSize(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for negative size: " + e);
        }
    }

    private static void testSetReceiveBufferSizeZero() {
        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket.setReceiveBufferSize(0);
            System.err.println("Failed: Expected IllegalArgumentException for zero size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for zero size: " + e);
        }
    }

    private static void testSetReceiveBufferSizePositive() {
        ServerSocket serverSocket = new ServerSocket();
        try {
            serverSocket.setReceiveBufferSize(1024);
            // No exception expected
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for positive size: " + e);
        }
    }

    private static void testSetReceiveBufferSizeWhenClosed() {
        ServerSocket serverSocket = new ServerSocket();
        try {
            // Close the socket to simulate closed state
            serverSocket.close();
            serverSocket.setReceiveBufferSize(1024);
            System.err.println("Failed: Expected SocketException when socket is closed");
        } catch (java.net.SocketException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception when socket is closed: " + e);
        }
    }
}
