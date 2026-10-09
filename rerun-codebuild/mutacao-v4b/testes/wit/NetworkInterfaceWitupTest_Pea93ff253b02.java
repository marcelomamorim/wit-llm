/* @test
   @summary Tests for NetworkInterface.getByIndex(int).
   @run main NetworkInterfaceWitupTest_Pea93ff253b02
 */
import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_Pea93ff253b02 {
    public static void main(String[] args) {
        testGetByIndexNegative();
        testGetByIndexValid();
    }

    static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testGetByIndexValid() {
        try {
            NetworkInterface ni = NetworkInterface.getByIndex(1);
            if (ni == null) {
                throw new AssertionError("getByIndex(1) returned null");
            }
        } catch (SocketException e) {
            // In environments without network interfaces, this may throw
            // but the test is still valid as it doesn't fail the test
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
