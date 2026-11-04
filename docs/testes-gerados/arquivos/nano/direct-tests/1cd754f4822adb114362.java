/* @test    @summary Test the valueOf method of JapaneseEra for valid and invalid inputs    @run main JapaneseEraWitupTest */

import java.time.chrono.JapaneseEra;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testValidEra();
        testInvalidEra();
    }

    private static void testValidEra() {
        String knownEraName = "Meiji";
        try {
            JapaneseEra era = JapaneseEra.valueOf(knownEraName);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era name");
            }
            System.out.println("Valid era test passed: " + era);
        } catch (Exception e) {
            throw new AssertionError("Exception thrown for valid era name", e);
        }
    }

    private static void testInvalidEra() {
        String invalidEraName = "InvalidEra";
        boolean exceptionThrown = false;
        try {
            JapaneseEra.valueOf(invalidEraName);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid era name");
        }
        System.out.println("Invalid era test passed: exception thrown as expected");
    }
}
