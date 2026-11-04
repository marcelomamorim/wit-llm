/*
 * @test
 * @summary Tests the doFinal() method of javax.crypto.Mac for expected behavior.
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testDoFinalWithoutInit();
        testDoFinalAfterInit();
        testDoFinalAfterReset();
    }

    private static void testDoFinalWithoutInit() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] result = mac.doFinal();
            System.err.println("FAIL: Expected IllegalStateException not thrown in doFinal() without init.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: doFinal() without init throws IllegalStateException as expected.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception in doFinal() without init: " + e);
        }
    }

    private static void testDoFinalAfterInit() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = {0x01, 0x02, 0x03, 0x04};
        Key key = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);
        byte[] result = mac.doFinal();
        if (result != null && result.length > 0) {
            System.out.println("PASS: doFinal() after init returns non-empty byte array.");
        } else {
            System.err.println("FAIL: doFinal() after init returned empty or null.");
        }
    }

    private static void testDoFinalAfterReset() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = {0x0A, 0x0B, 0x0C, 0x0D};
        Key key = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);
        byte[] firstResult = mac.doFinal();
        // Reset the Mac to simulate reuse
        mac.reset();
        try {
            byte[] secondResult = mac.doFinal();
            System.err.println("FAIL: Expected IllegalStateException after reset not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: doFinal() after reset throws IllegalStateException as expected.");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception after reset: " + e);
        }
    }
}
