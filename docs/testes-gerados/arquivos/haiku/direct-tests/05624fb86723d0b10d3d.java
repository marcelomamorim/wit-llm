/*
 * @test
 * @summary Checks observable behavior of Socket.setTrafficClass range and closed-state validation
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeTrafficClassThrowsIAE();
        testTooLargeTrafficClassThrowsIAE();
        testRangeCheckPrecedesClosedCheck();
        testClosedSocketThrowsSocketException();
        testBoundaryValuesDoNotThrowIAE();
    }

    static void testNegativeTrafficClassThrowsIAE() throws Exception {
        Socket s = new Socket();
        try {
            s.setTrafficClass(-1);
            throw new RuntimeException("Expected IllegalArgumentException for tc=-1");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            s.close();
        }
    }

    static void testTooLargeTrafficClassThrowsIAE() throws Exception {
        Socket s = new Socket();
        try {
            s.setTrafficClass(256);
            throw new RuntimeException("Expected IllegalArgumentException for tc=256");
        } catch (IllegalArgumentException expected) {
            // expected
        } finally {
            s.close();
        }
    }

    static void testRangeCheckPrecedesClosedCheck() throws Exception {
        Socket s = new Socket();
        s.close();
        try {
            s.setTrafficClass(-5);
            throw new RuntimeException("Expected IllegalArgumentException for closed socket with tc=-5");
        } catch (IllegalArgumentException expected) {
            // expected: range check happens before closed check
        }
    }

    static void testClosedSocketThrowsSocketException() throws Exception {
        Socket s = new Socket();
        s.close();
        try {
            s.setTrafficClass(0);
            throw new RuntimeException("Expected SocketException for closed socket");
        } catch (SocketException expected) {
            // expected
        }
    }

    static void testBoundaryValuesDoNotThrowIAE() throws Exception {
        int[] values = {0, 255};
        for (int tc : values) {
            Socket s = new Socket();
            try {
                s.setTrafficClass(tc);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unexpected IllegalArgumentException for tc=" + tc, e);
            } catch (SocketException e) {
                // option may be unsupported on unconnected socket; only IAE is contractually checked here
            } finally {
                s.close();
            }
        }
    }
}
