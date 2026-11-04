/*
 * @test
 * @summary Test StringJoiner.merge method behavior
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;
import java.util.Objects;

public class StringJoinerWitupTest {
    public static void main(String[] args) {
        testMergeWithNullThrowsNPE();
        testMergeWithEmptyOtherReturnsThis();
        testMergeWithNonEmptyOtherAddsFirstElement();
    }

    private static void testMergeWithNullThrowsNPE() {
        StringJoiner sj = new StringJoiner(",");
        try {
            sj.merge(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMergeWithEmptyOtherReturnsThis() {
        StringJoiner sj1 = new StringJoiner(",");
        StringJoiner sj2 = new StringJoiner(",");
        // sj2 has no elements added, so elts == null
        StringJoiner result = sj1.merge(sj2);
        if (result != sj1) {
            throw new AssertionError("merge should return this when other.elts is null");
        }
    }

    private static void testMergeWithNonEmptyOtherAddsFirstElement() {
        StringJoiner sj1 = new StringJoiner(",");
        StringJoiner sj2 = new StringJoiner(",");
        sj2.add("foo");
        StringJoiner result = sj1.merge(sj2);
        // result should be sj1 itself
        if (result != sj1) {
            throw new AssertionError("merge should return this");
        }
        String expected = "foo";
        String actual = sj1.toString();
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected '" + expected + "' but got '" + actual + "'");
        }
    }
}
