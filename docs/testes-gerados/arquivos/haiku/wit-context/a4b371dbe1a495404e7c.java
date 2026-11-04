/*
 * @test
 * @summary Verify NetworkInterface.getByName throws NullPointerException for null name and returns null for an unknown interface name
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            NetworkInterface.getByName(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException not thrown for null name");
        }

        NetworkInterface ni = NetworkInterface.getByName("witup-no-such-if-0");
        if (ni != null) {
            throw new RuntimeException("Expected null for unknown interface name");
        }
    }
}
