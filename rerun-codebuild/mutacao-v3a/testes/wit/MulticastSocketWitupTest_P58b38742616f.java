/* @test    @summary Test for MulticastSocket.setTimeToLive(int) method handling normal and exceptional cases    @run main MulticastSocketWitupTest_P58b38742616f */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest_P58b38742616f {

    public static void main(String[] args) throws Exception {
        testSetTimeToLiveWithinRange();
        testSetTimeToLiveBelowRange();
        testSetTimeToLiveAboveRange();
        testSetTimeToLiveOnClosedSocket();
    }

    static void testSetTimeToLiveWithinRange() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(128);
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for valid TTL", e);
        }
    }

    static void testSetTimeToLiveBelowRange() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(-1);
            throw new AssertionError("Expected IllegalArgumentException for TTL < 0");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testSetTimeToLiveAboveRange() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        try {
            socket.setTimeToLive(256);
            throw new AssertionError("Expected IllegalArgumentException for TTL > 255");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testSetTimeToLiveOnClosedSocket() throws IOException {
        MulticastSocket socket = new MulticastSocket();
        socket.close();
        try {
            socket.setTimeToLive(64);
            // No exception expected here based on current source code; method may throw SocketException if socket is closed
        } catch (SocketException e) {
            // Expected if socket is closed
        }
    }
}
