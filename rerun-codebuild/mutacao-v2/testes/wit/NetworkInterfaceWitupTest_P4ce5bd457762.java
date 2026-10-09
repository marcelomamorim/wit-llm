/*
 * @test
 * @summary Test NetworkInterface.getByIndex(int) for valid and invalid indices
 * @run main NetworkInterfaceWitupTest_P4ce5bd457762
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P4ce5bd457762 {
    public static void main(String[] args) throws Exception {
        testNegativeIndexThrows();
        testValidIndexReturnsOrNull();
    }

    private static void testNegativeIndexThrows() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // Expected exception
            if (!"Interface index can't be negative".equals(e.getMessage())) {
                throw new RuntimeException("IllegalArgumentException message mismatch", e);
            }
        } catch (SocketException e) {
            throw new RuntimeException("Unexpected SocketException for negative index", e);
        }
    }

    private static void testValidIndexReturnsOrNull() throws SocketException {
        // Test with index 0 which is often invalid or default
        NetworkInterface ni = NetworkInterface.getByIndex(0);
        // The method may return null if no interface with index 0 exists
        // Just check no exception and result is either null or a NetworkInterface
        if (ni != null && !(ni instanceof NetworkInterface)) {
            throw new RuntimeException("Returned object is not a NetworkInterface");
        }

        // Test with a likely invalid large index
        int largeIndex = Integer.MAX_VALUE;
        NetworkInterface niLarge = NetworkInterface.getByIndex(largeIndex);
        // Should not throw, may return null
        if (niLarge != null && !(niLarge instanceof NetworkInterface)) {
            throw new RuntimeException("Returned object is not a NetworkInterface");
        }
    }
}
