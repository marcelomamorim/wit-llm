/*
 * @test
 * @summary Witup-derived checks for javax.crypto.Mac.doFinal(): IllegalStateException when not initialized and state reset after doFinal
 * @run main MacWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testDoFinalWithoutInitThrowsIllegalStateException();
        testDoFinalResetsStateAfterInit();
    }

    private static void testDoFinalWithoutInitThrowsIllegalStateException() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.doFinal();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException from doFinal() without init");
    }

    private static void testDoFinalResetsStateAfterInit() throws Exception {
        byte[] key = "key".getBytes(StandardCharsets.UTF_8);
        byte[] data = "data".getBytes(StandardCharsets.UTF_8);

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        mac.update(data);
        byte[] first = mac.doFinal();
        if (first.length != 32) {
            throw new RuntimeException("Unexpected HmacSHA256 length: " + first.length);
        }

        byte[] afterReset = mac.doFinal();

        Mac fresh = Mac.getInstance("HmacSHA256");
        fresh.init(new SecretKeySpec(key, "HmacSHA256"));
        byte[] expectedEmpty = fresh.doFinal();

        if (!Arrays.equals(afterReset, expectedEmpty)) {
            throw new RuntimeException("doFinal() did not reset internal state");
        }
    }
}
