/*
 * @test
 * @summary Test java.text.AttributedString.setIndex(int) method behavior
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedString;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;

public class AttributedStringWitupTest {
    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalidLow();
        testSetIndexInvalidHigh();
    }

    private static void testSetIndexValid() {
        String text = "abcde";
        AttributedString as = new AttributedString(text);
        AttributedCharacterIterator it = as.getIterator();

        // setIndex is not public in AttributedString, so we test via iterator
        // but the method is in AttributedString, so we test the public getIterator and its setIndex
        // Actually, AttributedString.setIndex is public, but it is not static and requires internal state
        // We test the public method setIndex(int) of AttributedString directly

        // Valid indices: beginIndex <= position <= endIndex
        int begin = it.getBeginIndex();
        int end = it.getEndIndex();

        for (int pos = begin; pos <= end; pos++) {
            char c = as.setIndex(pos);
            // The returned char should be equal to the character at pos if pos < end
            if (pos < end) {
                char expected = text.charAt(pos);
                if (c != expected) {
                    throw new RuntimeException("setIndex(" + pos + ") returned '" + c + "', expected '" + expected + "'");
                }
            } else {
                // If pos == end, the returned char is CharacterIterator.DONE
                if (c != AttributedCharacterIterator.DONE) {
                    throw new RuntimeException("setIndex(" + pos + ") at end returned '" + c + "', expected DONE");
                }
            }
        }
    }

    private static void testSetIndexInvalidLow() {
        AttributedString as = new AttributedString("abc");
        try {
            as.setIndex(-1);
            throw new RuntimeException("Expected IllegalArgumentException for index -1");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetIndexInvalidHigh() {
        AttributedString as = new AttributedString("abc");
        int end = as.getIterator().getEndIndex();
        try {
            as.setIndex(end + 1);
            throw new RuntimeException("Expected IllegalArgumentException for index " + (end + 1));
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
