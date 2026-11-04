/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.normalizeSecondAndAppend method
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        testAppendDifferentObjects();
        testAppendSameObjectThrows();
    }

    private static void testAppendDifferentObjects() {
        Norm2AllModes.NoopNormalizer2 normalizer = new Norm2AllModes.NoopNormalizer2();
        StringBuilder first = new StringBuilder("Hello");
        CharSequence second = " World";
        StringBuilder result = normalizer.normalizeSecondAndAppend(first, second);
        if (result != first) {
            throw new AssertionError("Returned StringBuilder is not the first argument");
        }
        if (!"Hello World".contentEquals(result)) {
            throw new AssertionError("Content mismatch after append: " + result);
        }
    }

    private static void testAppendSameObjectThrows() {
        Norm2AllModes.NoopNormalizer2 normalizer = new Norm2AllModes.NoopNormalizer2();
        StringBuilder first = new StringBuilder("test");
        CharSequence second = first;
        try {
            normalizer.normalizeSecondAndAppend(first, second);
            throw new AssertionError("Expected IllegalArgumentException when first == second");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
