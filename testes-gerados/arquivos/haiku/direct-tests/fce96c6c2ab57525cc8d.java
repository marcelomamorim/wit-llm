/*
 * @test
 * @summary Witup-style behavioral checks for the public StringJoiner(CharSequence delimiter) constructor
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testEmptyJoinerReturnsEmptyString();
        testEmptyJoinerLengthIsZero();
        testMultipleElementsSeparatedByDelimiter();
        testSingleElementHasNoDelimiter();
        testEmptyDelimiterConcatenatesElements();
        testCharSequenceDelimiterIsAccepted();
        testSetEmptyValueOverridesEmptyResult();
        testLengthAfterAdds();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyJoinerReturnsEmptyString() {
        StringJoiner sj = new StringJoiner(",");
        check("".equals(sj.toString()), "expected empty string but was: " + sj.toString());
    }

    static void testEmptyJoinerLengthIsZero() {
        StringJoiner sj = new StringJoiner(",");
        check(sj.length() == 0, "expected length 0 but was: " + sj.length());
    }

    static void testMultipleElementsSeparatedByDelimiter() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("a").add("b").add("c");
        check("a,b,c".equals(sj.toString()), "expected a,b,c but was: " + sj.toString());
    }

    static void testSingleElementHasNoDelimiter() {
        StringJoiner sj = new StringJoiner(", ");
        sj.add("x");
        check("x".equals(sj.toString()), "expected x but was: " + sj.toString());
    }

    static void testEmptyDelimiterConcatenatesElements() {
        StringJoiner sj = new StringJoiner("");
        sj.add("ab").add("cd");
        check("abcd".equals(sj.toString()), "expected abcd but was: " + sj.toString());
    }

    static void testCharSequenceDelimiterIsAccepted() {
        StringBuilder delim = new StringBuilder(";");
        StringJoiner sj = new StringJoiner(delim);
        sj.add("1").add("2");
        check("1;2".equals(sj.toString()), "expected 1;2 but was: " + sj.toString());
    }

    static void testSetEmptyValueOverridesEmptyResult() {
        StringJoiner sj = new StringJoiner(",");
        sj.setEmptyValue("EMPTY");
        check("EMPTY".equals(sj.toString()), "expected EMPTY but was: " + sj.toString());
    }

    static void testLengthAfterAdds() {
        StringJoiner sj = new StringJoiner(",");
        sj.add("ab").add("c");
        check(sj.length() == 4, "expected length 4 but was: " + sj.length());
    }
}
