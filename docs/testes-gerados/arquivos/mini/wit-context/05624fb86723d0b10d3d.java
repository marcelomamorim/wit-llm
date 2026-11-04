/*
 * @test
 * @summary Test java.net.Socket.setTrafficClass(int) for valid, invalid and closed socket cases
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidLow();
        testSetTrafficClassInvalidHigh();
        testSetTrafficClassClosedSocket();
    }

    private static void testSetTrafficClassValid() throws Exception {
        Socket s = new Socket();
        // Valid values: 0 and 255 boundary and a middle value
        s.setTrafficClass(0);
        s.setTrafficClass(128);
        s.setTrafficClass(255);
        s.close();
    }

    private static void testSetTrafficClassInvalidLow() {
        Socket s = new Socket();
        try {
            s.setTrafficClass(-1);
            throw new RuntimeException("Expected IllegalArgumentException for tc < 0");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc < 0", e);
        } finally {
            try { s.close(); } catch (Exception ignore) {}
        }
    }

    private static void testSetTrafficClassInvalidHigh() {
        Socket s = new Socket();
        try {
            s.setTrafficClass(256);
            throw new RuntimeException("Expected IllegalArgumentException for tc > 255");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc > 255", e);
        } finally {
            try { s.close(); } catch (Exception ignore) {}
        }
    }

    private static void testSetTrafficClassClosedSocket() throws Exception {
        Socket s = new Socket();
        s.close();
        try {
            s.setTrafficClass(0);
            throw new RuntimeException("Expected SocketException for closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
