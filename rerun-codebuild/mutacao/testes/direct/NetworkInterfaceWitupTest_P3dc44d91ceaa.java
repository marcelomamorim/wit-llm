/* @test    @summary Test getByName with valid and null input    @run main NetworkInterfaceWitupTest_P3dc44d91ceaa */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P3dc44d91ceaa {

    public static void main(String[] args) {
        testGetByNameValid();
        testGetByNameNull();
    }

    static void testGetByNameValid() {
        try {
            NetworkInterface ni = NetworkInterface.getByName("lo");
            if (ni == null) {
                throw new AssertionError("Expected non-null NetworkInterface for 'lo'");
            }
            String name = ni.getName();
            if (name == null || !name.equals("lo")) {
                throw new AssertionError("Expected name 'lo', but got: " + name);
            }
        } catch (SocketException e) {
            throw new AssertionError("SocketException thrown during getByName with 'lo': " + e);
        }
    }

    static void testGetByNameNull() {
        boolean exceptionThrown = false;
        try {
            NetworkInterface.getByName(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        } catch (SocketException e) {
            throw new AssertionError("Unexpected SocketException for null input: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown for null input");
        }
    }
}
