/*
 * @test
 * @summary Test ChoiceFormat.setChoices method for correct behavior and exception
 * @run main ChoiceFormatWitupTest
 */

import java.text.ChoiceFormat;
import java.util.Arrays;

public class ChoiceFormatWitupTest {
    public static void main(String[] args) {
        testSetChoicesValid();
        testSetChoicesMismatchedLengths();
    }

    private static void testSetChoicesValid() {
        double[] limits = {0.0, 1.0, 2.0};
        String[] formats = {"zero", "one", "two"};
        ChoiceFormat cf = new ChoiceFormat();
        cf.setChoices(limits, formats);

        // Verify internal arrays copied correctly by formatting known values
        // Since ChoiceFormat formats based on limits, test formatting
        String result0 = cf.format(0.0);
        String result1 = cf.format(1.0);
        String result2 = cf.format(2.0);

        if (!"zero".equals(result0)) {
            throw new RuntimeException("Expected 'zero' for 0.0 but got: " + result0);
        }
        if (!"one".equals(result1)) {
            throw new RuntimeException("Expected 'one' for 1.0 but got: " + result1);
        }
        if (!"two".equals(result2)) {
            throw new RuntimeException("Expected 'two' for 2.0 but got: " + result2);
        }
    }

    private static void testSetChoicesMismatchedLengths() {
        double[] limits = {0.0, 1.0};
        String[] formats = {"zero"};
        ChoiceFormat cf = new ChoiceFormat();
        boolean thrown = false;
        try {
            cf.setChoices(limits, formats);
        } catch (IllegalArgumentException e) {
            thrown = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("same length")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for mismatched array lengths");
        }
    }
}
