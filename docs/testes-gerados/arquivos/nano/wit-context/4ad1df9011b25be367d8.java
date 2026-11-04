/* @test    @summary Test for javax.crypto.Mac.update(byte) method to verify exception handling and normal behavior    @run main MacWitupTest */

import javax.crypto.Mac;
import java.security.Provider;
import java.security.Security;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithoutInitialization();
        testUpdateWithInitialization();
    }

    private static void testUpdateWithoutInitialization() {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            // Force spi to null to simulate uninitialized state
            // Note: Since spi is package-private, we cannot set it directly; instead, simulate by not calling init()
            // and forcibly setting initialized to false if possible, or by subclassing if needed.
            // Here, we assume that calling update without init() triggers the exception.
            mac.update((byte) 0x01);
            System.out.println("FAIL: Expected IllegalStateException not thrown in uninitialized state.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException in uninitialized state.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithInitialization() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Initialize the Mac with a key (using dummy key for test)
            byte[] keyBytes = {0x01, 0x02, 0x03, 0x04};
            javax.crypto.spec.SecretKeySpec keySpec = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(keySpec);
            // Now update should not throw
            mac.update((byte) 0x0A);
            System.out.println("PASS: update() executed successfully after initialization.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception after initialization: " + e);
        }
    }
}
