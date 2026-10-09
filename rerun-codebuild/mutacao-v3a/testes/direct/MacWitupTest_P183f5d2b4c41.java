/* @test    @summary Test doFinal method of javax.crypto.Mac    @run main MacWitupTest_P183f5d2b4c41 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;

public class MacWitupTest_P183f5d2b4c41 {

    public static void main(String[] args) throws Exception {
        testDoFinalWithInitializedMac();
        testDoFinalWithoutInitialization();
    }

    private static void testDoFinalWithInitializedMac() throws Exception {
        String algorithm = "HmacSHA256";
        String keyString = "testkey";
        byte[] keyBytes = keyString.getBytes("UTF-8");
        Key key = new SecretKeySpec(keyBytes, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(key);
        byte[] result = mac.doFinal();
        byte[] expected = mac.spi.engineDoFinal();
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("doFinal output does not match expected");
        }
        System.out.println("testDoFinalWithInitializedMac passed.");
    }

    private static void testDoFinalWithoutInitialization() {
        try {
            String algorithm = "HmacSHA256";
            Mac mac = Mac.getInstance(algorithm);
            // Not initializing
            mac.doFinal();
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testDoFinalWithoutInitialization passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
