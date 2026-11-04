/*
 * @test
 * @summary Test for JapaneseChronology.eraOf(int) method, including exception path.
 * @run main JapaneseChronologyWitupTest
 */

import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.time.DateTimeException;

public class JapaneseChronologyWitupTest {

    public static void main(String[] args) {
        testValidEraValues();
        testInvalidEraValues();
    }

    static void testValidEraValues() {
        // Test with a valid era value, e.g., 1
        try {
            JapaneseEra era = JapaneseChronology.INSTANCE.eraOf(1);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era value 1");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era value 1", e);
        }
        // Test with another valid era value, e.g., 0
        try {
            JapaneseEra era = JapaneseChronology.INSTANCE.eraOf(0);
            if (era == null) {
                throw new AssertionError("Expected non-null for valid era value 0");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid era value 0", e);
        }
    }

    static void testInvalidEraValues() {
        // Test with an invalid era value, e.g., -2, expecting DateTimeException
        try {
            JapaneseChronology.INSTANCE.eraOf(-2);
            throw new AssertionError("Expected DateTimeException for era value -2");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for era value -2", e);
        }
        // Test with an invalid era value, e.g., 1000, expecting DateTimeException
        try {
            JapaneseChronology.INSTANCE.eraOf(1000);
            throw new AssertionError("Expected DateTimeException for era value 1000");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for era value 1000", e);
        }
    }
}
