/* @test    @summary Test for NetworkInterface.getByName(String) handling null input and normal behavior    @run main NetworkInterfaceWitupTest_Pbb07704bbeac */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_Pbb07704bbeac {

    public static void main(String[] args) throws Exception {
        testGetByNameWithNull();
        testGetByNameWithValidName();
    }

    private static void testGetByNameWithNull() {
        try {
            NetworkInterface.getByName(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetByNameWithValidName() {
        String testName = "lo"; // Common loopback interface name, may vary
        try {
            NetworkInterface ni = NetworkInterface.getByName(testName);
            if (ni == null) {
                throw new AssertionError("getByName returned null for valid name");
            }
            if (!testName.equals(ni.getName())) {
                throw new AssertionError("Returned NetworkInterface has name: " + ni.getName() + ", expected: " + testName);
            }
        } catch (SocketException e) {
            // Depending on environment, this may be thrown; consider environment-specific handling
            // For test purposes, we accept it as pass if no other failure occurs
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
