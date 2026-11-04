/*
 * @test
 * @summary Test formatToCharacterIterator(Object) method of MessageFormat
 * @run main MessageFormatWitupTest
 */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest {
    public static void main(String[] args) {
        testNullArguments();
        testEmptyPatternNoArguments();
        testValidPatternWithArguments();
    }

    private static void testNullArguments() {
        MessageFormat mf = new MessageFormat("Test {0}");
        try {
            mf.formatToCharacterIterator(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testEmptyPatternNoArguments() {
        MessageFormat mf = new MessageFormat("");
        AttributedCharacterIterator it = mf.formatToCharacterIterator(new Object[0]);
        if (it == null) {
            throw new RuntimeException("Returned AttributedCharacterIterator is null");
        }
        // The iterator should have no characters
        if (it.getEndIndex() - it.getBeginIndex() != 0) {
            throw new RuntimeException("Expected empty AttributedCharacterIterator for empty pattern");
        }
    }

    private static void testValidPatternWithArguments() {
        MessageFormat mf = new MessageFormat("Number: {0,number,integer}");
        Object[] args = {12345};
        AttributedCharacterIterator it = mf.formatToCharacterIterator(args);
        if (it == null) {
            throw new RuntimeException("Returned AttributedCharacterIterator is null");
        }
        // The iterator should contain characters representing the formatted number
        int length = it.getEndIndex() - it.getBeginIndex();
        if (length <= 0) {
            throw new RuntimeException("Expected non-empty AttributedCharacterIterator for valid pattern and arguments");
        }
    }
}
