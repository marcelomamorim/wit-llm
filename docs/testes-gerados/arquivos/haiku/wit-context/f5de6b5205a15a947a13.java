/*
 * @test
 * @summary Verify StringCharacterIterator.setText(String) rejects null and resets iterator state
 * @run main StringCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testSetTextResetsStateToNewText();
        testSetTextWithEmptyString();
    }

    static void testNullThrowsNullPointerException() {
        StringCharacterIterator it = new StringCharacterIterator("abc", 1);
        boolean thrown = false;
        try {
            it.setText(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null text");
        }
        if (it.current() != 'b') {
            throw new RuntimeException("State must be unchanged after rejected setText(null)");
        }
    }

    static void testSetTextResetsStateToNewText() {
        StringCharacterIterator it = new StringCharacterIterator("xyz");
        it.setIndex(2);
        it.setText("abc");
        if (it.getBeginIndex() != 0) {
            throw new RuntimeException("Expected begin index 0, got " + it.getBeginIndex());
        }
        if (it.getEndIndex() != 3) {
            throw new RuntimeException("Expected end index 3, got " + it.getEndIndex());
        }
        if (it.getIndex() != 0) {
            throw new RuntimeException("Expected index 0 after setText, got " + it.getIndex());
        }
        if (it.current() != 'a') {
            throw new RuntimeException("Expected current 'a', got " + it.current());
        }
        if (it.last() != 'c') {
            throw new RuntimeException("Expected last character 'c'");
        }
    }

    static void testSetTextWithEmptyString() {
        StringCharacterIterator it = new StringCharacterIterator("abc", 2);
        it.setText("");
        if (it.getBeginIndex() != 0 || it.getEndIndex() != 0) {
            throw new RuntimeException("Expected empty range [0,0)");
        }
        if (it.getIndex() != 0) {
            throw new RuntimeException("Expected index 0 for empty text");
        }
        if (it.current() != CharacterIterator.DONE) {
            throw new RuntimeException("Expected DONE for empty text");
        }
    }
}
