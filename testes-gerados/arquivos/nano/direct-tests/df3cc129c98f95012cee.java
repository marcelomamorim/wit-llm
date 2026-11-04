/* @test    @summary Test StringJoiner.merge method for correct concatenation behavior    @run main StringJoinerWitupTest */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testMergeWithEmptyJoiner();
        testMergeWithNonEmptyJoiner();
        testMergeWithNull();
    }

    static void testMergeWithEmptyJoiner() {
        StringJoiner sj1 = new StringJoiner(",");
        StringJoiner sj2 = new StringJoiner(",");
        StringJoiner result = sj1.merge(sj2);
        String expected = "";
        String actual = result.toString();
        if (!actual.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "', but got: '" + actual + "'");
        }
    }

    static void testMergeWithNonEmptyJoiner() {
        StringJoiner sj1 = new StringJoiner(",");
        sj1.add("A");
        sj1.add("B");
        StringJoiner sj2 = new StringJoiner(",");
        sj2.add("C");
        sj2.add("D");
        StringJoiner result = sj1.merge(sj2);
        String expected = "A,B,C";
        String actual = result.toString();
        if (!actual.equals(expected)) {
            throw new AssertionError("Expected: '" + expected + "', but got: '" + actual + "'");
        }
    }

    static void testMergeWithNull() {
        StringJoiner sj1 = new StringJoiner(",");
        try {
            sj1.merge(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
