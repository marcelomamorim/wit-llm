/* @test    @summary Test UCharacterIterator.getInstance(StringBuffer) method    @run main UCharacterIteratorWitupTest */

import java.text.CharacterIterator;
import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithEmptyStringBuffer();
        testGetInstanceWithNonEmptyStringBuffer();
        testGetInstanceWithNull();
    }

    private static void testGetInstanceWithEmptyStringBuffer() {
        StringBuffer sb = new StringBuffer("");
        UCharacterIterator iterator = UCharacterIterator.getInstance(sb);
        assert iterator != null : "Iterator should not be null for empty StringBuffer";
        int ch = iterator.next();
        assert ch == UCharacterIterator.DONE : "Next on empty StringBuffer should return DONE";
    }

    private static void testGetInstanceWithNonEmptyStringBuffer() {
        StringBuffer sb = new StringBuffer("abc");
        UCharacterIterator iterator = UCharacterIterator.getInstance(sb);
        assert iterator != null : "Iterator should not be null for non-empty StringBuffer";
        int ch1 = iterator.next();
        int ch2 = iterator.next();
        int ch3 = iterator.next();
        int ch4 = iterator.next();
        assert ch1 == 'a' : "First character should be 'a'";
        assert ch2 == 'b' : "Second character should be 'b'";
        assert ch3 == 'c' : "Third character should be 'c'";
        assert ch4 == UCharacterIterator.DONE : "After last character, should return DONE";
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
            assert false : "Expected IllegalArgumentException for null StringBuffer";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
