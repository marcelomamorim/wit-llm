/*
 * @test
 * @summary Test java.net.NetworkInterface.getByName(String) method
 * @run main NetworkInterfaceWitupTest_P3e506330ba92
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P3e506330ba92 {
    public static void main(String[] args) throws Exception {
        testGetByNameNull();
        testGetByNameEmptyString();
        testGetByNameNonExistent();
    }

    private static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null name", e);
        }
    }

    private static void testGetByNameEmptyString() throws Exception {
        NetworkInterface ni = NetworkInterface.getByName("");
        // The result may be null or a valid interface depending on platform
        // Just check no exception and that if non-null, name equals empty string
        if (ni != null && !"".equals(ni.getName())) {
            throw new RuntimeException("Returned NetworkInterface name is not empty string");
        }
    }

    private static void testGetByNameNonExistent() throws Exception {
        String unlikelyName = "nonexistent_interface_12345";
        NetworkInterface ni = NetworkInterface.getByName(unlikelyName);
        if (ni != null) {
            // On some platforms, may return null if interface not found
            // If non-null, name must match requested
            if (!unlikelyName.equals(ni.getName())) {
                throw new RuntimeException("Returned NetworkInterface name does not match requested");
            }
        }
    }
}
