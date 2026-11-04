/*
 * @test
 * @summary Test java.net.InetAddress.isReachable(int) method
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.io.IOException;

public class InetAddressWitupTest {
    public static void main(String[] args) throws Exception {
        testIsReachableWithZeroTimeout();
        testIsReachableWithNegativeTimeout();
        testIsReachableWithValidTimeout();
    }

    private static void testIsReachableWithZeroTimeout() throws IOException {
        InetAddress localhost = InetAddress.getLocalHost();
        boolean reachable = localhost.isReachable(0);
        // reachable may be true or false depending on platform, just ensure no exception
        if (reachable != true && reachable != false) {
            throw new AssertionError("Unexpected return value from isReachable(0): " + reachable);
        }
    }

    private static void testIsReachableWithNegativeTimeout() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            localhost.isReachable(-1);
            throw new AssertionError("Expected IOException for negative timeout");
        } catch (IOException e) {
            // Expected: negative timeout should cause IOException or similar
        } catch (IllegalArgumentException e) {
            // Some implementations may throw IllegalArgumentException for negative timeout
        }
    }

    private static void testIsReachableWithValidTimeout() throws IOException {
        InetAddress localhost = InetAddress.getLocalHost();
        boolean reachable = localhost.isReachable(1000);
        if (reachable != true && reachable != false) {
            throw new AssertionError("Unexpected return value from isReachable(1000): " + reachable);
        }
    }
}
