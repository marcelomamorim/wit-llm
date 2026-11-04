/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.NoopNormalizer2.normalizeSecondAndAppend behavior
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        testNormalizeSecondAndAppendDifferentObjects();
        testNormalizeSecondAndAppendSameObjectsThrows();
    }

    private static void testNormalizeSecondAndAppendDifferentObjects() {
        Norm2AllModes.NoopNormalizer2 normalizer = new Norm2AllModes.NoopNormalizer2();
        StringBuilder first = new StringBuilder("Hello");
        CharSequence second = " World";
        StringBuilder result = normalizer.normalizeSecondAndAppend(first, second);
        if (result != first) {
            throw new AssertionError("Returned StringBuilder is not the first argument");
        }
        if (!result.toString().equals("Hello World")) {
            throw new AssertionError("Content after append is incorrect: " + result);
        }
    }

    private static void testNormalizeSecondAndAppendSameObjectsThrows() {
        Norm2AllModes.NoopNormalizer2 normalizer = new Norm2AllModes.NoopNormalizer2();
        StringBuilder first = new StringBuilder("test");
        try {
            normalizer.normalizeSecondAndAppend(first, first);
            throw new AssertionError("Expected IllegalArgumentException when first == second");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
