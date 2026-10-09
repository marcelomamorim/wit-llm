/*
 * @test
 * @summary Test MessageFormat.setFormat behavior including bounds checking
 * @run main MessageFormatWitupTest_Pc9b4e80ef141
 */

import java.text.MessageFormat;
import java.text.Format;
import java.text.DecimalFormat;

public class MessageFormatWitupTest_Pc9b4e80ef141 {
    public static void main(String[] args) {
        testSetFormatValidIndex();
        testSetFormatIndexOutOfBounds();
    }

    private static void testSetFormatValidIndex() {
        // Create a MessageFormat with a pattern that expects at least one argument
        MessageFormat mf = new MessageFormat("{0}");
        Format df = new DecimalFormat("0.00");

        // Set format at index 0 (valid)
        mf.setFormat(0, df);

        // Verify by formatting a number and checking output contains formatted number
        Object[] args = {123.456};
        String result = mf.format(args);
        if (!result.contains("123.46")) {
            throw new RuntimeException("testSetFormatValidIndex failed: formatted output incorrect: " + result);
        }
    }

    private static void testSetFormatIndexOutOfBounds() {
        MessageFormat mf = new MessageFormat("{0}");
        Format df = new DecimalFormat("0.00");

        int invalidIndex = 1; // Since pattern has only one argument, maxOffset is 0
        try {
            mf.setFormat(invalidIndex, df);
            throw new RuntimeException("testSetFormatIndexOutOfBounds failed: expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
            if (e.getMessage() == null || !e.getMessage().equals(String.valueOf(invalidIndex))) {
                throw new RuntimeException("testSetFormatIndexOutOfBounds failed: exception message unexpected: " + e.getMessage());
            }
        }
    }
}
