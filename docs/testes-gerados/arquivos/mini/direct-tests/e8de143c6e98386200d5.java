/*
 * @test
 * @summary Test java.text.MessageFormat.formatToCharacterIterator(Object) method
 * @run main MessageFormatWitupTest
 */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest {
    public static void main(String[] args) throws Exception {
        testNullArgument();
        testEmptyArguments();
        testValidArguments();
    }

    private static void testNullArgument() {
        MessageFormat mf = new MessageFormat("{0}");
        try {
            mf.formatToCharacterIterator(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
            if (!"formatToCharacterIterator must be passed non-null object".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testEmptyArguments() {
        MessageFormat mf = new MessageFormat("");
        AttributedCharacterIterator it = mf.formatToCharacterIterator(new Object[0]);
        if (it == null) {
            throw new RuntimeException("Returned AttributedCharacterIterator is null");
        }
        // The iterator should have no characters
        if (it.getEndIndex() - it.getBeginIndex() != 0) {
            throw new RuntimeException("Expected empty AttributedCharacterIterator");
        }
    }

    private static void testValidArguments() {
        MessageFormat mf = new MessageFormat("Hello {0}");
        Object[] args = new Object[] {"World"};
        AttributedCharacterIterator it = mf.formatToCharacterIterator(args);
        if (it == null) {
            throw new RuntimeException("Returned AttributedCharacterIterator is null");
        }
        // The iterator should contain the formatted string "Hello World"
        StringBuilder sb = new StringBuilder();
        for (char c = it.first(); c != AttributedCharacterIterator.DONE; c = it.next()) {
            sb.append(c);
        }
        String result = sb.toString();
        if (!"Hello World".equals(result)) {
            throw new RuntimeException("Unexpected formatted string: " + result);
        }
    }
}
