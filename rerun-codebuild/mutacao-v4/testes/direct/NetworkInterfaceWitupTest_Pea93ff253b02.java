/* @test
   @summary Tests getByIndex with valid and invalid indices.
   @run main NetworkInterfaceWitupTest_Pea93ff253b02
 */
import java.net.NetworkInterface;
import java.net.SocketException;
public class NetworkInterfaceWitupTest_Pea93ff253b02 {
    public static void main(String[] args) throws Exception {
        testGetByIndexNegative();
        testGetByIndexValid();
    }
    private static void testGetByIndexNegative() {
        try {
            NetworkInterface.getByIndex(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    private static void testGetByIndexValid() throws SocketException {
        int testIndex = 1;
        NetworkInterface ni = NetworkInterface.getByIndex(testIndex);
        if (ni == null) {
            throw new AssertionError("getByIndex returned null for index " + testIndex);
        }
        if (ni.getName() == null) {
            // acceptable if name is null, as implementation may vary
        }
    }
}
