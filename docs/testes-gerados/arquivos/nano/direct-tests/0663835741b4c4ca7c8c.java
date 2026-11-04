/* @test    @summary Test MulticastSocket.setTimeToLive(int) for valid and invalid inputs    @run main MulticastSocketWitupTest */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest {

    public static void main(String[] args) {
        testSetTimeToLiveValid();
        testSetTimeToLiveInvalidNegative();
        testSetTimeToLiveInvalidAboveRange();
        testSetTimeToLiveWhenSocketClosed();
    }

    static void testSetTimeToLiveValid() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(128);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception in valid TTL test", e);
        }
    }

    static void testSetTimeToLiveInvalidNegative() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(-1);
            socket.close();
            throw new AssertionError("Expected IllegalArgumentException for negative TTL");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in negative TTL test", e);
        }
    }

    static void testSetTimeToLiveInvalidAboveRange() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(256);
            socket.close();
            throw new AssertionError("Expected IllegalArgumentException for TTL above 255");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in above range TTL test", e);
        }
    }

    static void testSetTimeToLiveWhenSocketClosed() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.close();
            socket.setTimeToLive(10);
            throw new AssertionError("Expected SocketException when socket is closed");
        } catch (SocketException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in closed socket test", e);
        }
    }
}
