/*
 * @test
 * @summary Test java.net.MulticastSocket.setTimeToLive(int) for valid and invalid TTL values and closed socket
 * @run main MulticastSocketWitupTest
 */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testValidTTL();
        testInvalidTTLBelowRange();
        testInvalidTTLAboveRange();
        testSetTTLOnClosedSocket();
    }

    private static void testValidTTL() throws IOException {
        MulticastSocket ms = new MulticastSocket();
        try {
            ms.setTimeToLive(0); // minimum valid
            ms.setTimeToLive(128); // mid valid
            ms.setTimeToLive(255); // maximum valid
        } finally {
            ms.close();
        }
    }

    private static void testInvalidTTLBelowRange() {
        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket();
            ms.setTimeToLive(-1);
            throw new RuntimeException("Expected IllegalArgumentException for TTL < 0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (ms != null) ms.close();
        }
    }

    private static void testInvalidTTLAboveRange() {
        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket();
            ms.setTimeToLive(256);
            throw new RuntimeException("Expected IllegalArgumentException for TTL > 255");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        } finally {
            if (ms != null) ms.close();
        }
    }

    private static void testSetTTLOnClosedSocket() {
        MulticastSocket ms = new MulticastSocket();
        try {
            ms.close();
            try {
                ms.setTimeToLive(1);
                throw new RuntimeException("Expected SocketException when setting TTL on closed socket");
            } catch (SocketException e) {
                // expected
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
