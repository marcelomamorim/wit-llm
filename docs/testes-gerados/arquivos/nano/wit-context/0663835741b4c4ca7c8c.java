/* @test    @summary Test for MulticastSocket.setTimeToLive(int) method behavior and exception handling    @run main MulticastSocketWitupTest */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest {

    public static void main(String[] args) {
        testSetTimeToLiveValidValues();
        testSetTimeToLiveBelowRange();
        testSetTimeToLiveAboveRange();
        testSetTimeToLiveWhenClosed();
    }

    private static void testSetTimeToLiveValidValues() {
        try {
            MulticastSocket socket = new MulticastSocket();
            for (int ttl = 0; ttl <= 255; ttl += 51) {
                socket.setTimeToLive(ttl);
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid values test", e);
        }
    }

    private static void testSetTimeToLiveBelowRange() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(-1);
            throw new AssertionError("Expected IllegalArgumentException for ttl = -1");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in below range test", e);
        }
    }

    private static void testSetTimeToLiveAboveRange() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.setTimeToLive(256);
            throw new AssertionError("Expected IllegalArgumentException for ttl = 256");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in above range test", e);
        }
    }

    private static void testSetTimeToLiveWhenClosed() {
        try {
            MulticastSocket socket = new MulticastSocket();
            socket.close();
            socket.setTimeToLive(10);
            throw new AssertionError("Expected SocketException when socket is closed");
        } catch (SocketException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in closed socket test", e);
        }
    }
}
