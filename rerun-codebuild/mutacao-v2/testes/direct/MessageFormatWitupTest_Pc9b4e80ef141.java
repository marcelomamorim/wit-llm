/*
 * @test
 * @summary Test java.text.MessageFormat.setFormat method behavior
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
        MessageFormat mf = new MessageFormat("{0}");
        Format df = new DecimalFormat("0.00");
        // setFormat with valid index 0
        mf.setFormat(0, df);
        // No exception expected, test passes if no exception thrown
    }

    private static void testSetFormatIndexOutOfBounds() {
        MessageFormat mf = new MessageFormat("{0}");
        Format df = new DecimalFormat("0.00");
        int invalidIndex = 1; // maxOffset is 0 for pattern "{0}"
        try {
            mf.setFormat(invalidIndex, df);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            if (e.getMessage() == null || !e.getMessage().equals(String.valueOf(invalidIndex))) {
                throw new RuntimeException("Exception message mismatch");
            }
        }
    }
}
