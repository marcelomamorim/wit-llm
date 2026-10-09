/* @test
   @summary Verifies behavior of setTrafficClass method in DatagramSocket.
   @run main DatagramSocketWitupTest_P0010e0741edc
 */
import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P0010e0741edc {
    public static void main(String[] args) throws Exception {
        testSetTrafficClassValid();
        testSetTrafficClassNegative();
        testSetTrafficClassAboveRange();
        testSetTrafficClassWhenClosed();
    }

    private static void testSetTrafficClassValid() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            ds.setTrafficClass(0);
            ds.setTrafficClass(255);
        } catch (SocketException e) {
            throw new AssertionError("Unexpected SocketException for valid values", e);
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassNegative() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            boolean thrown = false;
            try {
                ds.setTrafficClass(-1);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for negative value");
            }
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassAboveRange() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        try {
            boolean thrown = false;
            try {
                ds.setTrafficClass(256);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for value above 255");
            }
        } finally {
            ds.close();
        }
    }

    private static void testSetTrafficClassWhenClosed() throws Exception {
        DatagramSocket ds = new DatagramSocket();
        ds.close();
        boolean thrown = false;
        try {
            ds.setTrafficClass(10);
        } catch (SocketException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected SocketException when socket is closed");
        }
    }
}
