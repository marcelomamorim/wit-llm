/*
 * @test
 * @summary Tests for java.net.NetworkInterface.getByName
 * @run main NetworkInterfaceWitupTest_P0a7e9032713a
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P0a7e9032713a {
    public static void main(String[] args) {
        testGetByNameWithNull();
        testGetByNameWithValidName();
    }

    private static void testGetByNameWithNull() {
        try {
            NetworkInterface.getByName(null);
            System.err.println("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null name");
        } catch (Exception e) {
            System.err.println("Unexpected exception for null name: " + e);
        }
    }

    private static void testGetByNameWithValidName() {
        try {
            // Use a common interface name if available, otherwise skip
            NetworkInterface ni = NetworkInterface.getByName("lo0");
            if (ni != null) {
                System.out.println("Successfully retrieved NetworkInterface with name 'lo0': " + ni.getName());
            } else {
                System.out.println("No NetworkInterface found with name 'lo0'");
            }
        } catch (SocketException e) {
            System.err.println("SocketException thrown: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
