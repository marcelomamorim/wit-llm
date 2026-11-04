/*
 * @test
 * @summary Verify AttributedString iterator setIndex bounds checking
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testValidIndexes();
        testOutOfRange();
        testEmptyString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(AttributedCharacterIterator it, int position) {
        try {
            it.setIndex(position);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for position " + position);
    }

    static void testValidIndexes() {
        AttributedCharacterIterator it = new AttributedString("abc").getIterator();
        check(it.setIndex(0) == 'a', "setIndex(0) should return 'a'");
        check(it.setIndex(1) == 'b', "setIndex(1) should return 'b'");
        check(it.setIndex(2) == 'c', "setIndex(2) should return 'c'");
        check(it.setIndex(3) == AttributedCharacterIterator.DONE, "setIndex(endIndex) should return DONE");
    }

    static void testOutOfRange() {
        AttributedCharacterIterator it = new AttributedString("abc").getIterator();
        expectIllegalArgument(it, -1);
        expectIllegalArgument(it, 4);
    }

    static void testEmptyString() {
        AttributedCharacterIterator it = new AttributedString("").getIterator();
        check(it.setIndex(0) == AttributedCharacterIterator.DONE, "empty string setIndex(0) should return DONE");
        expectIllegalArgument(it, 1);
    }
}
