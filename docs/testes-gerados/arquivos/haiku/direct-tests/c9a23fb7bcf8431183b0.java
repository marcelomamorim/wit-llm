/*
 * @test
 * @summary Tests ChoiceFormat(double[] limits, String[] formats) constructor: interval matching, boundary and out-of-range formatting, defensive copy and length mismatch rejection
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        double[] limits = {0, 1, 2};
        String[] formats = {"a", "b", "c"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);

        check("a".equals(cf.format(0)), "0 should match first interval");
        check("a".equals(cf.format(0.5)), "0.5 should match first interval");
        check("b".equals(cf.format(1)), "1 should match second interval (lower bound inclusive)");
        check("b".equals(cf.format(1.5)), "1.5 should match second interval");
        check("c".equals(cf.format(2)), "2 should match last interval");
        check("c".equals(cf.format(100)), "values above the last limit should use last format");
        check("a".equals(cf.format(-5)), "values below the first limit should use first format");

        Object[] got = cf.getFormats();
        check(got.length == 3, "getFormats length should be 3");
        check("a".equals(got[0]) && "b".equals(got[1]) && "c".equals(got[2]),
              "getFormats should return the formats passed to the constructor");

        limits[0] = 10;
        formats[0] = "z";
        check("a".equals(cf.format(0)), "modifying limits array after construction must not affect the object");
        check("a".equals(cf.format(0)), "modifying formats array after construction must not affect the object");

        boolean thrown = false;
        try {
            new ChoiceFormat(new double[]{0, 1}, new String[]{"x"});
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "mismatched limits and formats lengths should throw IllegalArgumentException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
