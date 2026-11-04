/*
 * @test
 * @summary Test ChoiceFormat.setChoices for valid and invalid inputs
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testValidSetChoices();
        testInvalidSetChoices();
    }

    private static void testValidSetChoices() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"zero", "one", "two"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        // Verify that the object was created with correct choices
        boolean success = true;
        try {
            cf.setChoices(limits, formats);
        } catch (Exception e) {
            success = false;
        }
        if (!success) {
            throw new AssertionError("setChoices threw an exception on valid input");
        }
    }

    private static void testInvalidSetChoices() {
        double[] limits = {0.0, 1.0};
        String[] formats = {"zero"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        double[] invalidLimits = {0.0, 1.0, 2.0};
        String[] invalidFormats = {"zero", "one"};
        boolean exceptionThrown = false;
        try {
            cf.setChoices(invalidLimits, invalidFormats);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("setChoices did not throw IllegalArgumentException on mismatched array lengths");
        }
    }
}
