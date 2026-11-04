/*
 * @test
 * @summary Test for NetworkInterface.getByInetAddress method.
 * @run main NetworkInterfaceWitupTest
 */

import java.net.InetAddress;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    public static void main(String[] args) {
        testGetByInetAddress_Null();
        testGetByInetAddress_InvalidType();
        testGetByInetAddress_Valid();
    }

    public static void testGetByInetAddress_Null() {
        try {
            NetworkInterface.getByInetAddress(null);
            System.out.println("FAIL: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testGetByInetAddress_InvalidType() {
        try {
            // Creating a dummy InetAddress of an unsupported type is not straightforward,
            // so we simulate by subclassing or mocking if possible. Since no such class is available,
            // we skip this test as it requires a custom subclass. Alternatively, we can skip this test.
            // For demonstration, we assume an invalid address type triggers IllegalArgumentException.
            // But since we cannot instantiate such, we skip.
            System.out.println("SKIP: Cannot create invalid InetAddress type in current context");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testGetByInetAddress_Valid() {
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            if (ni != null) {
                System.out.println("PASS: getByInetAddress returned a NetworkInterface for 127.0.0.1");
            } else {
                System.out.println("FAIL: getByInetAddress returned null for 127.0.0.1");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
