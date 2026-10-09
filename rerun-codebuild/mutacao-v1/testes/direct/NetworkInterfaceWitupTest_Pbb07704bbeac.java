/* @test    @summary Test getByName with valid and null input    @run main NetworkInterfaceWitupTest_Pbb07704bbeac */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_Pbb07704bbeac {
    public static void main(String[] args) throws Exception {
        testGetByNameValid();
        testGetByNameNull();
    }

    static void testGetByNameValid() {
        try {
            NetworkInterface ni = NetworkInterface.getByName("lo");
            if (ni == null) {
                throw new AssertionError("Expected non-null NetworkInterface for 'lo'");
            }
            if (!"lo".equals(ni.getName())) {
                throw new AssertionError("Expected name 'lo', but got: " + ni.getName());
            }
        } catch (SocketException e) {
            throw new RuntimeException("SocketException thrown during getByName with 'lo'", e);
        }
    }

    static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for null input", e);
        }
    }
}
