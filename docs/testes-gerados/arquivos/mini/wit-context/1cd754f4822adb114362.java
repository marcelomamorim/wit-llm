/*
 * @test
 * @summary Test JapaneseEra.valueOf(String) for valid, invalid and null inputs
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.util.Objects;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testValueOfWithValidName();
        testValueOfWithInvalidName();
        testValueOfWithNull();
        System.out.println("All tests passed.");
    }

    private static void testValueOfWithValidName() {
        // Iterate over all known eras and verify valueOf returns the same era
        for (JapaneseEra era : JapaneseEra.values()) {
            String name = era.getName();
            JapaneseEra result = JapaneseEra.valueOf(name);
            if (result != era) {
                throw new AssertionError("valueOf did not return expected era for name: " + name);
            }
        }
    }

    private static void testValueOfWithInvalidName() {
        // Test with a string that does not match any known era
        String invalidName = "InvalidEraName";
        try {
            JapaneseEra.valueOf(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid era name");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testValueOfWithNull() {
        try {
            JapaneseEra.valueOf(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
