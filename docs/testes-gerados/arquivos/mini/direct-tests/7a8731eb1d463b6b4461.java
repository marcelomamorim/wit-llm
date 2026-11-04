/*
 * @test
 * @summary Test java.net.DatagramSocket.setTrafficClass(int) method
 * @run main DatagramSocketWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidLow();
        testSetTrafficClassInvalidHigh();
        testSetTrafficClassOnClosedSocket();
    }

    private static void testSetTrafficClassValid() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        // Test valid values at boundary and middle
        int[] validValues = {0, 1, 127, 255};
        for (int tc : validValues) {
            ds.setTrafficClass(tc);
        }
        ds.close();
    }

    private static void testSetTrafficClassInvalidLow() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(-1);
            throw new RuntimeException("Expected IllegalArgumentException for tc=-1");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testSetTrafficClassInvalidHigh() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(256);
            throw new RuntimeException("Expected IllegalArgumentException for tc=256");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testSetTrafficClassOnClosedSocket() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.close();
        try {
            ds.setTrafficClass(0);
            throw new RuntimeException("Expected SocketException on closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
