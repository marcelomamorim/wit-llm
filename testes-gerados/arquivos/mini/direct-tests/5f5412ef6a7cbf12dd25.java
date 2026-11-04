/*
 * @test
 * @summary Test UCharacterIterator.getInstance(CharacterIterator) with valid and null inputs
 * @run main UCharacterIteratorWitupTest
 */

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testGetInstanceWithValidCharacterIterator();
        testGetInstanceWithNull();
    }

    private static void testGetInstanceWithValidCharacterIterator() {
        String testString = "abc";
        CharacterIterator ci = new StringCharacterIterator(testString);
        sun.text.normalizer.UCharacterIterator uci = sun.text.normalizer.UCharacterIterator.getInstance(ci);
        if (uci == null) {
            throw new RuntimeException("getInstance returned null for valid CharacterIterator");
        }
        // Check that iteration returns the same characters
        int index = 0;
        int c;
        while ((c = uci.next()) != sun.text.normalizer.UCharacterIterator.DONE) {
            if ((char) c != testString.charAt(index)) {
                throw new RuntimeException("Character mismatch at index " + index + ": expected '" + testString.charAt(index) + "' but got '" + (char) c + "'");
            }
            index++;
        }
        if (index != testString.length()) {
            throw new RuntimeException("Did not iterate over all characters: expected " + testString.length() + " but iterated " + index);
        }
    }

    private static void testGetInstanceWithNull() {
        try {
            sun.text.normalizer.UCharacterIterator.getInstance((CharacterIterator) null);
            throw new RuntimeException("Expected NullPointerException or IllegalArgumentException when passing null, but no exception was thrown");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type when passing null: " + e);
        }
    }
}
