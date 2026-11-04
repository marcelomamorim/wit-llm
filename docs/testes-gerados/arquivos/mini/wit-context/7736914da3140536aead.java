/*
 * @test
 * @summary Test sun.text.normalizer.UCharacterIterator.getInstance(String) for null and valid input
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValidString();
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((String) null);
            throw new RuntimeException("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testGetInstanceWithValidString() {
        String testString = "abc";
        UCharacterIterator it = UCharacterIterator.getInstance(testString);
        if (it == null) {
            throw new RuntimeException("getInstance returned null for valid input");
        }
    }
}
