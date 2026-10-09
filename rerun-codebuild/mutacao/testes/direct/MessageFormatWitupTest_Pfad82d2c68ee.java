/* @test    @summary Test MessageFormat.setFormat method for valid and invalid indices    @run main MessageFormatWitupTest_Pfad82d2c68ee */

import java.text.MessageFormat;
import java.text.Format;
import java.util.Locale;

public class MessageFormatWitupTest_Pfad82d2c68ee {

    public static void main(String[] args) {
        testSetFormatValidIndex();
        testSetFormatInvalidIndex();
    }

    private static void testSetFormatValidIndex() {
        MessageFormat mf = new MessageFormat("{0}");
        Format newFormat = new java.text.DecimalFormat("#0.00");
        try {
            mf.setFormat(0, newFormat);
            assert mf.getFormats()[0] == newFormat : "Format at index 0 should be updated to newFormat";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
    }

    private static void testSetFormatInvalidIndex() {
        MessageFormat mf = new MessageFormat("{0}");
        Format newFormat = new java.text.DecimalFormat("#0.00");
        int invalidIndex = 10; // Out of bounds
        try {
            mf.setFormat(invalidIndex, newFormat);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for invalid index");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
