/* @test
   @summary Tests NetworkInterface.getByName with valid and null input.
   @run main NetworkInterfaceWitupTest_P68ca978def3b
 */
import java.net.NetworkInterface;
import java.net.SocketException;
public class NetworkInterfaceWitupTest_P68ca978def3b {
    public static void main(String[] args) throws Exception {
        testGetByNameValid();
        testGetByNameNull();
    }
    private static void testGetByNameValid() throws Exception {
        String name = "lo";
        try {
            NetworkInterface ni = NetworkInterface.getByName(name);
            if (ni == null) {
                throw new AssertionError("Expected non-null NetworkInterface for name: " + name);
            }
        } catch (SocketException e) {
            // In some environments, "lo" may not exist; skip test if so.
        }
    }
    private static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
