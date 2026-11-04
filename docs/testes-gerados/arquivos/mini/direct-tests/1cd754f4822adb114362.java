/*
 * @test
 * @summary Test JapaneseEra.valueOf(String) method behavior
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.util.Objects;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testValueOfWithValidName();
        testValueOfWithNull();
        testValueOfWithInvalidName();
    }

    private static void testValueOfWithValidName() {
        for (JapaneseEra era : JapaneseEra.values()) {
            String name = era.getName();
            JapaneseEra result = JapaneseEra.valueOf(name);
            if (!Objects.equals(result, era)) {
                throw new AssertionError("valueOf did not return expected era for name: " + name);
            }
        }
    }

    private static void testValueOfWithNull() {
        try {
            JapaneseEra.valueOf(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValueOfWithInvalidName() {
        String invalidName = "InvalidEraName";
        try {
            JapaneseEra.valueOf(invalidName);
            throw new AssertionError("Expected IllegalArgumentException for invalid era name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
