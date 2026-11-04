/*
 * @test
 * @summary Tests LocalDateTime.of(int,int,int,int,int) valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testLeapDay();
        testYearBoundaries();
        testInvalidMonth();
        testInvalidDayOfMonth();
        testInvalidHourAndMinute();
    }

    static void testValidValues() {
        LocalDateTime dt = LocalDateTime.of(2018, 9, 15, 10, 30);
        check(dt.getYear() == 2018, "year");
        check(dt.getMonthValue() == 9, "month");
        check(dt.getDayOfMonth() == 15, "day");
        check(dt.getHour() == 10, "hour");
        check(dt.getMinute() == 30, "minute");
        check(dt.getSecond() == 0, "second");
        check(dt.getNano() == 0, "nano");
    }

    static void testLeapDay() {
        LocalDateTime leap = LocalDateTime.of(2020, 2, 29, 0, 0);
        check(leap.getDayOfMonth() == 29 && leap.getMonthValue() == 2, "leap day 2020");
        expectDateTimeException(2019, 2, 29, 0, 0);
    }

    static void testYearBoundaries() {
        LocalDateTime max = LocalDateTime.of(999999999, 12, 31, 23, 59);
        check(max.getYear() == 999999999, "max year");
        expectDateTimeException(1000000000, 1, 1, 0, 0);
        expectDateTimeException(-1000000000, 1, 1, 0, 0);
    }

    static void testInvalidMonth() {
        expectDateTimeException(2018, 0, 1, 0, 0);
        expectDateTimeException(2018, 13, 1, 0, 0);
    }

    static void testInvalidDayOfMonth() {
        expectDateTimeException(2018, 4, 31, 0, 0);
        expectDateTimeException(2018, 1, 0, 0, 0);
        expectDateTimeException(2018, 1, 32, 0, 0);
    }

    static void testInvalidHourAndMinute() {
        expectDateTimeException(2018, 1, 1, 24, 0);
        expectDateTimeException(2018, 1, 1, -1, 0);
        expectDateTimeException(2018, 1, 1, 0, 60);
        expectDateTimeException(2018, 1, 1, 0, -1);
    }

    static void expectDateTimeException(int year, int month, int day, int hour, int minute) {
        try {
            LocalDateTime.of(year, month, day, hour, minute);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + year + "-" + month + "-" + day
                + "T" + hour + ":" + minute);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
