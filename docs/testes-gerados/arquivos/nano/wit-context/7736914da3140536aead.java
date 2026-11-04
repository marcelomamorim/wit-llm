/* @test    @summary Test for UCharacterIterator.getInstance(String) with null input    @run main UCharacterIteratorWitupTest */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValidString();
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance(null);
            System.err.println("Failed: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for null input");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testGetInstanceWithValidString() {
        String testString = "abc";
        UCharacterIterator iterator = null;
        try {
            iterator = UCharacterIterator.getInstance(testString);
            if (iterator != null) {
                System.out.println("Passed: getInstance with valid string returns non-null");
            } else {
                System.err.println("Failed: getInstance with valid string returned null");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception thrown during getInstance with valid string: " + t);
        }
    }
}
