/* @test    @summary Test constructor with StringBuffer argument in ReplaceableUCharacterIterator    @run main ReplaceableUCharacterIteratorWitupTest */

import sun.text.normalizer.ReplaceableUCharacterIterator;
import java.lang.reflect.Constructor;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidBuffer();
        testConstructorWithNullBuffer();
    }

    static void testConstructorWithValidBuffer() {
        StringBuffer buf = new StringBuffer("abc");
        ReplaceableUCharacterIterator iterator = new ReplaceableUCharacterIterator(buf);
        int length = iterator.getLength();
        int currentChar = iterator.current();
        assert length == buf.length() : "Expected length " + buf.length() + " but got " + length;
        assert currentChar == buf.charAt(0) : "Expected current char " + buf.charAt(0) + " but got " + currentChar;
    }

    static void testConstructorWithNullBuffer() {
        try {
            new ReplaceableUCharacterIterator((StringBuffer) null);
            assert false : "Expected IllegalArgumentException for null buffer";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
