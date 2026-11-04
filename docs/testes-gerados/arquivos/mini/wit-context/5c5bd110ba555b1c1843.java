/*
 * @test
 * @summary Deterministic tests for java.time.chrono.HijrahDate.now(ZoneId)
 * @run main HijrahDateWitupTest
 */

import java.time.ZoneId;
import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testNowWithValidZone();
        testNowWithNullZone();
    }

    private static void testNowWithValidZone() {
        ZoneId zone = ZoneId.of("UTC");
        HijrahDate date = HijrahDate.now(zone);
        if (date == null) {
            throw new AssertionError("HijrahDate.now(zone) returned null");
        }
        // Basic sanity: year, month, day should be positive
        int year = date.get(YEAR);
        int month = date.get(MONTH_OF_YEAR);
        int day = date.get(DAY_OF_MONTH);
        if (year <= 0) {
            throw new AssertionError("Year must be positive, got: " + year);
        }
        if (month < 1 || month > 12) {
            throw new AssertionError("Month must be 1-12, got: " + month);
        }
        if (day < 1 || day > 30) { // Hijrah months can be 29 or 30 days, 30 is safe upper bound
            throw new AssertionError("Day must be 1-30, got: " + day);
        }
    }

    private static void testNowWithNullZone() {
        try {
            HijrahDate.now(null);
            throw new AssertionError("Expected NullPointerException for null ZoneId");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception ex) {
            throw new AssertionError("Expected NullPointerException but got " + ex);
        }
    }

    // Constants for ChronoField to avoid import static
    private static final java.time.temporal.ChronoField YEAR = java.time.temporal.ChronoField.YEAR;
    private static final java.time.temporal.ChronoField MONTH_OF_YEAR = java.time.temporal.ChronoField.MONTH_OF_YEAR;
    private static final java.time.temporal.ChronoField DAY_OF_MONTH = java.time.temporal.ChronoField.DAY_OF_MONTH;
}
