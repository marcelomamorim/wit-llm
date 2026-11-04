/*
 * @test
 * @summary Verifies the public String constructor of AttributedString: null rejection and text exposure via getIterator.
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;

public class AttributedStringWitupTest {

    public static void main(String[] args) {
        testNullTextThrowsNPE();
        testNonEmptyTextIsExposed();
        testEmptyTextIsExposed();
    }

    static void testNullTextThrowsNPE() {
        boolean thrown = false;
        try {
            new AttributedString((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null text");
        }
    }

    static void testNonEmptyTextIsExposed() {
        AttributedString s = new AttributedString("abc");
        AttributedCharacterIterator it = s.getIterator();
        if (it.getBeginIndex() != 0) {
            throw new RuntimeException("Begin index expected 0 but was " + it.getBeginIndex());
        }
        if (it.getEndIndex() != 3) {
            throw new RuntimeException("End index expected 3 but was " + it.getEndIndex());
        }
        if (it.first() != 'a') {
            throw new RuntimeException("First char expected 'a' but was " + it.first());
        }
        if (it.last() != 'c') {
            throw new RuntimeException("Last char expected 'c' but was " + it.last());
        }
    }

    static void testEmptyTextIsExposed() {
        AttributedString s = new AttributedString("");
        AttributedCharacterIterator it = s.getIterator();
        if (it.getBeginIndex() != 0 || it.getEndIndex() != 0) {
            throw new RuntimeException("Empty text should have begin=end=0");
        }
        if (it.first() != CharacterIterator.DONE) {
            throw new RuntimeException("Empty text first() should return DONE");
        }
    }
}
