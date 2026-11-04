/*
 * @test
 * @summary Test java.time.Period.get(TemporalUnit)
 * @run main PeriodGetMethodTest
 */

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodGetMethodTest {

    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    static void testGetYears() {
        Period p = Period.of(3, 5, 10);
        long years = p.get(ChronoUnit.YEARS);
        assert years == 3 : "Expected 3 years, got " + years;
    }

    static void testGetMonths() {
        Period p = Period.of(3, 5, 10);
        long months = p.get(ChronoUnit.MONTHS);
        assert months == 5 : "Expected 5 months, got " + months;
    }

    static void testGetDays() {
        Period p = Period.of(3, 5, 10);
        long days = p.get(ChronoUnit.DAYS);
        assert days == 10 : "Expected 10 days, got " + days;
    }

    static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get(ChronoUnit.WEEKS);
            assert false : "Expected UnsupportedTemporalTypeException for WEEKS";
        } catch (UnsupportedTemporalTypeException e) {
            assert e.getMessage().contains("Unsupported unit") : "Unexpected exception message: " + e.getMessage();
        }
    }
}
