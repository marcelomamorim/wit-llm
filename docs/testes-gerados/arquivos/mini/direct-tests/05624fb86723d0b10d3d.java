/*
 * @test
 * @summary Test java.net.Socket.setTrafficClass(int) method
 * @run main SocketWitupTest
 */

import java.net.Socket;
import java.net.SocketException;

public class SocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidLow();
        testSetTrafficClassInvalidHigh();
        testSetTrafficClassOnClosedSocket();
    }

    private static void testSetTrafficClassValid() throws Exception {
        Socket s = new Socket();
        // Valid values: 0 and 255 boundary and a middle value 128
        s.setTrafficClass(0);
        s.setTrafficClass(128);
        s.setTrafficClass(255);
    }

    private static void testSetTrafficClassInvalidLow() {
        Socket s = new Socket();
        try {
            s.setTrafficClass(-1);
            throw new RuntimeException("Expected IllegalArgumentException for tc=-1");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc=-1", e);
        }
    }

    private static void testSetTrafficClassInvalidHigh() {
        Socket s = new Socket();
        try {
            s.setTrafficClass(256);
            throw new RuntimeException("Expected IllegalArgumentException for tc=256");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc=256", e);
        }
    }

    private static void testSetTrafficClassOnClosedSocket() throws Exception {
        Socket s = new Socket();
        s.close();
        try {
            s.setTrafficClass(0);
            throw new RuntimeException("Expected SocketException when setting traffic class on closed socket");
        } catch (SocketException se) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when setting traffic class on closed socket", e);
        }
    }
}
