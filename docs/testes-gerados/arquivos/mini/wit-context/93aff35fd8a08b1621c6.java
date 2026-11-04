/*
 * @test
 * @summary Test java.time.LocalDate.toEpochSecond(LocalTime, ZoneOffset) for null checks and correct epoch second calculation
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testNullTimeThrows();
        testNullOffsetThrows();
        testEpochSecondCalculation();
    }

    private static void testNullTimeThrows() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        try {
            date.toEpochSecond(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null time");
        } catch (NullPointerException e) {
            if (!"time".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null time");
            }
        }
    }

    private static void testNullOffsetThrows() {
        LocalDate date = LocalDate.of(2000, 1, 1);
        LocalTime time = LocalTime.of(0, 0);
        try {
            date.toEpochSecond(time, null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            if (!"offset".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null offset");
            }
        }
    }

    private static void testEpochSecondCalculation() {
        LocalDate date = LocalDate.of(1970, 1, 2); // one day after epoch
        LocalTime time = LocalTime.of(0, 0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        long expected = 86400L; // 1 day * 86400 seconds
        long actual = date.toEpochSecond(time, offset);
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        // Test with non-zero time and offset
        date = LocalDate.of(1970, 1, 1);
        time = LocalTime.of(1, 0, 0); // 3600 seconds
        offset = ZoneOffset.ofHours(1); // offset +3600 seconds
        expected = 0L; // (0 days * 86400 + 3600) - 3600 = 0
        actual = date.toEpochSecond(time, offset);
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }
}
