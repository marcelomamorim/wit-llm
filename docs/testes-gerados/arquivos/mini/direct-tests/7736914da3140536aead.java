/*
 * @test
 * @summary Test sun.text.normalizer.UCharacterIterator.getInstance(String) method
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testGetInstanceWithValidString();
        testGetInstanceWithEmptyString();
        testGetInstanceWithNullString();
    }

    private static void testGetInstanceWithValidString() {
        String testStr = "abc";
        UCharacterIterator it = UCharacterIterator.getInstance(testStr);
        if (it == null) {
            throw new RuntimeException("getInstance returned null for valid string");
        }
        int firstChar = it.next();
        if (firstChar != 'a') {
            throw new RuntimeException("Iterator did not return expected first character");
        }
    }

    private static void testGetInstanceWithEmptyString() {
        String emptyStr = "";
        UCharacterIterator it = UCharacterIterator.getInstance(emptyStr);
        if (it == null) {
            throw new RuntimeException("getInstance returned null for empty string");
        }
        int firstChar = it.next();
        if (firstChar != UCharacterIterator.DONE) {
            throw new RuntimeException("Iterator over empty string should return DONE immediately");
        }
    }

    private static void testGetInstanceWithNullString() {
        try {
            UCharacterIterator.getInstance((String) null);
            throw new RuntimeException("Expected NullPointerException or IllegalArgumentException for null input");
        } catch (NullPointerException | IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type for null input: " + e);
        }
    }
}
