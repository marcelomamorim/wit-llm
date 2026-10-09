/* @test    @summary Test the doFinal() method of javax.crypto.Mac for correct behavior and exception handling    @run main MacWitupTest_Pb6f68f7f3641 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class MacWitupTest_Pb6f68f7f3641 {

    public static void main(String[] args) {
        testDoFinalWithoutInit();
        testDoFinalAfterInit();
        testDoFinalAfterReset();
    }

    private static void testDoFinalWithoutInit() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] result = mac.doFinal();
            throw new AssertionError("Expected IllegalStateException when doFinal() called without init, but got result: " + java.util.Arrays.toString(result));
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDoFinalWithoutInit: " + e, e);
        }
    }

    private static void testDoFinalAfterInit() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = {1, 2, 3, 4, 5};
            Key key = new SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(key);
            byte[] result = mac.doFinal();
            if (result == null || result.length == 0) {
                throw new AssertionError("doFinal() returned null or empty array after init");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDoFinalAfterInit: " + e, e);
        }
    }

    private static void testDoFinalAfterReset() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] keyBytes = {10, 20, 30, 40, 50};
            Key key = new SecretKeySpec(keyBytes, "HmacSHA256");
            mac.init(key);
            byte[] firstResult = mac.doFinal();
            // After doFinal, the Mac should be reset and ready for new init
            byte[] secondResult = mac.doFinal();
            throw new AssertionError("Expected IllegalStateException after doFinal() without re-init, but got result: " + java.util.Arrays.toString(secondResult));
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testDoFinalAfterReset: " + e, e);
        }
    }
}
