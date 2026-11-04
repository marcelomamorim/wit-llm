/* @test    @summary Test constructor of ReplaceableUCharacterIterator with valid string input    @run main ReplaceableUCharacterIteratorWitupTest */

import sun.text.normalizer.ReplaceableUCharacterIterator;
import sun.text.normalizer.ReplaceableString;

public class ReplaceableUCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNull();
    }

    static void testConstructorWithValidString() {
        String testStr = "abc";
        ReplaceableUCharacterIterator iterator = new ReplaceableUCharacterIterator(testStr);
        int length = iterator.getLength();
        if (length != testStr.length()) {
            throw new AssertionError("Expected length: " + testStr.length() + ", but got: " + length);
        }
        int currentChar = iterator.current();
        if (currentChar != testStr.charAt(0)) {
            throw new AssertionError("Expected current char: " + testStr.charAt(0) + ", but got: " + currentChar);
        }
    }

    static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new ReplaceableUCharacterIterator((String) null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null input");
        }
    }
}
