/*
 * @test
 * @summary Witup-style tests for SecretKeySpec(byte[], int, int, String) argument checks and key copy semantics
 * @run main SecretKeySpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("FAILED: " + msg);
        }
    }

    static void expectIAE(byte[] key, int off, int len, String alg, String msg) {
        try {
            new SecretKeySpec(key, off, len, alg);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("FAILED (no IllegalArgumentException): " + msg);
    }

    static void expectAIOOBE(byte[] key, int off, int len, String alg, String msg) {
        try {
            new SecretKeySpec(key, off, len, alg);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("FAILED (no ArrayIndexOutOfBoundsException): " + msg);
    }

    public static void main(String[] args) {
        byte[] data = {1, 2, 3, 4, 5, 6, 7, 8};

        SecretKeySpec k = new SecretKeySpec(data, 2, 3, "AES");
        check(Arrays.equals(new byte[]{3, 4, 5}, k.getEncoded()), "sub-range copy");
        check("AES".equals(k.getAlgorithm()), "algorithm preserved");

        byte[] src = {10, 20, 30, 40};
        SecretKeySpec copy = new SecretKeySpec(src, 0, 4, "HmacSHA256");
        check(Arrays.equals(src, copy.getEncoded()), "full-length copy");
        src[1] = 99;
        check(Arrays.equals(new byte[]{10, 20, 30, 40}, copy.getEncoded()),
              "key must not reflect later modification of source array");

        SecretKeySpec tail = new SecretKeySpec(data, 5, 3, "AES");
        check(Arrays.equals(new byte[]{6, 7, 8}, tail.getEncoded()), "tail range");

        expectIAE(null, 0, 1, "AES", "null key");
        expectIAE(data, 0, 1, null, "null algorithm");
        expectIAE(new byte[0], 0, 0, "AES", "empty key");
        expectIAE(data, 6, 3, "AES", "offset+len exceeds key length");
        expectIAE(data, 9, 0, "AES", "offset beyond key length");
        expectAIOOBE(data, 0, -1, "AES", "negative len");
    }
}
