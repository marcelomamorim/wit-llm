/*
 * @test
 * @summary Checks ChoiceFormat.setChoices argument length validation and defensive copies
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testMismatchedLengthsThrowIllegalArgumentException();
        testMismatchedLengthsPreservePreviousChoices();
        testMatchingLengthsAppliesChoices();
        testArraysAreCopied();
    }

    static void testMismatchedLengthsThrowIllegalArgumentException() {
        ChoiceFormat cf = new ChoiceFormat("0#x|1#y");
        boolean thrown = false;
        try {
            cf.setChoices(new double[] {0, 1}, new String[] {"a"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for formats shorter than limits");
        }

        thrown = false;
        try {
            cf.setChoices(new double[] {0}, new String[] {"a", "b"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for formats longer than limits");
        }
    }

    static void testMismatchedLengthsPreservePreviousChoices() {
        ChoiceFormat cf = new ChoiceFormat("0#x|1#y");
        try {
            cf.setChoices(new double[] {0, 1, 2}, new String[] {"a"});
        } catch (IllegalArgumentException e) {
            // expected
        }
        check(cf.format(0.5), "x", "choice after rejected setChoices");
        check(cf.format(1.5), "y", "choice after rejected setChoices");
    }

    static void testMatchingLengthsAppliesChoices() {
        ChoiceFormat cf = new ChoiceFormat("0#x|1#y");
        cf.setChoices(new double[] {0, 1, 2}, new String[] {"a", "b", "c"});
        check(cf.format(0.5), "a", "first interval");
        check(cf.format(1.0), "b", "second interval lower bound");
        check(cf.format(1.5), "b", "second interval");
        check(cf.format(2.5), "c", "third interval");
    }

    static void testArraysAreCopied() {
        double[] limits = {0, 1, 2};
        String[] formats = {"a", "b", "c"};
        ChoiceFormat cf = new ChoiceFormat("0#x|1#y");
        cf.setChoices(limits, formats);
        limits[1] = 10;
        formats[0] = "zz";
        check(cf.format(0.5), "a", "input formats array mutated after setChoices");
        check(cf.format(1.5), "b", "input limits array mutated after setChoices");
    }

    static void check(String actual, String expected, String what) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(what + ": expected " + expected + " but was " + actual);
        }
    }
}
