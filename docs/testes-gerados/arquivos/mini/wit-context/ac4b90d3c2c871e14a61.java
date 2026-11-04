/*
 * @test
 * @summary Deterministic tests for java.time.Period.addTo(Temporal) method
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.TemporalAdjuster;
import java.time.LocalDate;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testAddToNullTemporal();
        testAddToWithZeroMonthsNonZeroYears();
        testAddToWithNonZeroMonths();
        testAddToWithDays();
        testAddToWithZeroPeriod();
    }

    private static void testAddToNullTemporal() {
        try {
            Period p = Period.of(1, 2, 3);
            p.addTo(null);
            throw new AssertionError("Expected NullPointerException for null temporal");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testAddToWithZeroMonthsNonZeroYears() {
        Period p = Period.of(2, 0, 0); // years=2, months=0, days=0
        LocalDate baseDate = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(baseDate);
        LocalDate expected = baseDate.plusYears(2);
        if (!expected.equals(result)) {
            throw new AssertionError("addTo failed for years only: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithNonZeroMonths() {
        Period p = Period.of(1, 3, 0); // years=1, months=3, days=0
        LocalDate baseDate = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(baseDate);
        // totalMonths = 1*12 + 3 = 15
        LocalDate expected = baseDate.plusMonths(15);
        if (!expected.equals(result)) {
            throw new AssertionError("addTo failed for total months: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithDays() {
        Period p = Period.of(0, 0, 10); // days=10
        LocalDate baseDate = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(baseDate);
        LocalDate expected = baseDate.plusDays(10);
        if (!expected.equals(result)) {
            throw new AssertionError("addTo failed for days only: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithZeroPeriod() {
        Period p = Period.of(0, 0, 0);
        LocalDate baseDate = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(baseDate);
        if (!baseDate.equals(result)) {
            throw new AssertionError("addTo failed for zero period: expected " + baseDate + " but got " + result);
        }
    }
}
