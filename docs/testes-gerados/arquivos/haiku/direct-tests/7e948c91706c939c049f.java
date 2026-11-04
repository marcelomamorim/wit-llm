/*
 * @test
 * @summary Verifies ChoiceFormat.setChoices validation, replacement of limits and formats, and defensive copying of its arguments
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testMismatchedLengthsThrowsAndKeepsState();
        testSetChoicesReplacesLimitsAndFormats();
        testSetChoicesCopiesArguments();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMismatchedLengthsThrowsAndKeepsState() {
        ChoiceFormat cf = new ChoiceFormat(new double[]{0, 1}, new String[]{"a", "b"});
        boolean thrown = false;
        try {
            cf.setChoices(new double[]{0, 1, 2}, new String[]{"x", "y"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException expected for mismatched lengths");
        check(cf.getLimits().length == 2, "limits must be unchanged after failed setChoices");
        check(cf.format(0).equals("a"), "format(0) must still use original formats");
        check(cf.format(1).equals("b"), "format(1) must still use original formats");
    }

    static void testSetChoicesReplacesLimitsAndFormats() {
        ChoiceFormat cf = new ChoiceFormat(new double[]{0, 1, 2}, new String[]{"a", "b", "c"});
        cf.setChoices(new double[]{10, 20}, new String[]{"x", "y"});
        double[] limits = cf.getLimits();
        check(limits.length == 2, "limits length must be 2 after setChoices");
        check(limits[0] == 10.0 && limits[1] == 20.0, "limits must match new values");
        check(cf.format(5).equals("x"), "value below first limit must map to first format");
        check(cf.format(15).equals("x"), "value in [10,20) must map to \"x\"");
        check(cf.format(25).equals("y"), "value at or above last limit must map to last format");
        check(cf.format(100).equals("y"), "large value must map to last format");
    }

    static void testSetChoicesCopiesArguments() {
        ChoiceFormat cf = new ChoiceFormat(new double[]{0, 1, 2}, new String[]{"a", "b", "c"});
        double[] limits = {0, 1};
        String[] formats = {"p", "q"};
        cf.setChoices(limits, formats);
        limits[0] = 100;
        formats[0] = "zzz";
        check(cf.getLimits()[0] == 0.0, "internal limits must not reflect later changes to input array");
        check(cf.format(0).equals("p"), "internal formats must not reflect later changes to input array");
    }
}
