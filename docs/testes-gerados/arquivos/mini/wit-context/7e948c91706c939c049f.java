/*
 * @test
 * @summary Test ChoiceFormat.setChoices for argument length mismatch and normal behavior
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;

public class ChoiceFormatWitupTest {
    public static void main(String[] args) {
        testSetChoicesLengthMismatch();
        testSetChoicesNormal();
    }

    private static void testSetChoicesLengthMismatch() {
        ChoiceFormat cf = new ChoiceFormat("0#zero|1#one");
        double[] limits = {0.0, 1.0};
        String[] formats = {"zero"}; // length mismatch
        try {
            cf.setChoices(limits, formats);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("same length")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetChoicesNormal() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"zero", "one", "two"};
        ChoiceFormat cf = new ChoiceFormat("0#zero|1#one|2#two");
        cf.setChoices(limits, formats);
        // Verify that formatting returns expected strings
        if (!"zero".equals(cf.format(0.0))) {
            throw new RuntimeException("Formatting 0.0 did not return 'zero'");
        }
        if (!"one".equals(cf.format(1.0))) {
            throw new RuntimeException("Formatting 1.0 did not return 'one'");
        }
        if (!"two".equals(cf.format(2.0))) {
            throw new RuntimeException("Formatting 2.0 did not return 'two'");
        }
    }
}
