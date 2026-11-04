/*
 * @test
 * @summary Witup-generated checks for LocalDate.plusYears behavior and out-of-range year errors
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2020, 6, 15);

        check(d.plusYears(0) == d, "plusYears(0) must return this");

        LocalDate p1 = d.plusYears(1);
        check(p1.getYear() == 2021, "plusYears(1) year");
        check(p1.getMonth() == Month.JUNE, "plusYears(1) month");
        check(p1.getDayOfMonth() == 15, "plusYears(1) day");

        LocalDate n1 = d.plusYears(-1);
        check(n1.getYear() == 2019, "plusYears(-1) year");
        check(n1.getMonth() == Month.JUNE, "plusYears(-1) month");
        check(n1.getDayOfMonth() == 15, "plusYears(-1) day");

        LocalDate leap = LocalDate.of(2020, 2, 29);
        LocalDate leapPlus1 = leap.plusYears(1);
        check(leapPlus1.getYear() == 2021, "leap plusYears(1) year");
        check(leapPlus1.getMonth() == Month.FEBRUARY, "leap plusYears(1) month");
        check(leapPlus1.getDayOfMonth() == 28, "leap plusYears(1) must clamp to Feb 28");

        LocalDate leapPlus4 = leap.plusYears(4);
        check(leapPlus4.getYear() == 2024, "leap plusYears(4) year");
        check(leapPlus4.getDayOfMonth() == 29, "leap plusYears(4) must keep Feb 29");

        LocalDate leapMinus1 = leap.plusYears(-1);
        check(leapMinus1.getYear() == 2019, "leap plusYears(-1) year");
        check(leapMinus1.getDayOfMonth() == 28, "leap plusYears(-1) must clamp to Feb 28");

        LocalDate maxYear = d.plusYears(999999999L - 2020L);
        check(maxYear.getYear() == 999999999, "max valid year must be reachable");
        check(maxYear.getMonth() == Month.JUNE, "max valid year month");

        LocalDate minYear = d.plusYears(-999999999L - 2020L);
        check(minYear.getYear() == -999999999, "min valid year must be reachable");

        expectDateTimeException(d, 999999999L);
        expectDateTimeException(d, Integer.MAX_VALUE);
        expectDateTimeException(d, Integer.MIN_VALUE);
        expectDateTimeException(d, Long.MAX_VALUE);
        expectDateTimeException(d, Long.MIN_VALUE);
    }

    static void expectDateTimeException(LocalDate d, long years) {
        try {
            d.plusYears(years);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for plusYears(" + years + ")");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
