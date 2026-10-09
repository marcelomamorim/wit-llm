/* @test    @summary Test MessageFormat.formatToCharacterIterator with simple arguments    @run main MessageFormatWitupTest_P353181e2ba95 */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest_P353181e2ba95 {

    public static void main(String[] args) {
        testSimpleStringArguments();
        testNullArguments();
        testEmptyArgumentsArray();
        System.out.println("All tests passed.");
    }

    private static void testSimpleStringArguments() {
        MessageFormat mf = new MessageFormat("Hello, {0}!");
        Object[] arguments = {"World"};
        AttributedCharacterIterator it = mf.formatToCharacterIterator(arguments);
        String result = toString(it);
        if (!result.contains("Hello, World!")) {
            throw new AssertionError("Expected 'Hello, World!', got: " + result);
        }
    }

    private static void testNullArguments() {
        MessageFormat mf = new MessageFormat("Test {0}");
        try {
            mf.formatToCharacterIterator(null);
            throw new AssertionError("Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptyArgumentsArray() {
        MessageFormat mf = new MessageFormat("No args here");
        Object[] arguments = {};
        AttributedCharacterIterator it = mf.formatToCharacterIterator(arguments);
        String result = toString(it);
        if (!"No args here".equals(result)) {
            throw new AssertionError("Expected 'No args here', got: " + result);
        }
    }

    private static String toString(AttributedCharacterIterator it) {
        StringBuilder sb = new StringBuilder();
        for (char c = it.first(); c != AttributedCharacterIterator.DONE; c = it.next()) {
            sb.append(c);
        }
        return sb.toString();
    }
}
