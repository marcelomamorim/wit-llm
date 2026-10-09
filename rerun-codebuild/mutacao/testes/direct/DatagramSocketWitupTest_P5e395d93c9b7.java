/* @test    @summary Test setTrafficClass method of DatagramSocket for valid and invalid inputs    @run main DatagramSocketWitupTest_P5e395d93c9b7 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P5e395d93c9b7 {

    public static void main(String[] args) {
        testSetTrafficClassValid();
        testSetTrafficClassInvalidNegative();
        testSetTrafficClassInvalidAboveRange();
        testSetTrafficClassWhenSocketClosed();
    }

    private static void testSetTrafficClassValid() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(0);
            ds.setTrafficClass(128);
            ds.setTrafficClass(255);
            ds.close();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid setTrafficClass", e);
        }
    }

    private static void testSetTrafficClassInvalidNegative() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(-1);
            ds.close();
            throw new AssertionError("Expected IllegalArgumentException for negative tc");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for negative tc", e);
        }
    }

    private static void testSetTrafficClassInvalidAboveRange() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(256);
            ds.close();
            throw new AssertionError("Expected IllegalArgumentException for tc > 255");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for tc > 255", e);
        }
    }

    private static void testSetTrafficClassWhenSocketClosed() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.close();
            ds.setTrafficClass(10);
            throw new AssertionError("Expected SocketException when socket is closed");
        } catch (SocketException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception when socket is closed", e);
        }
    }
}
