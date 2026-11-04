/*
 * @test
 * @summary Test StringJoiner.merge(StringJoiner) for null argument and basic merge behavior
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;
import java.util.Objects;

public class StringJoinerWitupTest {
    public static void main(String[] args) {
        testMergeNullArgument();
        testMergeWithEmptyOtherElts();
        testMergeWithNonEmptyOtherElts();
    }

    private static void testMergeNullArgument() {
        StringJoiner sj = new StringJoiner(",");
        try {
            sj.merge(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testMergeWithEmptyOtherElts() {
        StringJoiner sj1 = new StringJoiner(",", "[", "]");
        StringJoiner sj2 = new StringJoiner(",", "[", "]");
        // sj2.elts is null initially (no elements added)
        StringJoiner result = sj1.merge(sj2);
        if (result != sj1) {
            throw new RuntimeException("merge should return this when other.elts is null");
        }
        // toString should be prefix + suffix since no elements added
        if (!result.toString().equals("[]")) {
            throw new RuntimeException("Unexpected toString result: " + result.toString());
        }
    }

    private static void testMergeWithNonEmptyOtherElts() {
        StringJoiner sj1 = new StringJoiner(":", "[", "]");
        sj1.add("A");
        StringJoiner sj2 = new StringJoiner(":", "[", "]");
        sj2.add("B");
        sj2.add("C");
        StringJoiner merged = sj1.merge(sj2);
        // merged should be sj1 with first element of sj2 added
        String expected = "[A:B]";
        String actual = merged.toString();
        if (!expected.equals(actual)) {
            throw new RuntimeException("merge result mismatch. Expected: " + expected + ", got: " + actual);
        }
    }
}
