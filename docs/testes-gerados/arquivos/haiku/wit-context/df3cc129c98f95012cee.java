/*
 * @test
 * @summary Witup-derived tests for StringJoiner.merge(StringJoiner)
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testMergeNullThrowsNPE();
        testMergeEmptyOtherReturnsThis();
        testMergeEmptyOtherLeavesValueUnchanged();
        testMergeSingleElementOther();
        testMergeMultipleElementsUsesOtherDelimiter();
        testMergeIgnoresOtherPrefixAndSuffix();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMergeNullThrowsNPE() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("x");
        try {
            sj.merge(null);
            throw new RuntimeException("merge(null) did not throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check(sj.toString().equals("x"), "receiver changed after failed merge(null)");
    }

    static void testMergeEmptyOtherReturnsThis() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("x");
        StringJoiner other = new StringJoiner("|");
        StringJoiner result = sj.merge(other);
        check(result == sj, "merge with empty other must return this");
    }

    static void testMergeEmptyOtherLeavesValueUnchanged() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        sj.add("x");
        sj.merge(new StringJoiner("|"));
        check(sj.toString().equals("[x]"), "empty other changed value: " + sj);

        StringJoiner empty = new StringJoiner(",", "{", "}");
        empty.merge(new StringJoiner("|"));
        check(empty.toString().equals("{}"), "empty merge changed empty value: " + empty);
    }

    static void testMergeSingleElementOther() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("x");
        StringJoiner other = new StringJoiner("|");
        other.add("a");
        sj.merge(other);
        check(sj.toString().equals("x,a"), "expected x,a but was " + sj);
    }

    static void testMergeMultipleElementsUsesOtherDelimiter() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("x");
        StringJoiner other = new StringJoiner("|");
        other.add("a").add("b");
        sj.merge(other);
        check(sj.toString().equals("x,a|b"), "expected x,a|b but was " + sj);
    }

    static void testMergeIgnoresOtherPrefixAndSuffix() {
        StringJoiner sj = new StringJoiner(",");
        StringJoiner other = new StringJoiner("|", "[", "]");
        other.add("a").add("b");
        sj.merge(other);
        check(sj.toString().equals("a|b"), "expected a|b but was " + sj);
    }
}
