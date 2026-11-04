/*
 * @test
 * @summary Verifies that InetAddress.isReachable(int) reports the loopback address as reachable
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;

public class InetAddressWitupTest {
    public static void main(String[] args) throws Exception {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        boolean reachable = loopback.isReachable(5000);
        if (!reachable) {
            throw new RuntimeException("Loopback address expected to be reachable");
        }
    }
}
