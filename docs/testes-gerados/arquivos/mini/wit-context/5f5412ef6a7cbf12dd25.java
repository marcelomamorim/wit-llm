/*
 * @test
 * @summary Test sun.text.normalizer.UCharacterIterator.getInstance(CharacterIterator) for null and valid input
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValidCharacterIterator();
        System.out.println("All tests passed.");
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((CharacterIterator) null);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testGetInstanceWithValidCharacterIterator() {
        String testString = "abc";
        CharacterIterator ci = new StringCharacterIterator(testString);
        UCharacterIterator uci = UCharacterIterator.getInstance(ci);
        if (uci == null) {
            throw new RuntimeException("UCharacterIterator.getInstance returned null for valid input");
        }
        // Basic sanity check: iterate over characters and verify
        int count = 0;
        int c;
        while ((c = uci.next()) != UCharacterIterator.DONE) {
            if (c != testString.charAt(count)) {
                throw new RuntimeException("Character mismatch at position " + count);
            }
            count++;
        }
        if (count != testString.length()) {
            throw new RuntimeException("Did not iterate over all characters");
        }
    }
}
