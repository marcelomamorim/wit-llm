/*
 * @test
 * @summary Witup-derived checks for IsoChronology.dateYearDay observable behavior
 * @run main IsoChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        IsoChronology iso = IsoChronology.INSTANCE;

        check(iso.dateYearDay(2020, 1).equals(LocalDate.of(2020, 1, 1)), "first day of leap year");
        check(iso.dateYearDay(2020, 366).equals(LocalDate.of(2020, 12, 31)), "last day of leap year");
        check(iso.dateYearDay(2019, 365).equals(LocalDate.of(2019, 12, 31)), "last day of common year");
        check(iso.dateYearDay(-1, 1).equals(LocalDate.of(-1, 1, 1)), "negative proleptic year");

        expectDateTimeException(iso, 2019, 366);
        expectDateTimeException(iso, 2020, 0);
        expectDateTimeException(iso, 2020, 367);
        expectDateTimeException(iso, 1000000000, 1);
        expectDateTimeException(iso, -1000000000, 1);
    }

    static void expectDateTimeException(IsoChronology iso, int year, int dayOfYear) {
        try {
            LocalDate d = iso.dateYearDay(year, dayOfYear);
            throw new RuntimeException("Expected DateTimeException for year=" + year
                    + " dayOfYear=" + dayOfYear + " but got " + d);
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
