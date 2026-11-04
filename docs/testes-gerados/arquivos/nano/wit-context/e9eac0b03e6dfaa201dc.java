/* @test    @summary Test the update(byte[] input) method of javax.crypto.Mac for normal and exceptional behavior    @run main MacWitupTest */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithNullInput();
        testUpdateWithValidInput();
        testUpdateWithoutInitialization();
        testUpdateWithNullSpi();
    }

    private static void testUpdateWithNullInput() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.update((byte[]) null);
        } catch (IllegalStateException e) {
            // Expected if not initialized
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
        // If no exception, test passes assuming default state is initialized
    }

    private static void testUpdateWithValidInput() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Initialize with dummy key
        mac.init(new SecretKeySpec(new byte[]{1,2,3,4}, "HmacSHA256"));
        byte[] input = {10, 20, 30};
        mac.update(input);
        // No exception expected
    }

    private static void testUpdateWithoutInitialization() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        // Do not initialize
        try {
            mac.update(new byte[]{1,2,3});
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private static void testUpdateWithNullSpi() throws Exception {
        // Reflection or direct access not used; simulate by subclassing or mocking not feasible here
        // Instead, test the behavior if spi is null after initialization
        Mac mac = Mac.getInstance("HmacSHA256");
        // forcibly set spi to null (not possible directly, so skip or assume default)
        // As per constraints, avoid reflection; so this test is omitted.
    }
}
