/* @test    @summary Test for ReplaceableUCharacterIterator constructor with null buffer    @run main ReplaceableUCharacterIteratorWitupTest */

import sun.text.normalizer.ReplaceableUCharacterIterator;
import java.lang.IllegalArgumentException;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullBuffer();
        testConstructorWithValidBuffer();
    }

    private static void testConstructorWithNullBuffer() {
        boolean threwException = false;
        try {
            new ReplaceableUCharacterIterator((StringBuffer) null);
        } catch (IllegalArgumentException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected IllegalArgumentException for null buffer");
        }
    }

    private static void testConstructorWithValidBuffer() {
        StringBuffer buf = new StringBuffer("test");
        ReplaceableUCharacterIterator it = new ReplaceableUCharacterIterator(buf);
        if (it == null) {
            throw new AssertionError("Constructor returned null with valid buffer");
        }
        int length = it.getLength();
        if (length != buf.length()) {
            throw new AssertionError("getLength() mismatch: expected " + buf.length() + " but got " + length);
        }
        int currentChar = it.current();
        if (currentChar != buf.charAt(0)) {
            throw new AssertionError("current() mismatch: expected " + buf.charAt(0) + " but got " + currentChar);
        }
    }
}
