/* @test
   @summary Verifies that doFinal() throws IllegalStateException if MAC is not initialized.
   @run main MacWitupTest_P2fb30afa0e8b
 */
import java.util.*;
import java.security.*;
import javax.crypto.*;

public class MacWitupTest_P2fb30afa0e8b {
    public static void main(String[] args) throws Exception {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("HmacSHA256 not available", e);
        }
        // Test doFinal() without initialization
        try {
            byte[] result = mac.doFinal();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
        // Initialize MAC with a key
        byte[] keyBytes = "testkey".getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);
        // Now doFinal() should succeed
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new AssertionError("doFinal() returned null or empty array");
        }
    }
}
