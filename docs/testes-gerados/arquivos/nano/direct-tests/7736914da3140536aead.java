/* @test    @summary Test UCharacterIterator.getInstance(String) with a simple string    @run main UCharacterIteratorWitupTest */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithNormalString();
        testGetInstanceWithEmptyString();
        testGetInstanceWithNullString();
    }

    private static void testGetInstanceWithNormalString() {
        String testString = "Hello, World!";
        UCharacterIterator iterator = UCharacterIterator.getInstance(testString);
        if (iterator == null) {
            throw new AssertionError("Expected non-null UCharacterIterator for a valid string.");
        }
        // Verify that the iterator's current character is the first character of the string
        int firstCharCodePoint = iterator.current();
        int expectedCodePoint = testString.codePointAt(0);
        if (firstCharCodePoint != expectedCodePoint) {
            throw new AssertionError("First code point mismatch: expected " + expectedCodePoint + " but got " + firstCharCodePoint);
        }
        // Move to next and verify
        int nextCodePoint = iterator.next();
        int secondCodePoint = testString.codePointAt(Character.charCount(expectedCodePoint));
        if (nextCodePoint != secondCodePoint) {
            throw new AssertionError("Second code point mismatch: expected " + secondCodePoint + " but got " + nextCodePoint);
        }
    }

    private static void testGetInstanceWithEmptyString() {
        String emptyString = "";
        UCharacterIterator iterator = UCharacterIterator.getInstance(emptyString);
        if (iterator == null) {
            throw new AssertionError("Expected non-null UCharacterIterator for empty string.");
        }
        int current = iterator.current();
        if (current != UCharacterIterator.DONE) {
            throw new AssertionError("Expected DONE indicator for empty string, but got " + current);
        }
    }

    private static void testGetInstanceWithNullString() {
        try {
            UCharacterIterator.getInstance((String) null);
            throw new AssertionError("Expected IllegalArgumentException for null input.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
