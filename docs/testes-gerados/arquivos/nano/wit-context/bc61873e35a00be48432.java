/*
 * @test
 * @summary Test for InetAddress.isReachable(int) method, including exception path for invalid timeout.
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.io.IOException;

public class InetAddressWitupTest {

    public static void main(String[] args) {
        testIsReachableWithValidTimeout();
        testIsReachableWithNegativeTimeout();
    }

    static void testIsReachableWithValidTimeout() {
        try {
            InetAddress address = InetAddress.getLoopbackAddress();
            boolean result = address.isReachable(1000);
            System.out.println("isReachable with valid timeout: " + result);
        } catch (IOException e) {
            System.err.println("Unexpected IOException for valid timeout: " + e);
        }
    }

    static void testIsReachableWithNegativeTimeout() {
        try {
            InetAddress address = InetAddress.getLoopbackAddress();
            boolean result = address.isReachable(-1);
            System.out.println("isReachable with negative timeout: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected IllegalArgumentException for negative timeout: " + e);
        } catch (IOException e) {
            System.err.println("Unexpected IOException for negative timeout: " + e);
        }
    }
}
