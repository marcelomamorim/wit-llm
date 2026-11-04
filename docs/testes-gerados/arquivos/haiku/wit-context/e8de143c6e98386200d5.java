/*
 * @test
 * @summary Witup-derived checks for MessageFormat.formatToCharacterIterator observable behavior
 * @run main MessageFormatWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.util.Locale;

public class MessageFormatWitupTest {

    public static void main(String[] args) {
        checkEquals("Hello World!",
                text(new MessageFormat("Hello {0}!").formatToCharacterIterator(new Object[]{"World"})));

        checkEquals("1,234",
                text(new MessageFormat("{0}", Locale.US).formatToCharacterIterator(new Object[]{Integer.valueOf(1234)})));

        checkEquals("abc",
                text(new MessageFormat("abc").formatToCharacterIterator(new Object[0])));

        checkEquals("",
                text(new MessageFormat("").formatToCharacterIterator(new Object[0])));

        try {
            new MessageFormat("{0}").formatToCharacterIterator(null);
            throw new RuntimeException("expected NullPointerException for null arguments");
        } catch (NullPointerException expected) {
        }
    }

    private static String text(AttributedCharacterIterator it) {
        StringBuilder sb = new StringBuilder();
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static void checkEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }
}
