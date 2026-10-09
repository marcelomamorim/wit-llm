/* @test    @summary Test MessageFormat.setFormat method for valid and invalid indices    @run main MessageFormatWitupTest_P58ca04335008 */

import java.text.MessageFormat;
import java.text.Format;

public class MessageFormatWitupTest_P58ca04335008 {

    public static void main(String[] args) {
        try {
            testSetFormatValidIndex();
            testSetFormatInvalidIndex();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void testSetFormatValidIndex() {
        MessageFormat mf = new MessageFormat("{0}");
        Format newFormat = new java.text.DecimalFormat("#0.00");
        int index = 0;
        try {
            mf.setFormat(index, newFormat);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid index: " + e);
        }
        if (mf.formats == null || mf.formats.length <= index || mf.formats[index] != newFormat) {
            throw new AssertionError("Format not set correctly at index " + index);
        }
    }

    private static void testSetFormatInvalidIndex() {
        MessageFormat mf = new MessageFormat("{0}");
        Format newFormat = new java.text.DecimalFormat("#0.00");
        int invalidIndex = mf.maxOffset + 1;
        try {
            mf.setFormat(invalidIndex, newFormat);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for index " + invalidIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
