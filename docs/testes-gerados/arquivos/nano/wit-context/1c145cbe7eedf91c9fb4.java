/* @test    @summary Test for NormalizerBase constructor with valid inputs and null input handling    @run main NormalizerBaseWitupTest */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullString();
    }

    private static void testConstructorWithValidString() {
        String testString = "test";
        Mode mode = Mode.NFC;
        int options = 0;
        try {
            NormalizerBase nb = new NormalizerBase(testString, mode, options);
            if (nb == null) {
                throw new AssertionError("Constructor returned null for valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input: " + e);
        }
    }

    private static void testConstructorWithNullString() {
        String nullString = null;
        Mode mode = Mode.NFC;
        int options = 0;
        try {
            new NormalizerBase(nullString, mode, options);
            throw new AssertionError("Expected IllegalArgumentException for null string");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
