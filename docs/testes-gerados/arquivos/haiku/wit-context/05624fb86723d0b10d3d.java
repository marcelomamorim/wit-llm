/*
 * @test
 * @summary Verifies setTrafficClass range validation and closed-socket behavior
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {

    public static void main(String[] args) throws Exception {
        testOutOfRangeThrowsIAE();
        testBoundaryValuesAccepted();
        testClosedSocketThrowsSocketException();
        testRangeCheckPrecedesClosedCheck();
    }

    static void testOutOfRangeThrowsIAE() throws Exception {
        Socket s = new Socket();
        try {
            expectIAE(s, -1);
            expectIAE(s, 256);
            expectIAE(s, Integer.MIN_VALUE);
            expectIAE(s, Integer.MAX_VALUE);
        } finally {
            s.close();
        }
    }

    static void testBoundaryValuesAccepted() throws Exception {
        Socket s = new Socket();
        try {
            try {
                s.setTrafficClass(0);
                s.setTrafficClass(255);
            } catch (SocketException e) {
                // Platform may not support IP_TOS on this socket; only the
                // range check (IllegalArgumentException) is asserted here.
            }
        } finally {
            s.close();
        }
    }

    static void testClosedSocketThrowsSocketException() throws Exception {
        Socket s = new Socket();
        s.close();
        try {
            s.setTrafficClass(0);
            throw new RuntimeException("expected SocketException for closed socket");
        } catch (SocketException expected) {
            // expected
        }
    }

    static void testRangeCheckPrecedesClosedCheck() throws Exception {
        Socket s = new Socket();
        s.close();
        expectIAE(s, -1);
        expectIAE(s, 256);
    }

    static void expectIAE(Socket s, int tc) throws SocketException {
        try {
            s.setTrafficClass(tc);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for tc=" + tc);
    }
}
