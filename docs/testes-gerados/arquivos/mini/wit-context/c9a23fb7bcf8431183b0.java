/*
 * @test
 * @summary Test ChoiceFormat constructor with double[] limits and String[] formats
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {
    public static void main(String[] args) {
        testConstructorValid();
        testConstructorInvalidLengths();
    }

    private static void testConstructorValid() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"zero", "one", "two"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        // Check that formatting returns expected strings
        if (!"zero".equals(cf.format(0.0))) {
            throw new AssertionError("Expected 'zero' for 0.0");
        }
        if (!"one".equals(cf.format(1.0))) {
            throw new AssertionError("Expected 'one' for 1.0");
        }
        if (!"two".equals(cf.format(2.0))) {
            throw new AssertionError("Expected 'two' for 2.0");
        }
    }

    private static void testConstructorInvalidLengths() {
        double[] limits = {0.0, 1.0};
        String[] formats = {"zero"};
        boolean thrown = false;
        try {
            new ChoiceFormat(limits, formats);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException when formats.length != limits.length");
        }
    }
}
