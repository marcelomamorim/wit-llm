/* @test    @summary Test MessageFormat's formatToCharacterIterator method for null and valid input    @run main MessageFormatWitupTest_P8625fa1de0ca */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest_P8625fa1de0ca {

    public static void main(String[] args) {
        testNullArguments();
        testValidArguments();
    }

    static void testNullArguments() {
        MessageFormat mf = new MessageFormat("Hello {0}");
        boolean exceptionThrown = false;
        try {
            mf.formatToCharacterIterator(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null arguments");
        }
    }

    static void testValidArguments() {
        MessageFormat mf = new MessageFormat("Hello {0}");
        Object[] args = {"World"};
        AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
        String result = toString(iterator);
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static String toString(AttributedCharacterIterator iterator) {
        StringBuilder sb = new StringBuilder();
        for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
            sb.append(c);
        }
        return sb.toString();
    }
}
