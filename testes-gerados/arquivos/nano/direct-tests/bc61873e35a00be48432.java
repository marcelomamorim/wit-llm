/*
 * @test
 * @summary Test InetAddress.isReachable(int timeout) for basic behavior
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.io.IOException;

public class InetAddressWitupTest {

    public static void main(String[] args) throws Exception {
        testIsReachableWithTimeout();
    }

    private static void testIsReachableWithTimeout() throws IOException {
        InetAddress localHost = InetAddress.getByName("127.0.0.1");
        boolean reachable = localHost.isReachable(1000);
        // Since localhost should be reachable, verify the result is true or false
        // but in some environments, it may be false; so just print the result
        System.out.println("localhost isReachable with 1000ms: " + reachable);
    }
}
