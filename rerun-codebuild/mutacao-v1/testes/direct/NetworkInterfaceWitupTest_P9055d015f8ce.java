/* @test    @summary Test getByIndex with valid and invalid indices    @run main NetworkInterfaceWitupTest_P9055d015f8ce */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P9055d015f8ce {
    public static void main(String[] args) {
        testGetByIndexValid();
        testGetByIndexNegative();
    }

    static void testGetByIndexValid() {
        try {
            int testIndex = 1;
            NetworkInterface ni = NetworkInterface.getByIndex(testIndex);
            if (ni == null) {
                throw new AssertionError("Expected non-null NetworkInterface for index " + testIndex);
            }
            if (ni.getClass() == null) {
                throw new AssertionError("NetworkInterface.getClass() returned null");
            }
        } catch (SocketException e) {
            throw new RuntimeException("SocketException thrown during valid index test", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during valid index test", e);
        }
    }

    static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (SocketException e) {
            throw new RuntimeException("SocketException thrown during negative index test", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during negative index test", e);
        }
    }
}
