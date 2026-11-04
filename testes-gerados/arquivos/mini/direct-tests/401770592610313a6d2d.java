/*
 * @test
 * @summary Test IvParameterSpec(byte[] iv) constructor behavior
 * @run main IvParameterSpecWitupTest
 */

import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;

public class IvParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidIv();
        testNullIv();
        testIvImmutability();
    }

    private static void testValidIv() {
        byte[] iv = {1, 2, 3, 4};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] returnedIv = spec.getIV();
        if (!Arrays.equals(iv, returnedIv)) {
            throw new RuntimeException("Returned IV does not match input IV");
        }
    }

    private static void testNullIv() {
        try {
            new IvParameterSpec(null);
            throw new RuntimeException("Expected IllegalArgumentException for null IV");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testIvImmutability() {
        byte[] iv = {5, 6, 7, 8};
        IvParameterSpec spec = new IvParameterSpec(iv);
        byte[] returnedIv = spec.getIV();
        // Modify returned array
        returnedIv[0] = 99;
        byte[] returnedIv2 = spec.getIV();
        if (returnedIv2[0] == 99) {
            throw new RuntimeException("IV array was modified externally");
        }
    }
}
