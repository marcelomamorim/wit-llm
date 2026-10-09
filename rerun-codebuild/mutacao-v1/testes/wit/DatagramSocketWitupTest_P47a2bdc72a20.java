/* @test    @summary Test setting traffic class with valid and invalid values    @run main DatagramSocketWitupTest_P47a2bdc72a20 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P47a2bdc72a20 {

    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidNegative();
        testSetTrafficClassInvalidAbove255();
        testSetTrafficClassWhenSocketClosed();
    }

    private static void testSetTrafficClassValid() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.setTrafficClass(0);
            ds.setTrafficClass(255);
        } catch (SocketException se) {
            throw new AssertionError("Unexpected SocketException for valid values", se);
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassInvalidNegative() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (SocketException se) {
            throw new AssertionError("Unexpected SocketException for negative value", se);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }

    private static void testSetTrafficClassInvalidAbove255() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(256);
            throw new AssertionError("Expected IllegalArgumentException for value above 255");
        } catch (IllegalArgumentException iae) {
            // expected
        } catch (SocketException se) {
            throw new AssertionError("Unexpected SocketException for value above 255", se);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }

    private static void testSetTrafficClassWhenSocketClosed() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.close();
        try {
            ds.setTrafficClass(10);
            throw new AssertionError("Expected SocketException when socket is closed");
        } catch (SocketException se) {
            // expected
        }
    }
}
