/*
 * @test
 * @summary Test java.net.InetAddress.isReachable(int) for valid and invalid timeout values
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.io.IOException;

public class InetAddressWitupTest {
    public static void main(String[] args) throws Exception {
        testIsReachableWithValidTimeout();
        testIsReachableWithZeroTimeout();
        testIsReachableWithNegativeTimeout();
    }

    private static void testIsReachableWithValidTimeout() throws Exception {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        boolean reachable = loopback.isReachable(1000);
        // reachable can be true or false depending on environment, just ensure no exception
        if (reachable != true && reachable != false) {
            throw new AssertionError("Unexpected return value from isReachable");
        }
    }

    private static void testIsReachableWithZeroTimeout() throws Exception {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        boolean reachable = loopback.isReachable(0);
        // zero timeout is allowed, result can be true or false
        if (reachable != true && reachable != false) {
            throw new AssertionError("Unexpected return value from isReachable with zero timeout");
        }
    }

    private static void testIsReachableWithNegativeTimeout() {
        try {
            InetAddress loopback = InetAddress.getByName("127.0.0.1");
            loopback.isReachable(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            // IOException is not expected for negative timeout, fail
            throw new AssertionError("Unexpected IOException for negative timeout", e);
        }
    }
}
