/*
 * @test
 * @summary Test java.net.MulticastSocket.setTimeToLive(int) for valid and invalid TTL values
 * @run main MulticastSocketWitupTest_P23486d14a7c0
 */

import java.net.MulticastSocket;
import java.net.SocketException;
import java.io.IOException;

public class MulticastSocketWitupTest_P23486d14a7c0 {
    public static void main(String[] args) throws Exception {
        testValidTTL();
        testInvalidTTLBelowRange();
        testInvalidTTLAboveRange();
        testClosedSocketThrows();
    }

    private static void testValidTTL() throws IOException {
        MulticastSocket ms = new MulticastSocket();
        // Test boundary values 0 and 255 and a middle value 128
        ms.setTimeToLive(0);
        ms.setTimeToLive(128);
        ms.setTimeToLive(255);
        ms.close();
    }

    private static void testInvalidTTLBelowRange() throws IOException {
        MulticastSocket ms = new MulticastSocket();
        try {
            ms.setTimeToLive(-1);
            throw new RuntimeException("Expected IllegalArgumentException for ttl=-1");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            ms.close();
        }
    }

    private static void testInvalidTTLAboveRange() throws IOException {
        MulticastSocket ms = new MulticastSocket();
        try {
            ms.setTimeToLive(256);
            throw new RuntimeException("Expected IllegalArgumentException for ttl=256");
        } catch (IllegalArgumentException iae) {
            // expected
        } finally {
            ms.close();
        }
    }

    private static void testClosedSocketThrows() throws IOException {
        MulticastSocket ms = new MulticastSocket();
        ms.close();
        try {
            ms.setTimeToLive(10);
            throw new RuntimeException("Expected SocketException when setting TTL on closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
