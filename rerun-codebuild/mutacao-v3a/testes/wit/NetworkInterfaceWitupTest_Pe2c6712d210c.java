/* @test    @summary Test for NetworkInterface.getByIndex(int) method to verify exception handling for invalid input.    @run main NetworkInterfaceWitupTest_Pe2c6712d210c */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_Pe2c6712d210c {

    public static void main(String[] args) {
        testGetByIndexNegative();
    }

    static void testGetByIndexNegative() {
        int invalidIndex = -1;
        boolean exceptionThrown = false;
        try {
            NetworkInterface.getByIndex(invalidIndex);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            String message = e.getMessage();
            if (message == null || !message.contains("Interface index can't be negative")) {
                throw new AssertionError("Unexpected exception message: " + message);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException not thrown for index: " + invalidIndex);
        }
    }
}
