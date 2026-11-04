/*
 * @test
 * @summary Test java.time.chrono.HijrahDate.now() method
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.Clock;
import java.time.ZoneId;
import java.time.Instant;
import java.time.LocalDate;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testNowReturnsNonNullHijrahDate();
    }

    private static void testNowReturnsNonNullHijrahDate() {
        HijrahDate dateNow = HijrahDate.now();
        if (dateNow == null) {
            throw new AssertionError("HijrahDate.now() returned null");
        }
        // Check that the returned date corresponds to the current date in system default zone
        // by comparing with LocalDate.now() in system default zone
        LocalDate localDateNow = LocalDate.now();
        // HijrahDate should represent a date roughly corresponding to the current date
        // We cannot assert exact equality because HijrahDate is a different calendar,
        // but we can check that the HijrahDate can be converted to LocalDate and is close
        LocalDate converted = LocalDate.from(dateNow);
        long daysDiff = Math.abs(converted.toEpochDay() - localDateNow.toEpochDay());
        if (daysDiff > 1) {
            throw new AssertionError("HijrahDate.now() date differs from LocalDate.now() by more than 1 day");
        }
    }
}
