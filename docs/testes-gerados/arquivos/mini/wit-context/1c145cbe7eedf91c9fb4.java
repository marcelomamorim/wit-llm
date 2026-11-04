/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase(String, Mode, int) constructor for null input and normal usage
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullString();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNullString() {
        try {
            new NormalizerBase(null, Mode.NFC, 0);
            throw new RuntimeException("Expected IllegalArgumentException for null string input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testConstructorWithValidString() {
        String testStr = "abc";
        NormalizerBase nb = new NormalizerBase(testStr, Mode.NFD, 0);
        if (nb == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
        // No direct public getter to verify internal state, so just ensure no exception and instance created
    }
}
