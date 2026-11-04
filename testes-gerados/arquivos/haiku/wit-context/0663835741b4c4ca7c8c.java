/*
 * @test
 * @summary Verifies MulticastSocket.setTimeToLive range validation and closed-socket behavior
 * @run main MulticastSocketWitupTest
 */

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MulticastSocketWitupTest {

    public static void main(String[] args) throws Exception {
        testOutOfRangeTtlThrowsIllegalArgument();
        testBoundaryTtlAcceptedOnOpenSocket();
        testValidTtlOnClosedSocketThrowsSocketException();
        testInvalidTtlOnClosedSocketStillThrowsIllegalArgument();
    }

    private static void testOutOfRangeTtlThrowsIllegalArgument() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            expectIllegalArgument(s, -1);
            expectIllegalArgument(s, 256);
            expectIllegalArgument(s, Integer.MIN_VALUE);
            expectIllegalArgument(s, Integer.MAX_VALUE);
        } finally {
            s.close();
        }
    }

    private static void testBoundaryTtlAcceptedOnOpenSocket() throws IOException {
        MulticastSocket s = new MulticastSocket();
        try {
            s.setTimeToLive(0);
            s.setTimeToLive(1);
            s.setTimeToLive(255);
        } finally {
            s.close();
        }
    }

    private static void testValidTtlOnClosedSocketThrowsSocketException() throws IOException {
        MulticastSocket s = new MulticastSocket();
        s.close();
        try {
            s.setTimeToLive(1);
            throw new RuntimeException("Expected SocketException for closed socket");
        } catch (SocketException expected) {
            // expected
        }
    }

    private static void testInvalidTtlOnClosedSocketStillThrowsIllegalArgument() throws IOException {
        MulticastSocket s = new MulticastSocket();
        s.close();
        expectIllegalArgument(s, -1);
        expectIllegalArgument(s, 256);
    }

    private static void expectIllegalArgument(MulticastSocket s, int ttl) throws IOException {
        try {
            s.setTimeToLive(ttl);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for ttl=" + ttl);
    }
}
