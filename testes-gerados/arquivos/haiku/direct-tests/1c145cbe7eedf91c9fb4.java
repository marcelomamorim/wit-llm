/*
 * @test
 * @summary Verifies that the NormalizerBase(String, Mode, int) constructor rejects a null Mode with NullPointerException
 * @modules java.base/sun.text.normalizer
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testNullModeWithOptionZero();
        testNullModeWithOptionOne();
        testNullModeWithEmptyString();
    }

    static void testNullModeWithOptionZero() {
        boolean thrown = false;
        try {
            new NormalizerBase("abc", null, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mode, opt=0");
        }
    }

    static void testNullModeWithOptionOne() {
        boolean thrown = false;
        try {
            new NormalizerBase("abc", null, 1);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mode, opt=1");
        }
    }

    static void testNullModeWithEmptyString() {
        boolean thrown = false;
        try {
            new NormalizerBase("", null, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mode with empty string");
        }
    }
}
