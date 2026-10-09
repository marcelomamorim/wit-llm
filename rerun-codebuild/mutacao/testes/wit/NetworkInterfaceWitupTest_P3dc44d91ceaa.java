/*
 * @test
 * @summary Tests for java.net.NetworkInterface.getByName(String)
 * @run main NetworkInterfaceWitupTest_P3dc44d91ceaa
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P3dc44d91ceaa {

    public static void main(String[] args) {
        testGetByNameWithNull();
        testGetByNameWithValidName();
    }

    private static void testGetByNameWithNull() {
        try {
            NetworkInterface.getByName(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    private static void testGetByNameWithValidName() {
        try {
            // Use a common interface name, e.g., "lo" or "eth0" if available
            String testName = "lo";
            NetworkInterface ni = NetworkInterface.getByName(testName);
            if (ni != null && testName.equals(ni.getName())) {
                System.out.println("Passed: getByName returned interface with expected name");
            } else {
                System.err.println("Failed: getByName did not return expected interface");
            }
        } catch (SocketException e) {
            System.err.println("Failed: SocketException thrown: " + e);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
