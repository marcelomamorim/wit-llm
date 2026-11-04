/*
 * @test
 * @summary Verifies Mac.update(byte) throws IllegalStateException before initialization and matches update(byte[]) output after initialization
 * @run main MacWitupTest
 */

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateBeforeInitThrowsIllegalStateException();
        testUpdateByteMatchesUpdateArray();
    }

    private static void testUpdateBeforeInitThrowsIllegalStateException() throws NoSuchAlgorithmException {
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.update((byte) 'a');
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected: MAC not initialized
        }
    }

    private static void testUpdateByteMatchesUpdateArray()
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] keyBytes = new byte[] {1, 2, 3, 4, 5, 6, 7, 8};
        byte[] message = "abc".getBytes();

        Mac byByte = Mac.getInstance("HmacSHA256");
        byByte.init(new SecretKeySpec(keyBytes, "HmacSHA256"));
        for (byte b : message) {
            byByte.update(b);
        }
        byte[] resultByByte = byByte.doFinal();

        Mac byArray = Mac.getInstance("HmacSHA256");
        byArray.init(new SecretKeySpec(keyBytes, "HmacSHA256"));
        byte[] resultByArray = byArray.doFinal(message);

        if (!Arrays.equals(resultByByte, resultByArray)) {
            throw new AssertionError("update(byte) result differs from update(byte[]) result");
        }
        if (resultByByte.length != byByte.getMacLength()) {
            throw new AssertionError("Unexpected MAC length");
        }
    }
}
