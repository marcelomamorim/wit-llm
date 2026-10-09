/* @test    @summary Test for NetworkInterface.getByIndex(int) method, focusing on exception path for negative index    @run main NetworkInterfaceWitupTest_P9055d015f8ce */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P9055d015f8ce {
    public static void main(String[] args) throws Exception {
        testGetByIndexNegative();
        testGetByIndexValid();
    }

    private static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Interface index can't be negative")) {
                throw new AssertionError("Unexpected message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }

    private static void testGetByIndexValid() throws SocketException {
        // Use a valid index, e.g., 0, assuming at least one network interface exists
        NetworkInterface ni = NetworkInterface.getByIndex(0);
        if (ni == null) {
            throw new AssertionError("getByIndex(0) returned null");
        }
        String name = ni.getName();
        if (name == null) {
            throw new AssertionError("NetworkInterface.getName() returned null");
        }
    }
}
