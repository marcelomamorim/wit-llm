/*
 * @test
 * @summary Test LocalDate.toEpochSecond(LocalTime, ZoneOffset) for correctness and exception handling
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testToEpochSecondWithValidInputs();
        testToEpochSecondWithNullTime();
        testToEpochSecondWithNullOffset();
        testToEpochSecondWithBoundaryValues();
    }

    static void testToEpochSecondWithValidInputs() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalTime time = LocalTime.of(12, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        long result = date.toEpochSecond(time, offset);
        long expected = (date.toEpochDay() * 86400L) + time.toSecondOfDay() - offset.getTotalSeconds();
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testToEpochSecondWithNullTime() {
        try {
            LocalDate date = LocalDate.of(2023, 3, 15);
            date.toEpochSecond(null, ZoneOffset.ofHours(1));
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testToEpochSecondWithNullOffset() {
        try {
            LocalDate date = LocalDate.of(2023, 3, 15);
            date.toEpochSecond(LocalTime.of(10, 0), null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testToEpochSecondWithBoundaryValues() {
        LocalDate date = LocalDate.of(1970, 1, 1);
        LocalTime time = LocalTime.of(0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(0);
        long result = date.toEpochSecond(time, offset);
        long expected = 0L;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
        // Test with maximum offset
        offset = ZoneOffset.ofHoursMinutes(14, 0);
        result = date.toEpochSecond(time, offset);
        expected = (date.toEpochDay() * 86400L) - offset.getTotalSeconds();
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
        // Test with minimum offset
        offset = ZoneOffset.ofHoursMinutes(-12, 0);
        result = date.toEpochSecond(time, offset);
        expected = (date.toEpochDay() * 86400L) - offset.getTotalSeconds();
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
