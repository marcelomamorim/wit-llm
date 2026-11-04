/*
 * @test
 * @summary Witup-derived checks for IsoChronology.date(TemporalAccessor)
 * @run main IsoChronologyWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        IsoChronology chrono = IsoChronology.INSTANCE;

        testNullTemporalThrowsNpe(chrono);
        testLocalDateIsReturned(chrono);
        testLocalDateTimeYieldsDatePart(chrono);
        testZonedDateTimeYieldsDatePart(chrono);
        testTemporalWithoutDateThrowsDateTimeException(chrono);
    }

    static void testNullTemporalThrowsNpe(IsoChronology chrono) {
        boolean thrown = false;
        try {
            chrono.date(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "date(null) must throw NullPointerException");
    }

    static void testLocalDateIsReturned(IsoChronology chrono) {
        LocalDate expected = LocalDate.of(2020, 2, 29);
        LocalDate actual = chrono.date(expected);
        check(expected.equals(actual), "date(LocalDate) must return an equal LocalDate");
    }

    static void testLocalDateTimeYieldsDatePart(IsoChronology chrono) {
        LocalDateTime ldt = LocalDateTime.of(1999, 12, 31, 23, 59, 58);
        LocalDate actual = chrono.date(ldt);
        check(LocalDate.of(1999, 12, 31).equals(actual), "date(LocalDateTime) must return its date part");
    }

    static void testZonedDateTimeYieldsDatePart(IsoChronology chrono) {
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2015, 6, 15, 10, 30), ZoneOffset.UTC);
        LocalDate actual = chrono.date(zdt);
        check(LocalDate.of(2015, 6, 15).equals(actual), "date(ZonedDateTime) must return its local date");
    }

    static void testTemporalWithoutDateThrowsDateTimeException(IsoChronology chrono) {
        boolean thrown = false;
        try {
            chrono.date(Month.JANUARY);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "date(Month) must throw DateTimeException because no date can be extracted");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
