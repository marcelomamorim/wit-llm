/*
 * @test
 * @summary Test NetworkInterface.getByName(String) for null argument and normal usage
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        testGetByNameNull();
        testGetByNameNonNull();
    }

    private static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testGetByNameNonNull() throws SocketException {
        // Use a name that is unlikely to exist to test normal behavior
        // The method returns null if no interface with the name is found
        NetworkInterface ni = NetworkInterface.getByName("unlikely_interface_name_12345");
        if (ni != null) {
            // If an interface is found, verify its name matches
            if (!"unlikely_interface_name_12345".equals(ni.getName())) {
                throw new RuntimeException("Returned NetworkInterface name mismatch");
            }
        }
        // No exception expected
    }
}
