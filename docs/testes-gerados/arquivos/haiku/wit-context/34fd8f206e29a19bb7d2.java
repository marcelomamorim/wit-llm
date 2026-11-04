/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.withMinute(int): valid update and DateTimeException for out-of-range minutes
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2018, 9, 15, 10, 30);

        LocalDateTime changed = base.withMinute(59);
        check(changed.equals(LocalDateTime.of(2018, 9, 15, 10, 59)), "withMinute(59) should produce 10:59");
        check(changed.getYear() == 2018, "year must be preserved");
        check(changed.getMonthValue() == 9, "month must be preserved");
        check(changed.getDayOfMonth() == 15, "day must be preserved");
        check(changed.getHour() == 10, "hour must be preserved");
        check(changed.getSecond() == 0, "second must be preserved");

        LocalDateTime zero = base.withMinute(0);
        check(zero.equals(LocalDateTime.of(2018, 9, 15, 10, 0)), "withMinute(0) should produce 10:00");

        LocalDateTime same = base.withMinute(30);
        check(same.equals(base), "withMinute with the same value should equal the original");

        expectDateTimeException(base, 60);
        expectDateTimeException(base, -1);
        expectDateTimeException(base, 100);
    }

    static void expectDateTimeException(LocalDateTime t, int minute) {
        try {
            t.withMinute(minute);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for minute " + minute);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
