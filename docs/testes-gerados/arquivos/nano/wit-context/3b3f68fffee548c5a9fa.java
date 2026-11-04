/* @test    @summary Test for normalizeSecondAndAppend method in Norm2AllModes.
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;
import java.lang.StringBuilder;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testNormalizeSecondAndAppend_NormalCase();
        testNormalizeSecondAndAppend_SameObjectThrows();
    }

    private static void testNormalizeSecondAndAppend_NormalCase() {
        StringBuilder sb1 = new StringBuilder("Hello ");
        StringBuilder result = Norm2AllModes.normalizeSecondAndAppend(sb1, "World");
        if (!result.toString().equals("Hello World")) {
            throw new AssertionError("Expected 'Hello World', but got: " + result);
        }
        if (result != sb1) {
            throw new AssertionError("Result should be the same StringBuilder instance as first argument");
        }
    }

    private static void testNormalizeSecondAndAppend_SameObjectThrows() {
        StringBuilder sb = new StringBuilder("Test");
        try {
            Norm2AllModes.normalizeSecondAndAppend(sb, sb);
            throw new AssertionError("Expected IllegalArgumentException when first and second are the same object");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
