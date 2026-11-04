/*
 * @test
 * @summary Verify observable behavior of LocalDateTime.of(int, Month, int, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testTypicalValues();
        testBoundaryValues();
        testLeapDay();
        testInvalidValuesThrow();
    }

    static void testTypicalValues() {
        LocalDateTime dt = LocalDateTime.of(2018, Month.SEPTEMBER, 15, 13, 45, 30, 123456789);
        check(dt.getYear() == 2018, "year");
        check(dt.getMonth() == Month.SEPTEMBER, "month");
        check(dt.getDayOfMonth() == 15, "dayOfMonth");
        check(dt.getHour() == 13, "hour");
        check(dt.getMinute() == 45, "minute");
        check(dt.getSecond() == 30, "second");
        check(dt.getNano() == 123456789, "nano");
    }

    static void testBoundaryValues() {
        LocalDateTime min = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0, 0);
        check(min.getYear() == 2000, "min year");
        check(min.getMonth() == Month.JANUARY, "min month");
        check(min.getDayOfMonth() == 1, "min day");
        check(min.getHour() == 0, "min hour");
        check(min.getMinute() == 0, "min minute");
        check(min.getSecond() == 0, "min second");
        check(min.getNano() == 0, "min nano");
        check(min.equals(LocalDateTime.of(2000, 1, 1, 0, 0)), "min equals int overload");

        LocalDateTime max = LocalDateTime.of(2000, Month.DECEMBER, 31, 23, 59, 59, 999999999);
        check(max.getMonth() == Month.DECEMBER, "max month");
        check(max.getDayOfMonth() == 31, "max day");
        check(max.getHour() == 23, "max hour");
        check(max.getMinute() == 59, "max minute");
        check(max.getSecond() == 59, "max second");
        check(max.getNano() == 999999999, "max nano");
    }

    static void testLeapDay() {
        LocalDateTime leap = LocalDateTime.of(2024, Month.FEBRUARY, 29, 12, 0, 0, 0);
        check(leap.getMonth() == Month.FEBRUARY, "leap month");
        check(leap.getDayOfMonth() == 29, "leap day");
    }

    static void testInvalidValuesThrow() {
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.FEBRUARY, 29, 0, 0, 0, 0);
        }, "non-leap Feb 29");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.APRIL, 31, 0, 0, 0, 0);
        }, "April 31");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.JANUARY, 1, 24, 0, 0, 0);
        }, "hour 24");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.JANUARY, 1, 0, 60, 0, 0);
        }, "minute 60");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 60, 0);
        }, "second 60");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0, 1000000000);
        }, "nano 1000000000");
        expectDateTimeException(() -> {
            LocalDateTime.of(2023, Month.JANUARY, 1, 0, 0, 0, -1);
        }, "negative nano");
    }

    static void expectDateTimeException(Runnable r, String msg) {
        try {
            r.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException: " + msg);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
