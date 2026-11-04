/*
 * @test
 * @summary Test for JapaneseChronology.eraOf(int)
 * @run main JapaneseChronologyWitupTest
 */

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;

public class JapaneseChronologyWitupTest {

    public static void main(String[] args) {
        testValidEraValues();
        testInvalidEraValues();
    }

    static void testValidEraValues() {
        // Test known valid era values
        try {
            JapaneseEra era1 = JapaneseChronology.INSTANCE.eraOf(1);
            assert era1 != null : "Era 1 should not be null";
            JapaneseEra era2 = JapaneseChronology.INSTANCE.eraOf(2);
            assert era2 != null : "Era 2 should not be null";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era values", e);
        }
    }

    static void testInvalidEraValues() {
        // Test invalid era values, expecting exceptions or nulls
        int[] invalidValues = {0, -1, 9999};
        for (int val : invalidValues) {
            try {
                JapaneseEra era = JapaneseChronology.INSTANCE.eraOf(val);
                // Depending on implementation, it may throw or return null
                assert era != null : "Era for invalid value " + val + " should not be null";
            } catch (Exception e) {
                // Expected if method throws exception
                assert e instanceof RuntimeException || e instanceof IllegalArgumentException :
                    "Expected RuntimeException or IllegalArgumentException for value " + val;
            }
        }
    }
}
