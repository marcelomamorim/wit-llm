/*
 * @test
 * @summary Behavioral tests for MessageFormat.formatToCharacterIterator using public API
 * @run main MessageFormatWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.MessageFormat;

public class MessageFormatWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArgumentsThrowsNPE();
        testSingleStringArgument();
        testMultipleStringArguments();
        testEmptyPatternProducesEmptyText();
        testIteratorBoundsMatchFormattedText();
    }

    static void testNullArgumentsThrowsNPE() {
        MessageFormat mf = new MessageFormat("a{0}b");
        boolean thrown = false;
        try {
            mf.formatToCharacterIterator(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null arguments");
        }
    }

    static void testSingleStringArgument() {
        MessageFormat mf = new MessageFormat("a{0}b");
        AttributedCharacterIterator it =
            mf.formatToCharacterIterator(new Object[] {"XY"});
        check("aXYb", text(it));
    }

    static void testMultipleStringArguments() {
        MessageFormat mf = new MessageFormat("{0}-{1}");
        AttributedCharacterIterator it =
            mf.formatToCharacterIterator(new Object[] {"A", "B"});
        check("A-B", text(it));
    }

    static void testEmptyPatternProducesEmptyText() {
        MessageFormat mf = new MessageFormat("");
        AttributedCharacterIterator it =
            mf.formatToCharacterIterator(new Object[0]);
        check("", text(it));
        if (it.getBeginIndex() != it.getEndIndex()) {
            throw new RuntimeException("Expected empty range for empty pattern");
        }
    }

    static void testIteratorBoundsMatchFormattedText() {
        MessageFormat mf = new MessageFormat("x{0}yz");
        AttributedCharacterIterator it =
            mf.formatToCharacterIterator(new Object[] {"QQ"});
        String expected = mf.format(new Object[] {"QQ"});
        check(expected, text(it));
        if (it.getEndIndex() - it.getBeginIndex() != expected.length()) {
            throw new RuntimeException("Iterator length does not match formatted text");
        }
    }

    static String text(AttributedCharacterIterator it) {
        StringBuilder sb = new StringBuilder();
        for (char c = it.first(); c != AttributedCharacterIterator.DONE; c = it.next()) {
            sb.append(c);
        }
        return sb.toString();
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
