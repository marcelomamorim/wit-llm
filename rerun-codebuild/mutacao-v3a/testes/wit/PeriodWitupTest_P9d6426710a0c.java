/*
 * @test    @summary Test the get(TemporalUnit) method of java.time.Period.
 * @run main PeriodWitupTest_P9d6426710a0c
 */

import static java.time.temporal.ChronoUnit.YEARS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest_P9d6426710a0c {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    public static void testGetYears() {
        Period p = Period.of(3, 4, 5);
        long result = p.get( java.time.temporal.ChronoUnit.YEARS );
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
    }

    public static void testGetMonths() {
        Period p = Period.of(3, 4, 5);
        long result = p.get( java.time.temporal.ChronoUnit.MONTHS );
        if (result != 4) {
            throw new AssertionError("Expected 4, got " + result);
        }
    }

    public static void testGetDays() {
        Period p = Period.of(3, 4, 5);
        long result = p.get( java.time.temporal.ChronoUnit.DAYS );
        if (result != 5) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    public static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get( java.time.temporal.ChronoUnit.HOURS );
            throw new AssertionError("Expected UnsupportedTemporalTypeException") ;
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
