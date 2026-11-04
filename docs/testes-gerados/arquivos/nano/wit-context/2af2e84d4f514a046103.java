/*
 * @test
 * @summary Tests for NetworkInterface.getByIndex(int)
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    public static void main(String[] args) {
        testGetByIndexNegative();
        testGetByIndexZero();
        testGetByIndexPositive();
    }

    private static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            System.err.println("Failed: Expected IllegalArgumentException for index -1");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for index -1");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for index -1: " + e);
        }
    }

    private static void testGetByIndexZero() {
        try {
            NetworkInterface ni = NetworkInterface.getByIndex(0);
            if (ni != null) {
                System.out.println("Passed: getByIndex(0) returned a NetworkInterface instance");
            } else {
                System.err.println("Failed: getByIndex(0) returned null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception thrown for index 0: " + e);
        }
    }

    private static void testGetByIndexPositive() {
        try {
            // Use a typical valid index, e.g., 1, assuming at least one interface exists
            NetworkInterface ni = NetworkInterface.getByIndex(1);
            if (ni != null) {
                System.out.println("Passed: getByIndex(1) returned a NetworkInterface instance");
            } else {
                System.err.println("Failed: getByIndex(1) returned null");
            }
        } catch (SocketException e) {
            System.out.println("Skipped: SocketException occurred, possibly no interface with index 1");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for index 1: " + e);
        }
    }
}
