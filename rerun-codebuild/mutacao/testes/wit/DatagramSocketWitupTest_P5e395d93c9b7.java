/*
 * @test
 * @summary Tests for java.net.DatagramSocket.setTrafficClass(int)
 * @run main DatagramSocketWitupTest_P5e395d93c9b7
 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P5e395d93c9b7 {

    public static void main(String[] args) {
        testSetTrafficClassValid();
        testSetTrafficClassNegative();
        testSetTrafficClassAboveRange();
        testSetTrafficClassClosedSocket();
    }

    static void testSetTrafficClassValid() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(0);
            ds.close();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid traffic class", e);
        }
    }

    static void testSetTrafficClassNegative() {
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

    static void testSetTrafficClassAboveRange() {
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

    static void testSetTrafficClassClosedSocket() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.close();
            ds.setTrafficClass(10);
            throw new AssertionError("Expected SocketException for closed socket");
        } catch (java.net.SocketException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for closed socket", e);
        }
    }
}
