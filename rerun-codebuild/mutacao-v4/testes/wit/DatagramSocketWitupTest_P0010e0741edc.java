/* @test
   @summary Verifies behavior of setTrafficClass method in DatagramSocket.
   @run main DatagramSocketWitupTest_P0010e0741edc
 */
import java.net.DatagramSocket;
import java.net.SocketException;
public class DatagramSocketWitupTest_P0010e0741edc {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassBelowRange();
        testSetTrafficClassAboveRange();
        testSetTrafficClassOnClosedSocket();
    }

    private static void testSetTrafficClassValid() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.setTrafficClass(0);
            ds.setTrafficClass(255);
        } catch (Exception e) {
            throw new AssertionError("Valid traffic class values should not throw", e);
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassBelowRange() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            boolean exceptionThrown = false;
            try {
                ds.setTrafficClass(-1);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for tc < 0");
            }
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassAboveRange() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            boolean exceptionThrown = false;
            try {
                ds.setTrafficClass(256);
            } catch (IllegalArgumentException e) {
                exceptionThrown = true;
            }
            if (!exceptionThrown) {
                throw new AssertionError("Expected IllegalArgumentException for tc > 255");
            }
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassOnClosedSocket() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.close();
        boolean exceptionThrown = false;
        try {
            ds.setTrafficClass(10);
        } catch (SocketException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected SocketException when setting traffic class on closed socket");
        }
    }
}
