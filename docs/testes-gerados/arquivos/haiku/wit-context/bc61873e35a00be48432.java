/*
 * @test
 * @summary Verify InetAddress.isReachable(int) rejects negative timeouts with IllegalArgumentException
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.io.IOException;

public class InetAddressWitupTest {
    public static void main(String[] args) throws Exception {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        check(loopback, -1);
        check(loopback, Integer.MIN_VALUE);
    }

    private static void check(InetAddress addr, int timeout) throws IOException {
        try {
            addr.isReachable(timeout);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for timeout " + timeout);
    }
}
