/*
 * @test    @summary Test IsoChronology.dateEpochDay method for various epoch days
 * @run main IsoChronologyWitupTest
 */

import java.time.chrono.IsoChronology;
import java.time.LocalDate;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testDateEpochDayZero();
        testDateEpochDayPositive();
        testDateEpochDayNegative();
        testDateEpochDayLarge();
    }

    static void testDateEpochDayZero() {
        LocalDate result = IsoChronology.INSTANCE.dateEpochDay(0L);
        LocalDate expected = LocalDate.ofEpochDay(0L);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dateEpochDay(0L)");
        }
    }

    static void testDateEpochDayPositive() {
        long epochDay = 18517L; // corresponds to 2020-12-31
        LocalDate result = IsoChronology.INSTANCE.dateEpochDay(epochDay);
        LocalDate expected = LocalDate.ofEpochDay(epochDay);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dateEpochDay(" + epochDay + ")");
        }
    }

    static void testDateEpochDayNegative() {
        long epochDay = -365L; // corresponds to  -1 year,  -1 day
        LocalDate result = IsoChronology.INSTANCE.dateEpochDay(epochDay);
        LocalDate expected = LocalDate.ofEpochDay(epochDay);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dateEpochDay(" + epochDay + ")");
        }
    }

    static void testDateEpochDayLarge() {
        long epochDay = Long.MAX_VALUE / 2;
        LocalDate result = IsoChronology.INSTANCE.dateEpochDay(epochDay);
        LocalDate expected = LocalDate.ofEpochDay(epochDay);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: dateEpochDay(" + epochDay + ")");
        }
    }
}
