/*
 * @test
 * @summary Witup-generated checks for LocalDateTime.of(int,int,int,int,int,int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2020, 2, 29, 23, 59, 58);
        check(dt.getYear() == 2020 && dt.getMonthValue() == 2 && dt.getDayOfMonth() == 29, "date fields");
        check(dt.getHour() == 23 && dt.getMinute() == 59 && dt.getSecond() == 58, "time fields");
        check(dt.getNano() == 0, "nano must be zero");

        check(LocalDateTime.of(2000, 2, 29, 0, 0, 0).getDayOfMonth() == 29, "leap year 2000");
        check(LocalDateTime.of(-1, 1, 1, 0, 0, 0).getYear() == -1, "negative year");
        check(LocalDateTime.of(2020, 12, 31, 23, 59, 59).getMonthValue() == 12, "december");

        expect(() -> LocalDateTime.of(2019, 2, 29, 0, 0, 0), "non-leap February 29");
        expect(() -> LocalDateTime.of(2100, 2, 29, 0, 0, 0), "century non-leap February 29");
        expect(() -> LocalDateTime.of(2020, 2, 30, 0, 0, 0), "February 30");
        expect(() -> LocalDateTime.of(2020, 4, 31, 0, 0, 0), "April 31");
        expect(() -> LocalDateTime.of(2020, 0, 1, 0, 0, 0), "month 0");
        expect(() -> LocalDateTime.of(2020, 13, 1, 0, 0, 0), "month 13");
        expect(() -> LocalDateTime.of(2020, 1, 0, 0, 0, 0), "day 0");
        expect(() -> LocalDateTime.of(2020, 1, 32, 0, 0, 0), "day 32");
        expect(() -> LocalDateTime.of(2020, 1, 1, 24, 0, 0), "hour 24");
        expect(() -> LocalDateTime.of(2020, 1, 1, -1, 0, 0), "hour -1");
        expect(() -> LocalDateTime.of(2020, 1, 1, 0, 60, 0), "minute 60");
        expect(() -> LocalDateTime.of(2020, 1, 1, 0, -1, 0), "minute -1");
        expect(() -> LocalDateTime.of(2020, 1, 1, 0, 0, 60), "second 60");
        expect(() -> LocalDateTime.of(2020, 1, 1, 0, 0, -1), "second -1");
        expect(() -> LocalDateTime.of(1_000_000_000, 1, 1, 0, 0, 0), "year above range");
        expect(() -> LocalDateTime.of(-1_000_000_000, 1, 1, 0, 0, 0), "year below range");
    }

    private static void expect(Runnable r, String msg) {
        try {
            r.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException: " + msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }
}
