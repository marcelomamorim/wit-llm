/*
 * @test
 * @summary Tests LocalDateTime.of(int,int,int,int,int) with valid fields, observable getters, toString, equality and invalid field rejection
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2020, 1, 15, 10, 30);
        check(dt.getYear() == 2020, "year");
        check(dt.getMonthValue() == 1, "month");
        check(dt.getDayOfMonth() == 15, "dayOfMonth");
        check(dt.getHour() == 10, "hour");
        check(dt.getMinute() == 30, "minute");
        check(dt.getSecond() == 0, "second");
        check(dt.getNano() == 0, "nano");
        check(dt.toString().equals("2020-01-15T10:30"), "toString: " + dt);

        LocalDateTime midnight = LocalDateTime.of(1999, 12, 31, 0, 0);
        check(midnight.toString().equals("1999-12-31T00:00"), "midnight toString: " + midnight);

        LocalDateTime leap = LocalDateTime.of(2020, 2, 29, 23, 59);
        check(leap.getDayOfMonth() == 29, "leap day");
        check(leap.getHour() == 23 && leap.getMinute() == 59, "leap time");

        check(LocalDateTime.of(2020, 1, 15, 10, 30).equals(dt), "equality");
        check(!LocalDateTime.of(2020, 1, 15, 10, 31).equals(dt), "inequality");

        expectDateTimeException(2019, 2, 29, 0, 0);
        expectDateTimeException(2020, 13, 1, 0, 0);
        expectDateTimeException(2020, 0, 1, 0, 0);
        expectDateTimeException(2020, 1, 0, 0, 0);
        expectDateTimeException(2020, 4, 31, 0, 0);
        expectDateTimeException(2020, 1, 1, 24, 0);
        expectDateTimeException(2020, 1, 1, 0, 60);
        expectDateTimeException(2020, 1, 1, -1, 0);
    }

    static void expectDateTimeException(int year, int month, int day, int hour, int minute) {
        try {
            LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute);
            throw new RuntimeException("Expected DateTimeException for " + dt);
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
