/*
 * @test
 * @summary Tests LocalTime.ofSecondOfDay boundaries, field values and round trip with toSecondOfDay
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testRoundTrip();
        testInvalidValues();
    }

    static void testValidValues() {
        check(LocalTime.ofSecondOfDay(0L).equals(LocalTime.of(0, 0, 0)), "midnight");
        check(LocalTime.ofSecondOfDay(60L).equals(LocalTime.of(0, 1, 0)), "one minute");
        check(LocalTime.ofSecondOfDay(3661L).equals(LocalTime.of(1, 1, 1)), "01:01:01");
        check(LocalTime.ofSecondOfDay(43200L).getHour() == 12, "noon hour");
        check(LocalTime.ofSecondOfDay(86399L).equals(LocalTime.of(23, 59, 59)), "last second of day");
        check(LocalTime.ofSecondOfDay(86399L).getNano() == 0, "nano must be zero");
    }

    static void testRoundTrip() {
        long[] values = {0L, 1L, 59L, 3599L, 3600L, 45296L, 86399L};
        for (long v : values) {
            LocalTime t = LocalTime.ofSecondOfDay(v);
            check(t.toSecondOfDay() == v, "round trip failed for " + v);
        }
    }

    static void testInvalidValues() {
        expectDateTimeException(-1L);
        expectDateTimeException(86400L);
        expectDateTimeException(Long.MIN_VALUE);
        expectDateTimeException(Long.MAX_VALUE);
    }

    static void expectDateTimeException(long value) {
        try {
            LocalTime t = LocalTime.ofSecondOfDay(value);
            throw new RuntimeException("expected DateTimeException for " + value + " but got " + t);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
