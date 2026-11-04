/*
 * @test
 * @summary Smoke and exception-contract tests for KeyUsageExtension(BitArray) constructor
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main KeyUsageExtensionWitupTest
 */

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.x509.KeyUsageExtension;

public class KeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBitArrayThrowsNullPointerException();
        testValidBitArrayConstructsWithoutException();
        testEmptyBitArrayConstructsWithoutException();
        testSourceBitArrayIsNotModified();
    }

    private static void testNullBitArrayThrowsNullPointerException() throws IOException {
        boolean thrown = false;
        try {
            new KeyUsageExtension((BitArray) null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null BitArray");
        }
    }

    private static void testValidBitArrayConstructsWithoutException() throws IOException {
        BitArray bits = new BitArray(new boolean[] {true, false, true});
        KeyUsageExtension ext = new KeyUsageExtension(bits);
        if (ext == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    private static void testEmptyBitArrayConstructsWithoutException() throws IOException {
        BitArray bits = new BitArray(new boolean[0]);
        KeyUsageExtension ext = new KeyUsageExtension(bits);
        if (ext == null) {
            throw new AssertionError("Constructor returned null for empty BitArray");
        }
    }

    private static void testSourceBitArrayIsNotModified() throws IOException {
        boolean[] expected = {true, true, false, true};
        BitArray bits = new BitArray(expected);
        new KeyUsageExtension(bits);
        boolean[] actual = bits.toBooleanArray();
        if (actual.length != expected.length) {
            throw new AssertionError("BitArray length changed by constructor");
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("BitArray bit " + i + " changed by constructor");
            }
        }
    }
}
