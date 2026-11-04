/*
 * @test
 * @summary Verify NetworkInterface.getByName rejects null names and returns null for an unknown interface name
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNullPointerException();
        testUnknownNameReturnsNull();
    }

    static void testNullNameThrowsNullPointerException() throws Exception {
        try {
            NetworkInterface.getByName(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null name");
    }

    static void testUnknownNameReturnsNull() throws SocketException {
        String name = "witup-no-such-if-0";
        NetworkInterface ni = NetworkInterface.getByName(name);
        if (ni != null) {
            throw new RuntimeException("Expected null for unknown interface, got: " + ni.getName());
        }
    }
}
