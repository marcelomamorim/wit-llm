/* @test    @summary Test for MessageFormat.formatToCharacterIterator with null argument and valid input    @run main MessageFormatWitupTest_Pedd03ac5da4a */

import java.text.AttributedCharacterIterator;
import java.text.MessageFormat;

public class MessageFormatWitupTest_Pedd03ac5da4a {

    public static void main(String[] args) {
        testNullArguments();
        testValidArguments();
    }

    static void testNullArguments() {
        MessageFormat mf = new MessageFormat("Test {0}");
        try {
            mf.formatToCharacterIterator(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    static void testValidArguments() {
        MessageFormat mf = new MessageFormat("Hello {0}");
        Object[] args = {"World"};
        try {
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
            if (iterator == null) {
                throw new AssertionError("Returned iterator is null");
            }
            // Additional checks can be added here for content if needed
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
