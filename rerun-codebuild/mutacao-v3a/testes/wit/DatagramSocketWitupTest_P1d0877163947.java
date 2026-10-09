/* @test    @summary Test setting traffic class with valid and invalid values    @run main DatagramSocketWitupTest_P1d0877163947 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P1d0877163947 {

    public static void main(String[] args) throws Exception {
        testValidTrafficClass();
        testTrafficClassBelowRange();
        testTrafficClassAboveRange();
        testSetTrafficClassWhenSocketClosed();
    }

    private static void testValidTrafficClass() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.setTrafficClass(0);
            ds.setTrafficClass(255);
            System.out.println("testValidTrafficClass passed");
        } catch (Exception e) {
            throw new AssertionError("Valid traffic class values should not throw", e);
        }
        ds.close();
    }

    private static void testTrafficClassBelowRange() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(-1);
            throw new AssertionError("Expected IllegalArgumentException for tc = -1");
        } catch (IllegalArgumentException e) {
            System.out.println("testTrafficClassBelowRange passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for tc = -1", e);
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }

    private static void testTrafficClassAboveRange() {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            ds.setTrafficClass(256);
            throw new AssertionError("Expected IllegalArgumentException for tc = 256");
        } catch (IllegalArgumentException e) {
            System.out.println("testTrafficClassAboveRange passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for tc = 256", e);
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
            throw new AssertionError("Expected SocketException when setting traffic class on closed socket");
        } catch (SocketException e) {
            System.out.println("testSetTrafficClassWhenSocketClosed passed");
        }
    }
}
