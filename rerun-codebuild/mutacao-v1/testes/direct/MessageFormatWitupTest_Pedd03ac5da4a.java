/* @test    @summary Test MessageFormat.formatToCharacterIterator with valid arguments    @run main MessageFormatWitupTest_Pedd03ac5da4a */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest_Pedd03ac5da4a {

    public static void main(String[] args) {
        testFormatToCharacterIteratorWithValidArguments();
        testFormatToCharacterIteratorWithNullArguments();
    }

    private static void testFormatToCharacterIteratorWithValidArguments() {
        String pattern = "Hello, {0}!";
        MessageFormat mf = new MessageFormat(pattern);
        Object[] arguments = {"World"};
        try {
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(arguments);
            if (iterator == null) {
                throw new AssertionError("Expected non-null AttributedCharacterIterator");
            }
            StringBuilder sb = new StringBuilder();
            for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
                sb.append(c);
            }
            String resultString = sb.toString();
            if (!resultString.contains("Hello, World!")) {
                throw new AssertionError("Unexpected formatted string: " + resultString);
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during formatToCharacterIterator with valid arguments", e);
        }
    }

    private static void testFormatToCharacterIteratorWithNullArguments() {
        String pattern = "Test";
        MessageFormat mf = new MessageFormat(pattern);
        try {
            mf.formatToCharacterIterator(null);
            throw new AssertionError("Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
