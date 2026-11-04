/*
 * @test
 * @summary Witup-generated checks for MonthDay.atYear
 * @run main MonthDayWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        check(MonthDay.of(Month.DECEMBER, 25).atYear(2020).equals(LocalDate.of(2020, 12, 25)), "dec25 in 2020");
        check(MonthDay.of(6, 15).atYear(1999).equals(LocalDate.of(1999, 6, 15)), "jun15 in 1999");
        check(MonthDay.of(2, 29).atYear(2020).equals(LocalDate.of(2020, 2, 29)), "feb29 in leap year 2020");
        check(MonthDay.of(2, 29).atYear(2000).equals(LocalDate.of(2000, 2, 29)), "feb29 in leap year 2000");
        check(MonthDay.of(2, 29).atYear(2019).equals(LocalDate.of(2019, 2, 28)), "feb29 in non-leap 2019 maps to feb28");
        check(MonthDay.of(2, 29).atYear(1900).equals(LocalDate.of(1900, 2, 28)), "feb29 in non-leap 1900 maps to feb28");
        check(MonthDay.of(6, 15).atYear(999999999).equals(LocalDate.of(999999999, 6, 15)), "max supported year");
        check(MonthDay.of(6, 15).atYear(-999999999).equals(LocalDate.of(-999999999, 6, 15)), "min supported year");
        expectDateTimeException(() -> MonthDay.of(6, 15).atYear(1000000000), "year above max");
        expectDateTimeException(() -> MonthDay.of(6, 15).atYear(-1000000000), "year below min");
        expectDateTimeException(() -> MonthDay.of(6, 15).atYear(Integer.MAX_VALUE), "Integer.MAX_VALUE year");
        expectDateTimeException(() -> MonthDay.of(6, 15).atYear(Integer.MIN_VALUE), "Integer.MIN_VALUE year");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    private static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException: " + message);
    }
}
