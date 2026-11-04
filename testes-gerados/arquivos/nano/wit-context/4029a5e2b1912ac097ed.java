/*
 * @test
 * @summary Test for setSoTimeout method in SocketAdaptor.
 * @run main SocketAdaptorWitupTest
 */

import java.net.SocketException;
import sun.nio.ch.SocketAdaptor;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) {
        testSetSoTimeoutNegative();
        testSetSoTimeoutZero();
        testSetSoTimeoutPositive();
    }

    public static void testSetSoTimeoutNegative() {
        SocketAdaptor sa = createSocketAdaptor();
        try {
            sa.setSoTimeout(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    public static void testSetSoTimeoutZero() {
        SocketAdaptor sa = createSocketAdaptor();
        try {
            sa.setSoTimeout(0);
            System.out.println("Passed: setSoTimeout(0) executed without exception");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for zero timeout: " + e);
        }
    }

    public static void testSetSoTimeoutPositive() {
        SocketAdaptor sa = createSocketAdaptor();
        try {
            sa.setSoTimeout(1000);
            System.out.println("Passed: setSoTimeout(1000) executed without exception");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for positive timeout: " + e);
        }
    }

    private static SocketAdaptor createSocketAdaptor() {
        // Create a dummy SocketChannel to pass to SocketAdaptor
        // Since SocketChannelImpl is package-private, we simulate with null and catch exception
        // but as per context, we can instantiate via the static method if available.
        // Here, we create a dummy SocketAdaptor with null, which is acceptable for testing setSoTimeout.
        return new SocketAdaptor(null) {
            @Override
            public void setSoTimeout(int timeout) throws SocketException {
                super.setSoTimeout(timeout);
            }
        };
    }
}
