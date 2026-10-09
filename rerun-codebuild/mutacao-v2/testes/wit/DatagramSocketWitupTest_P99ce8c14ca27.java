/*
 * @test
 * @summary Test DatagramSocket.setTrafficClass(int) for valid, invalid and closed socket cases
 * @run main DatagramSocketWitupTest_P99ce8c14ca27
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P99ce8c14ca27 {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidLow();
        testSetTrafficClassInvalidHigh();
        testSetTrafficClassOnClosedSocket();
    }

    private static void testSetTrafficClassValid() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            // Test valid values at boundary and middle
            ds.setTrafficClass(0);
            ds.setTrafficClass(128);
            ds.setTrafficClass(255);
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassInvalidLow() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            try {
                ds.setTrafficClass(-1);
                throw new RuntimeException("Expected IllegalArgumentException for tc < 0");
            } catch (IllegalArgumentException iae) {
                // expected
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testSetTrafficClassInvalidLow", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testSetTrafficClassInvalidHigh() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            try {
                ds.setTrafficClass(256);
                throw new RuntimeException("Expected IllegalArgumentException for tc > 255");
            } catch (IllegalArgumentException iae) {
                // expected
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testSetTrafficClassInvalidHigh", e);
        } finally {
            if (ds != null) ds.close();
        }
    }

    private static void testSetTrafficClassOnClosedSocket() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.close();
        try {
            ds.setTrafficClass(10);
            throw new RuntimeException("Expected SocketException when setting traffic class on closed socket");
        } catch (SocketException se) {
            // expected
        }
    }
}
