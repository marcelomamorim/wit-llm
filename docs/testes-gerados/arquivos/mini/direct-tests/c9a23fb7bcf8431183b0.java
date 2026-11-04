/*
 * @test
 * @summary Test ChoiceFormat constructor with double[] limits and String[] formats
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {
    public static void main(String[] args) {
        testValidLimitsAndFormats();
        testEmptyArrays();
        testSingleElementArrays();
        testNullLimits();
        testNullFormats();
    }

    private static void testValidLimitsAndFormats() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"zero", "one", "two"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        // Test formatting values within the limits
        String f0 = cf.format(0.0);
        String f1 = cf.format(1.0);
        String f15 = cf.format(1.5);
        String f2 = cf.format(2.0);
        if (!"zero".equals(f0)) throw new RuntimeException("Expected 'zero' for 0.0 but got " + f0);
        if (!"one".equals(f1)) throw new RuntimeException("Expected 'one' for 1.0 but got " + f1);
        if (!"one".equals(f15)) throw new RuntimeException("Expected 'one' for 1.5 but got " + f15);
        if (!"two".equals(f2)) throw new RuntimeException("Expected 'two' for 2.0 but got " + f2);
    }

    private static void testEmptyArrays() {
        double[] limits = new double[0];
        String[] formats = new String[0];
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        // Formatting any number should return null or empty string (ChoiceFormat returns null format as "null")
        String result = cf.format(1.0);
        if (result == null) throw new RuntimeException("Expected non-null format string for empty arrays");
    }

    private static void testSingleElementArrays() {
        double[] limits = {5.0};
        String[] formats = {"five"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        // Any number should map to the single format
        String f4 = cf.format(4.0);
        String f5 = cf.format(5.0);
        String f6 = cf.format(6.0);
        if (!"five".equals(f4)) throw new RuntimeException("Expected 'five' for 4.0 but got " + f4);
        if (!"five".equals(f5)) throw new RuntimeException("Expected 'five' for 5.0 but got " + f5);
        if (!"five".equals(f6)) throw new RuntimeException("Expected 'five' for 6.0 but got " + f6);
    }

    private static void testNullLimits() {
        try {
            new ChoiceFormat(null, new String[] {"a"});
            throw new RuntimeException("Expected NullPointerException for null limits");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullFormats() {
        try {
            new ChoiceFormat(new double[] {0.0}, null);
            throw new RuntimeException("Expected NullPointerException for null formats");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
