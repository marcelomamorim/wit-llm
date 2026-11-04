/*
 * @test
 * @summary Witup generated checks for ChoiceFormat(double[] limits, String[] formats) length validation and basic formatting
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testFormatsShorterThanLimitsRejected();
        testFormatsLongerThanLimitsRejected();
        testEmptyArraysAccepted();
        testBasicFormatting();
    }

    static void testFormatsShorterThanLimitsRejected() {
        boolean thrown = false;
        try {
            new ChoiceFormat(new double[]{1, 2, 3}, new String[]{"a", "b"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for formats shorter than limits");
        }
    }

    static void testFormatsLongerThanLimitsRejected() {
        boolean thrown = false;
        try {
            new ChoiceFormat(new double[]{1, 2}, new String[]{"a", "b", "c"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for formats longer than limits");
        }
    }

    static void testEmptyArraysAccepted() {
        ChoiceFormat cf = new ChoiceFormat(new double[0], new String[0]);
        if (cf == null) {
            throw new RuntimeException("Constructor returned null for empty arrays");
        }
    }

    static void testBasicFormatting() {
        ChoiceFormat cf = new ChoiceFormat(new double[]{1, 2, 3}, new String[]{"a", "b", "c"});
        check("a", cf.format(0.0));
        check("b", cf.format(1.0));
        check("b", cf.format(1.5));
        check("c", cf.format(2.5));
        check("c", cf.format(10.0));
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
