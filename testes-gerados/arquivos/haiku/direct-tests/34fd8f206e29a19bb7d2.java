/*
 * @test
 * @summary Verify LocalDateTime.withMinute keeps date, hour, seconds and nanos, and rejects invalid minutes
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, 3, 15, 10, 20, 30, 123000000);

        LocalDateTime changed = base.withMinute(45);
        check(changed.getYear() == 2020, "year must be preserved");
        check(changed.getMonthValue() == 3, "month must be preserved");
        check(changed.getDayOfMonth() == 15, "day must be preserved");
        check(changed.getHour() == 10, "hour must be preserved");
        check(changed.getMinute() == 45, "minute must be updated");
        check(changed.getSecond() == 30, "second must be preserved");
        check(changed.getNano() == 123000000, "nano must be preserved");

        check(base.getMinute() == 20, "withMinute must not mutate the original");

        check(base.withMinute(20).equals(base), "same minute must yield equal value");
        check(base.withMinute(0).getMinute() == 0, "minute 0 must be accepted");
        check(base.withMinute(59).getMinute() == 59, "minute 59 must be accepted");

        LocalDateTime endOfMonth = LocalDateTime.of(2020, 2, 29, 23, 0);
        check(endOfMonth.withMinute(59).equals(LocalDateTime.of(2020, 2, 29, 23, 59)),
              "end of month date and hour must be preserved");

        expectDateTimeException(-1);
        expectDateTimeException(60);
    }

    private static void expectDateTimeException(int minute) {
        try {
            LocalDateTime.of(2020, 3, 15, 10, 20, 30).withMinute(minute);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for minute " + minute);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
