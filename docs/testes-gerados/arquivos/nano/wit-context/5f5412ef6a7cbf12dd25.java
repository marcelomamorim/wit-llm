/* @test    @summary Test for UCharacterIterator.getInstance(CharacterIterator) handling null input    @run main UCharacterIteratorWitupTest */

import java.text.CharacterIterator;
import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValidInput();
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((CharacterIterator) null);
            System.err.println("Failed: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for null input");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testGetInstanceWithValidInput() {
        String testString = "Sample";
        CharacterIterator ci = new java.text.StringCharacterIterator(testString);
        try {
            UCharacterIterator uci = UCharacterIterator.getInstance(ci);
            if (uci != null) {
                System.out.println("Passed: getInstance with valid CharacterIterator");
            } else {
                System.err.println("Failed: getInstance returned null with valid input");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception thrown with valid input: " + t);
        }
    }
}
