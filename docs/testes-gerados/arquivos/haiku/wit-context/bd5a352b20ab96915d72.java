/*
 * @test
 * @summary Verifies NetworkInterface.getByInetAddress null-argument handling and that a valid loopback address does not trigger argument-validation exceptions
 * @run main NetworkInterfaceWitupTest
 */

import java.net.InetAddress;
import java.net.NetworkInterface;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            NetworkInterface.getByInetAddress(null);
            throw new RuntimeException("NullPointerException expected for null address");
        } catch (NullPointerException expected) {
        }

        InetAddress lo = InetAddress.getLoopbackAddress();
        try {
            NetworkInterface.getByInetAddress(lo);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new RuntimeException("Unexpected argument-validation exception for loopback address", e);
        }
    }
}
