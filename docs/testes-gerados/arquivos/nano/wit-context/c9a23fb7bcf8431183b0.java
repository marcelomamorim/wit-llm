/* @test    @summary Test for ChoiceFormat constructor with valid and invalid inputs    @run main ChoiceFormatWitupTest */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testInvalidConstructor();
    }

    private static void testValidConstructor() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"Zero", "One", "Two"};
        ChoiceFormat cf = new ChoiceFormat(limits, formats);
        String result0 = cf.format(0.5);
        String result1 = cf.format(1.5);
        String result2 = cf.format(2.5);
        System.out.println("Valid constructor test:");
        System.out.println("Input 0.5: " + result0); // Expect "Zero"
        System.out.println("Input 1.5: " + result1); // Expect "One"
        System.out.println("Input 2.5: " + result2); // Expect "Two"
    }

    private static void testInvalidConstructor() {
        double[] limits = {0.0, 1.0};
        String[] formats = {"Zero", "One", "Extra"};
        boolean exceptionThrown = false;
        try {
            ChoiceFormat cf = new ChoiceFormat(limits, formats);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected IllegalArgumentException for mismatched array lengths.");
        }
        if (!exceptionThrown) {
            System.out.println("Error: Expected IllegalArgumentException was not thrown.");
        }
    }
}
