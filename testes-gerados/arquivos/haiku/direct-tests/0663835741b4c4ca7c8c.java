/*
 * @test
 * @summary Verify MulticastSocket.setTimeToLive argument validation, closed-socket behavior and TTL round trip
 * @run main MulticastSocketWitupTest
 */

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MulticastSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTtlRejected();
        testTtlAboveRangeRejected();
        testBoundaryTtlRejectedAboveMax();
        testClosedSocketRejectsValidTtl();
        testClosedSocketRangeCheckHasPriority();
        testTtlRoundTripOpenSocket();
        testTtlZeroAndMaxRoundTrip();
    }

    static void testNegativeTtlRejected() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            boolean thrown = false;
            try {
                s.setTimeToLive(-1);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for ttl=-1");
            }
        } finally {
            s.close();
        }
    }

    static void testTtlAboveRangeRejected() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            boolean thrown = false;
            try {
                s.setTimeToLive(256);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for ttl=256");
            }
        } finally {
            s.close();
        }
    }

    static void testBoundaryTtlRejectedAboveMax() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            boolean thrown = false;
            try {
                s.setTimeToLive(Integer.MAX_VALUE);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for ttl=Integer.MAX_VALUE");
            }
        } finally {
            s.close();
        }
    }

    static void testClosedSocketRejectsValidTtl() throws IOException {
        MulticastSocket s = new MulticastSocket();
        s.close();
        boolean thrown = false;
        try {
            s.setTimeToLive(1);
        } catch (SocketException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected SocketException for closed socket with ttl=1");
        }
    }

    static void testClosedSocketRangeCheckHasPriority() throws IOException {
        MulticastSocket s = new MulticastSocket();
        s.close();
        boolean thrown = false;
        try {
            s.setTimeToLive(300);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException before closed check for ttl=300");
        }
    }

    static void testTtlRoundTripOpenSocket() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            s.setTimeToLive(1);
            int ttl = s.getTimeToLive();
            if (ttl != 1) {
                throw new RuntimeException("Expected TTL 1 but got " + ttl);
            }
        } finally {
            s.close();
        }
    }

    static void testTtlZeroAndMaxRoundTrip() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            s.setTimeToLive(0);
            int ttl = s.getTimeToLive();
            if (ttl != 0) {
                throw new RuntimeException("Expected TTL 0 but got " + ttl);
            }
            s.setTimeToLive(255);
            ttl = s.getTimeToLive();
            if (ttl != 255) {
                throw new RuntimeException("Expected TTL 255 but got " + ttl);
            }
        } finally {
            s.close();
        }
    }
}
