/*
 * @test
 * @summary Test java.time.LocalDateTime.withMinute(int) method
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteBoundary();
        testWithMinuteNoChange();
    }

    private static void testWithMinuteValid() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 15);
        LocalDateTime changed = dt.withMinute(30);
        if (changed.getMinute() != 30) {
            throw new AssertionError("Expected minute 30 but got " + changed.getMinute());
        }
        if (changed.getHour() != 10 || changed.getDayOfMonth() != 1 || changed.getMonth() != Month.JANUARY || changed.getYear() != 2018) {
            throw new AssertionError("Date part changed unexpectedly");
        }
    }

    private static void testWithMinuteBoundary() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59);
        LocalDateTime changed = dt.withMinute(0);
        if (changed.getMinute() != 0) {
            throw new AssertionError("Expected minute 0 but got " + changed.getMinute());
        }
        if (changed.getHour() != 23 || changed.getDayOfMonth() != 31 || changed.getMonth() != Month.DECEMBER || changed.getYear() != 2018) {
            throw new AssertionError("Date part changed unexpectedly");
        }
    }

    private static void testWithMinuteNoChange() {
        LocalDateTime dt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 12, 45);
        LocalDateTime changed = dt.withMinute(45);
        if (changed != dt) {
            throw new AssertionError("Expected same instance when minute is unchanged");
        }
    }
}
