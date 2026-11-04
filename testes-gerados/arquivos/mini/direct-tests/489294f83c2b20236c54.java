/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.append method behavior
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        testAppendNormal();
        testAppendSameInstanceThrows();
    }

    private static void testAppendNormal() {
        Norm2AllModes norm = new Norm2AllModes();
        StringBuilder first = new StringBuilder("Hello");
        CharSequence second = " World";
        StringBuilder result = norm.append(first, second);
        if (result != first) {
            throw new AssertionError("append should return the first StringBuilder instance");
        }
        String expected = "Hello World";
        if (!result.toString().equals(expected)) {
            throw new AssertionError("append result mismatch: expected '" + expected + "' but got '" + result + "'");
        }
    }

    private static void testAppendSameInstanceThrows() {
        Norm2AllModes norm = new Norm2AllModes();
        StringBuilder sb = new StringBuilder("test");
        try {
            norm.append(sb, sb);
            throw new AssertionError("Expected IllegalArgumentException when first and second are the same instance");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
