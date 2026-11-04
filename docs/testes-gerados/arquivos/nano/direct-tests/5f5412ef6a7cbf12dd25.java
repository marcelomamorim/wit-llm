/* @test    @summary Test UCharacterIterator.getInstance(CharacterIterator) with a simple StringBuffer    @run main UCharacterIteratorWitupTest */

import java.text.StringCharacterIterator;
import java.text.CharacterIterator;
import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithStringBuffer();
        testGetInstanceWithEmptyStringBuffer();
        testGetInstanceWithNull();
    }

    static void testGetInstanceWithStringBuffer() {
        StringBuffer sb = new StringBuffer("abc");
        UCharacterIterator it = UCharacterIterator.getInstance(sb);
        assert it != null : "Instance should not be null";
        // Verify that getInstance returns a non-null object
        // and that it can be used to iterate over the string
        int ch = it.next();
        assert ch == 'a' : "First character should be 'a'";
        ch = it.next();
        assert ch == 'b' : "Second character should be 'b'";
        ch = it.next();
        assert ch == 'c' : "Third character should be 'c'";
        ch = it.next();
        assert ch == UCharacterIterator.DONE : "Should reach end of string";
    }

    static void testGetInstanceWithEmptyStringBuffer() {
        StringBuffer sb = new StringBuffer("");
        UCharacterIterator it = UCharacterIterator.getInstance(sb);
        assert it != null : "Instance should not be null";
        int ch = it.next();
        assert ch == UCharacterIterator.DONE : "Empty string should immediately be DONE";
    }

    static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
            assert false : "Expected IllegalArgumentException for null input";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
