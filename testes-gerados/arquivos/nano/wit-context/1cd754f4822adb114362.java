/*
 * @test
 * @summary Test for JapaneseEra.valueOf(String)
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.util.Objects;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testValueOfWithValidString();
        testValueOfWithNull();
        testValueOfWithInvalidString();
    }

    static void testValueOfWithValidString() {
        try {
            // Assuming "Meiji" is a valid era name in KNOWN_ERAS
            String validEraName = "Meiji";
            JapaneseEra era = JapaneseEra.valueOf(validEraName);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era name");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era name", e);
        }
    }

    static void testValueOfWithNull() {
        try {
            JapaneseEra.valueOf(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null input", e);
        }
    }

    static void testValueOfWithInvalidString() {
        try {
            JapaneseEra.valueOf("InvalidEraName");
            throw new AssertionError("Expected IllegalArgumentException for invalid era name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for invalid era name", e);
        }
    }
}
