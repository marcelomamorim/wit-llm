/*
 * @test
 * @summary Tests for MessageFormat.formatToCharacterIterator with various inputs
 * @run main MessageFormatWitupTest_P353181e2ba95
 */

import java.text.MessageFormat;
import java.text.AttributedCharacterIterator;

public class MessageFormatWitupTest_P353181e2ba95 {

    public static void main(String[] args) {
        testNullArguments();
        testEmptyMessageFormat();
        testValidArguments();
        testNullArgumentThrows();
        testArgumentsArrayWithNull();
    }

    static void testNullArguments() {
        try {
            MessageFormat mf = new MessageFormat("Test {0}");
            mf.formatToCharacterIterator(null);
            System.err.println("Failed: Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testEmptyMessageFormat() {
        try {
            MessageFormat mf = new MessageFormat("");
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(new Object[]{"abc"});
            if (iterator == null) {
                System.err.println("Failed: iterator is null for empty pattern");
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for empty pattern: " + e);
        }
    }

    static void testValidArguments() {
        try {
            MessageFormat mf = new MessageFormat("Hello {0}");
            Object[] args = {"World"};
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
            StringBuilder sb = new StringBuilder();
            for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
                sb.append(c);
            }
            String result = sb.toString();
            if (!"Hello World".equals(result)) {
                System.err.println("Failed: Unexpected output: " + result);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception in valid arguments test: " + e);
        }
    }

    static void testNullArgumentThrows() {
        try {
            MessageFormat mf = new MessageFormat("Pattern {0}");
            mf.formatToCharacterIterator(null);
            System.err.println("Failed: Expected NullPointerException for null arguments");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testArgumentsArrayWithNull() {
        try {
            MessageFormat mf = new MessageFormat("Test {0}");
            Object[] args = {null};
            AttributedCharacterIterator iterator = mf.formatToCharacterIterator(args);
            StringBuilder sb = new StringBuilder();
            for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
                sb.append(c);
            }
            String result = sb.toString();
            if (!"Test null".equals(result)) {
                System.err.println("Failed: Unexpected output with null argument: " + result);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception with null argument: " + e);
        }
    }
}
