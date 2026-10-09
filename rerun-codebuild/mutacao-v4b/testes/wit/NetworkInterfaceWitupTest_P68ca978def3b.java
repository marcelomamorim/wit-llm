/* @test
   @summary Verifies behavior of NetworkInterface.getByName with null input.
   @run main NetworkInterfaceWitupTest_P68ca978def3b
 */
import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P68ca978def3b {
    public static void main(String[] args) throws Exception {
        testGetByNameNull();
    }

    public static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
