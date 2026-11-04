/*
 * @test
 * @summary Verifies the 4-argument StringCharacterIterator constructor: valid ranges, boundaries, and exceptions for null text, invalid range, and invalid position
 * @run main StringCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testBoundaries();
        testTwoArgConstructorDelegation();
        testNullText();
        testInvalidRanges();
        testInvalidPositions();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testValidRange() {
        StringCharacterIterator it = new StringCharacterIterator("abcdef", 1, 4, 2);
        check(it.getBeginIndex() == 1, "begin should be 1");
        check(it.getEndIndex() == 4, "end should be 4");
        check(it.getIndex() == 2, "index should be 2");
        check(it.current() == 'c', "current should be 'c'");
    }

    static void testBoundaries() {
        StringCharacterIterator atEnd = new StringCharacterIterator("abc", 0, 3, 3);
        check(atEnd.getIndex() == 3, "index at end should be 3");
        check(atEnd.current() == CharacterIterator.DONE, "current at end should be DONE");

        StringCharacterIterator collapsed = new StringCharacterIterator("abc", 3, 3, 3);
        check(collapsed.getBeginIndex() == 3, "collapsed begin should be 3");
        check(collapsed.getEndIndex() == 3, "collapsed end should be 3");
        check(collapsed.getIndex() == 3, "collapsed index should be 3");

        StringCharacterIterator emptyText = new StringCharacterIterator("", 0, 0, 0);
        check(emptyText.getEndIndex() == 0, "empty text end should be 0");
        check(emptyText.current() == CharacterIterator.DONE, "empty text current should be DONE");

        StringCharacterIterator fullRange = new StringCharacterIterator("abc", 0, 3, 0);
        check(fullRange.current() == 'a', "full range current should be 'a'");
    }

    static void testTwoArgConstructorDelegation() {
        StringCharacterIterator it = new StringCharacterIterator("xyz", 1);
        check(it.getBeginIndex() == 0, "two-arg begin should be 0");
        check(it.getEndIndex() == 3, "two-arg end should be text length");
        check(it.getIndex() == 1, "two-arg index should be 1");
    }

    static void testNullText() {
        try {
            new StringCharacterIterator(null, 0, 0, 0);
            throw new RuntimeException("expected NullPointerException for null text");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void expectIAE(String text, int begin, int end, int pos) {
        try {
            new StringCharacterIterator(text, begin, end, pos);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for "
                + begin + ", " + end + ", " + pos);
    }

    static void testInvalidRanges() {
        expectIAE("abc", -1, 2, 0);
        expectIAE("abc", 2, 1, 1);
        expectIAE("abc", 0, 4, 0);
    }

    static void testInvalidPositions() {
        expectIAE("abc", 1, 2, 0);
        expectIAE("abc", 1, 2, 3);
        expectIAE("abc", 0, 3, -1);
    }
}
