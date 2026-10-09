/* @test    @summary Test for MessageFormat.setFormat to verify ArrayIndexOutOfBoundsException handling    @run main MessageFormatWitupTest_Pfad82d2c68ee */

import java.text.MessageFormat;
import java.text.Format;

public class MessageFormatWitupTest_Pfad82d2c68ee {

    public static void main(String[] args) {
        testSetFormatWithInvalidIndex();
    }

    public static void testSetFormatWithInvalidIndex() {
        MessageFormat mf = new MessageFormat("Test pattern {0}");
        // Prepare a valid index based on the current formats array length
        int invalidIndex = 100; // intentionally large to trigger exception
        try {
            mf.setFormat(invalidIndex, null);
            // If no exception, test fails
            System.err.println("Failed: Expected ArrayIndexOutOfBoundsException was not thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception caught, test passes
            System.out.println("Passed: Caught expected ArrayIndexOutOfBoundsException.");
        } catch (Exception e) {
            // Unexpected exception, test fails
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
