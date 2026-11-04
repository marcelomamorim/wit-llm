/*
 * @test
 * @summary Witup-generated tests for StringCharacterIterator four-argument constructor
 * @run main StringCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testNullText();
        testInvalidRanges();
        testValidConstruction();
        testBoundaryPositions();
    }

    static void testNullText() {
        try {
            new StringCharacterIterator(null, 0, 0, 0);
            fail("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
        }
    }

    static void testInvalidRanges() {
        expectIllegalArgument("abc", -1, 2, 0);
        expectIllegalArgument("abc", 2, 1, 2);
        expectIllegalArgument("abc", 0, 4, 0);
        expectIllegalArgument("abc", 1, 3, 0);
        expectIllegalArgument("abc", 0, 2, 3);
    }

    static void testValidConstruction() {
        StringCharacterIterator it = new StringCharacterIterator("abcd", 1, 3, 2);
        check(it.getBeginIndex() == 1, "begin index");
        check(it.getEndIndex() == 3, "end index");
        check(it.getIndex() == 2, "current index");
        check(it.current() == 'c', "current character");
    }

    static void testBoundaryPositions() {
        StringCharacterIterator atBegin = new StringCharacterIterator("abcd", 1, 3, 1);
        check(atBegin.getIndex() == 1, "pos equals begin");
        check(atBegin.current() == 'b', "char at begin");

        StringCharacterIterator atEnd = new StringCharacterIterator("abcd", 1, 3, 3);
        check(atEnd.getIndex() == 3, "pos equals end");
        check(atEnd.current() == CharacterIterator.DONE, "DONE at end");

        StringCharacterIterator empty = new StringCharacterIterator("abcd", 2, 2, 2);
        check(empty.getBeginIndex() == 2 && empty.getEndIndex() == 2, "empty range");
        check(empty.current() == CharacterIterator.DONE, "DONE for empty range");

        StringCharacterIterator full = new StringCharacterIterator("abcd", 0, 4, 4);
        check(full.getIndex() == 4, "full range pos at end");
    }

    static void expectIllegalArgument(String text, int begin, int end, int pos) {
        try {
            new StringCharacterIterator(text, begin, end, pos);
            fail("expected IllegalArgumentException for begin=" + begin
                 + " end=" + end + " pos=" + pos);
        } catch (IllegalArgumentException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
