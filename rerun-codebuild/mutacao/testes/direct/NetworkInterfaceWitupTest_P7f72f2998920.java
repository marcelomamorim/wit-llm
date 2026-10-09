/* @test    @summary Test getByIndex with valid and invalid indices    @run main NetworkInterfaceWitupTest_P7f72f2998920 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P7f72f2998920 {

    public static void main(String[] args) {
        testGetByIndexValid();
        testGetByIndexNegative();
    }

    static void testGetByIndexValid() {
        try {
            // Use a known valid index, e.g., 1, assuming system has at least one interface
            NetworkInterface ni = NetworkInterface.getByIndex(1);
            if (ni == null) {
                throw new AssertionError("Expected non-null NetworkInterface for index 1");
            }
            System.out.println("testGetByIndexValid passed: " + ni.getName());
        } catch (SocketException e) {
            throw new AssertionError("SocketException thrown during valid index test", e);
        }
    }

    static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("testGetByIndexNegative passed: " + e.getMessage());
        } catch (SocketException e) {
            throw new AssertionError("Unexpected SocketException for negative index", e);
        }
    }
}
