/*
 * @test
 * @summary Witup-style tests for LocalDateTime.of(int,int,int,int,int,int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 15, 10, 30, 45);
        check(dt.getYear() == 2018, "year");
        check(dt.getMonthValue() == 9, "month");
        check(dt.getDayOfMonth() == 15, "day");
        check(dt.getHour() == 10, "hour");
        check(dt.getMinute() == 30, "minute");
        check(dt.getSecond() == 45, "second");
        check(dt.getNano() == 0, "nano");
        check(dt.toString().equals("2018-09-15T10:30:45"), "toString " + dt);

        check(LocalDateTime.of(2000, 2, 29, 0, 0, 0).getDayOfMonth() == 29, "leap year 2000");
        check(LocalDateTime.of(2018, 1, 1, 0, 0, 0).equals(LocalDateTime.of(2018, 1, 1, 0, 0, 0)), "equals");
        check(LocalDateTime.of(2018, 1, 1, 23, 59, 59).toString().equals("2018-01-01T23:59:59"), "end of day");
        check(LocalDateTime.of(2018, 12, 31, 0, 0, 0).toString().equals("2018-12-31T00:00"), "midnight toString");

        expectDateTimeException(2018, 2, 29, 0, 0, 0);
        expectDateTimeException(1900, 2, 29, 0, 0, 0);
        expectDateTimeException(2018, 13, 1, 0, 0, 0);
        expectDateTimeException(2018, 0, 1, 0, 0, 0);
        expectDateTimeException(2018, 4, 31, 0, 0, 0);
        expectDateTimeException(2018, 1, 1, 24, 0, 0);
        expectDateTimeException(2018, 1, 1, -1, 0, 0);
        expectDateTimeException(2018, 1, 1, 0, 60, 0);
        expectDateTimeException(2018, 1, 1, 0, 0, 60);
    }

    static void expectDateTimeException(int year, int month, int day,
                                        int hour, int minute, int second) {
        boolean thrown = false;
        try {
            LocalDateTime.of(year, month, day, hour, minute, second);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException for " + year + "-" + month + "-" + day
                + "T" + hour + ":" + minute + ":" + second);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
