/*
 * @test
 * @summary Verifies StringCharacterIterator.setText resets range and position and rejects null
 * @run main StringCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testResetsRangeAndPosition();
        testEmptyText();
        testNullThrowsAndKeepsState();
        testResetAfterSetIndex();
    }

    static void testResetsRangeAndPosition() {
        StringCharacterIterator it = new StringCharacterIterator("abcdef", 3, 5, 4);
        it.setText("xyz");
        check(it.getBeginIndex() == 0, "begin should be 0");
        check(it.getEndIndex() == 3, "end should be text length");
        check(it.getIndex() == 0, "pos should be 0");
        check(it.current() == 'x', "current should be first char of new text");
    }

    static void testEmptyText() {
        StringCharacterIterator it = new StringCharacterIterator("abc", 2);
        it.setText("");
        check(it.getBeginIndex() == 0, "begin should be 0 for empty text");
        check(it.getEndIndex() == 0, "end should be 0 for empty text");
        check(it.getIndex() == 0, "pos should be 0 for empty text");
        check(it.current() == CharacterIterator.DONE, "empty text should be DONE");
    }

    static void testNullThrowsAndKeepsState() {
        StringCharacterIterator it = new StringCharacterIterator("xyz");
        boolean thrown = false;
        try {
            it.setText(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setText(null) must throw NullPointerException");
        check(it.getEndIndex() == 3, "state must be unchanged after null");
        check(it.current() == 'x', "current must be unchanged after null");
    }

    static void testResetAfterSetIndex() {
        StringCharacterIterator it = new StringCharacterIterator("hello");
        it.setIndex(2);
        check(it.getIndex() == 2, "setIndex should move position");
        it.setText("hello");
        check(it.getIndex() == 0, "setText should reset position to 0");
        check(it.current() == 'h', "current should be 'h' after reset");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
