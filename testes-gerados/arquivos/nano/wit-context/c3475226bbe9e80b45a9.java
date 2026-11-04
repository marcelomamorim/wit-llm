/* @test    @summary Tests for java.time.chrono.HijrahDate.of(int, int, int) method.
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahChronology;
import java.time.DateTimeException;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidInputs();
    }

    static void testValidInputs() {
        try {
            HijrahDate date = HijrahDate.of(1442, 9, 15);
            if (date == null) {
                throw new AssertionError("Expected non-null HijrahDate for valid inputs");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid inputs: " + e);
        }
    }

    static void testInvalidInputs() {
        // Test for invalid month (e.g., 0)
        try {
            HijrahDate.of(1442, 0, 10);
            throw new AssertionError("Expected DateTimeException for month=0");
        } catch (DateTimeException e) {
            // Expected
        }
        // Test for invalid month (e.g., 13)
        try {
            HijrahDate.of(1442, 13, 10);
            throw new AssertionError("Expected DateTimeException for month=13");
        } catch (DateTimeException e) {
            // Expected
        }
        // Test for invalid day (e.g., 0)
        try {
            HijrahDate.of(1442, 5, 0);
            throw new AssertionError("Expected DateTimeException for dayOfMonth=0");
        } catch (DateTimeException e) {
            // Expected
        }
        // Test for invalid day (e.g., exceeding month length)
        try {
            HijrahDate.of(1442, 5, 50);
            throw new AssertionError("Expected DateTimeException for invalid dayOfMonth");
        } catch (DateTimeException e) {
            // Expected
        }
        // Test for invalid year (e.g., negative or too large)
        try {
            HijrahDate.of(-1, 5, 10);
            // Depending on implementation, may throw or accept; assume exception
            throw new AssertionError("Expected DateTimeException for negative year");
        } catch (DateTimeException e) {
            // Expected
        }
    }
}
