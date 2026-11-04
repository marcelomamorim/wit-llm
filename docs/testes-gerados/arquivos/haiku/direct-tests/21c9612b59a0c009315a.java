/*
 * @test
 * @summary Behavioral tests for LocalDateTime.withHour(int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 1, 15, 10, 30, 45, 500);

        // Changes only the hour; date, minute, second and nano are preserved.
        LocalDateTime changed = base.withHour(15);
        check(changed.equals(LocalDateTime.of(2020, 1, 15, 15, 30, 45, 500)), "hour 15 result");
        check(changed.getYear() == 2020, "year preserved");
        check(changed.getMonthValue() == 1, "month preserved");
        check(changed.getDayOfMonth() == 15, "day preserved");
        check(changed.getMinute() == 30, "minute preserved");
        check(changed.getSecond() == 45, "second preserved");
        check(changed.getNano() == 500, "nano preserved");

        // Boundary values.
        check(base.withHour(0).equals(LocalDateTime.of(2020, 1, 15, 0, 30, 45, 500)), "hour 0 boundary");
        check(base.withHour(23).equals(LocalDateTime.of(2020, 1, 15, 23, 30, 45, 500)), "hour 23 boundary");

        // Same hour returns an equal value.
        check(base.withHour(10).equals(base), "same hour equals original");

        // Original value is immutable.
        check(base.getHour() == 10, "original unchanged");

        // Midnight rollover case: date stays the same when hour is set on the same day.
        LocalDateTime lateNight = LocalDateTime.of(2019, 12, 31, 23, 59, 59, 0);
        LocalDateTime early = lateNight.withHour(0);
        check(early.equals(LocalDateTime.of(2019, 12, 31, 0, 59, 59, 0)), "hour 0 keeps date");

        // Invalid hours must be rejected.
        assertRejected(base, -1);
        assertRejected(base, 24);
        assertRejected(base, Integer.MAX_VALUE);
    }

    private static void assertRejected(LocalDateTime value, int hour) {
        try {
            value.withHour(hour);
        } catch (RuntimeException expected) {
            return;
        }
        throw new AssertionError("expected exception for hour " + hour);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
