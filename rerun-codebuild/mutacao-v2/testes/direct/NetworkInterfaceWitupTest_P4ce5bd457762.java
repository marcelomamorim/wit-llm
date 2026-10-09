/*
 * @test
 * @summary Test NetworkInterface.getByIndex(int) for valid and invalid indices
 * @run main NetworkInterfaceWitupTest_P4ce5bd457762
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P4ce5bd457762 {
    public static void main(String[] args) throws Exception {
        testNegativeIndex();
        testValidIndex();
    }

    private static void testNegativeIndex() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (SocketException e) {
            throw new RuntimeException("Unexpected SocketException for negative index", e);
        }
    }

    private static void testValidIndex() throws Exception {
        // The default interface index is >= 0 or 0 if none
        // We test index 0 which is valid and should not throw IllegalArgumentException
        // It may return null if no interface with index 0 exists
        try {
            NetworkInterface ni = NetworkInterface.getByIndex(0);
            // ni may be null if no interface with index 0 exists
            // Just check no exception thrown and return type is NetworkInterface or null
            if (ni != null && !(ni instanceof NetworkInterface)) {
                throw new RuntimeException("Returned object is not a NetworkInterface");
            }
        } catch (SocketException e) {
            throw new RuntimeException("Unexpected SocketException for index 0", e);
        }
    }
}
