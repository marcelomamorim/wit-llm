/*
 * @test
 * @summary Test java.net.ServerSocket.setReceiveBufferSize(int) for valid and invalid inputs
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetReceiveBufferSizeValid();
        testSetReceiveBufferSizeZeroOrNegative();
        testSetReceiveBufferSizeOnClosedSocket();
    }

    private static void testSetReceiveBufferSizeValid() throws IOException, SocketException {
        ServerSocket ss = new ServerSocket(0);
        int validSize = 1024;
        ss.setReceiveBufferSize(validSize);
        int actualSize = ss.getReceiveBufferSize();
        if (actualSize < validSize) {
            throw new RuntimeException("setReceiveBufferSize did not set the buffer size correctly: expected at least " + validSize + ", got " + actualSize);
        }
        ss.close();
    }

    private static void testSetReceiveBufferSizeZeroOrNegative() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        try {
            ss.setReceiveBufferSize(0);
            throw new RuntimeException("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        try {
            ss.setReceiveBufferSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for size=-1");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        ss.close();
    }

    private static void testSetReceiveBufferSizeOnClosedSocket() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        ss.close();
        try {
            ss.setReceiveBufferSize(1024);
            throw new RuntimeException("Expected SocketException when setting receive buffer size on closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
