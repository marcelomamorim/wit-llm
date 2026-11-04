/*
 * @test
 * @summary Witup-generated checks for NormalizerBase(String, Mode, int) argument validation
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testNullStringRejected();
        testNullModeRejected();
    }

    static void testNullStringRejected() {
        try {
            new NormalizerBase((String) null, null, 0);
            throw new RuntimeException("Expected exception for null str was not thrown");
        } catch (IllegalArgumentException | NullPointerException expected) {
            // Expath: null str is rejected; the exact type comes from the callee chain,
            // so both candidate types are accepted.
        }
    }

    static void testNullModeRejected() {
        try {
            new NormalizerBase("abc", null, 0);
            throw new RuntimeException("Expected NullPointerException for null mode was not thrown");
        } catch (NullPointerException expected) {
            // mode.getNormalizer2(opt) is invoked on a null Mode.
        }
    }
}
