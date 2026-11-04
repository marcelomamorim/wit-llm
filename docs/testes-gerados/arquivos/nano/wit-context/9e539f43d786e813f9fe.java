/*
 * @test
 * @summary Tests for java.time.Period.get(TemporalUnit)
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    static void testGetYears() {
        Period p = Period.of(3, 4, 5);
        long result = p.get(ChronoUnit.YEARS);
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
    }

    static void testGetMonths() {
        Period p = Period.of(3, 4, 5);
        long result = p.get(ChronoUnit.MONTHS);
        if (result != 4) {
            throw new AssertionError("Expected 4, got " + result);
        }
    }

    static void testGetDays() {
        Period p = Period.of(3, 4, 5);
        long result = p.get(ChronoUnit.DAYS);
        if (result != 5) {
            throw new AssertionError("Expected 5, got " + result);
        }
    }

    static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get(ChronoUnit.HOURS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
