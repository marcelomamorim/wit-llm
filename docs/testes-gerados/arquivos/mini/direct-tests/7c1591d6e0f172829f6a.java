/*
 * @test
 * @summary Test java.net.ServerSocket.setReceiveBufferSize(int) for valid and invalid inputs
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizePositive();
        testSetReceiveBufferSizeZero();
        testSetReceiveBufferSizeNegative();
        testSetReceiveBufferSizeOnClosedSocket();
    }

    private static void testSetReceiveBufferSizePositive() throws Exception {
        ServerSocket ss = new ServerSocket();
        int size = 1024;
        ss.setReceiveBufferSize(size);
        int actual = ss.getReceiveBufferSize();
        if (actual < size) {
            throw new RuntimeException("Expected receive buffer size >= " + size + ", but got " + actual);
        }
        ss.close();
    }

    private static void testSetReceiveBufferSizeZero() throws Exception {
        ServerSocket ss = new ServerSocket();
        try {
            ss.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            ss.close();
        }
    }

    private static void testSetReceiveBufferSizeNegative() throws Exception {
        ServerSocket ss = new ServerSocket();
        try {
            ss.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            ss.close();
        }
    }

    private static void testSetReceiveBufferSizeOnClosedSocket() throws Exception {
        ServerSocket ss = new ServerSocket();
        ss.close();
        try {
            ss.setReceiveBufferSize(1024);
            throw new RuntimeException("Expected SocketException when setting receive buffer size on closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
