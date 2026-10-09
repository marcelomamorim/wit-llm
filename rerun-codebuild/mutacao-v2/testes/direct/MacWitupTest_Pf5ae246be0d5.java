/*
 * @test
 * @summary Test javax.crypto.Mac doFinal() method behavior
 * @run main MacWitupTest_Pf5ae246be0d5
 */

import javax.crypto.Mac;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

public class MacWitupTest_Pf5ae246be0d5 {

    public static void main(String[] args) throws Exception {
        testDoFinalWithoutInitThrows();
        testDoFinalReturnsNonNullAndResets();
    }

    private static void testDoFinalWithoutInitThrows() {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            // Do not init the Mac
            mac.doFinal();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // Expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e);
        }
    }

    private static void testDoFinalReturnsNonNullAndResets() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(key);

        mac.update(new byte[] { 10, 20, 30 });
        byte[] result1 = mac.doFinal();
        if (result1 == null) {
            throw new RuntimeException("doFinal() returned null");
        }

        // After doFinal(), Mac should be reset, so updating with same data and calling doFinal again
        mac.update(new byte[] { 10, 20, 30 });
        byte[] result2 = mac.doFinal();
        if (result2 == null) {
            throw new RuntimeException("doFinal() returned null on second call");
        }

        // The two results should be equal because the Mac was reset
        if (result1.length != result2.length) {
            throw new RuntimeException("doFinal() results length differ after reset");
        }
        for (int i = 0; i < result1.length; i++) {
            if (result1[i] != result2[i]) {
                throw new RuntimeException("doFinal() results differ after reset");
            }
        }
    }
}
