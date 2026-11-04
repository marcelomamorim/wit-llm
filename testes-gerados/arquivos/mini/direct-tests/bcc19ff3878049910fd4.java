/*
 * @test
 * @summary Test java.time.chrono.IsoChronology.date(TemporalAccessor) method
 * @run main IsoChronologyWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;

public class IsoChronologyWitupTest {
    public static void main(String[] args) {
        testDateWithLocalDate();
        testDateWithLocalDateTime();
        testDateWithUnsupportedTemporal();
    }

    private static void testDateWithLocalDate() {
        LocalDate now = LocalDate.of(2020, Month.JANUARY, 15);
        LocalDate result = IsoChronology.INSTANCE.date(now);
        if (!result.equals(now)) {
            throw new AssertionError("Expected LocalDate to be returned unchanged");
        }
    }

    private static void testDateWithLocalDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 10, 30);
        LocalDate result = IsoChronology.INSTANCE.date(ldt);
        LocalDate expected = LocalDate.of(2020, Month.FEBRUARY, 29);
        if (!result.equals(expected)) {
            throw new AssertionError("Expected LocalDate extracted from LocalDateTime");
        }
    }

    private static void testDateWithUnsupportedTemporal() {
        TemporalAccessor unsupported = new TemporalAccessor() {
            @Override
            public boolean isSupported(java.time.temporal.TemporalField field) {
                return false;
            }

            @Override
            public long getLong(java.time.temporal.TemporalField field) {
                throw new UnsupportedTemporalTypeException("Unsupported field");
            }
        };

        try {
            IsoChronology.INSTANCE.date(unsupported);
            throw new AssertionError("Expected DateTimeException or UnsupportedTemporalTypeException");
        } catch (java.time.DateTimeException | UnsupportedTemporalTypeException e) {
            // Expected exception
        }
    }
}
