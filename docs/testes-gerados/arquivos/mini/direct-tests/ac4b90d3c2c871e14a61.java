/*
 * @test
 * @summary Test java.time.Period.addTo(Temporal) method behavior
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testAddToWithZeroMonthsNonZeroYears();
        testAddToWithNonZeroMonths();
        testAddToWithDays();
        testAddToWithZeroPeriod();
        testAddToWithUnsupportedTemporal();
    }

    private static void testAddToWithZeroMonthsNonZeroYears() {
        Period p = Period.of(2, 0, 0); // 2 years
        LocalDate base = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(base);
        LocalDate expected = base.plusYears(2);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed testAddToWithZeroMonthsNonZeroYears: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithNonZeroMonths() {
        Period p = Period.of(1, 3, 0); // 1 year and 3 months
        LocalDate base = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(base);
        // totalMonths = 1*12 + 3 = 15 months
        LocalDate expected = base.plusMonths(15);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed testAddToWithNonZeroMonths: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithDays() {
        Period p = Period.of(0, 0, 10); // 10 days
        LocalDate base = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(base);
        LocalDate expected = base.plusDays(10);
        if (!expected.equals(result)) {
            throw new AssertionError("Failed testAddToWithDays: expected " + expected + " but got " + result);
        }
    }

    private static void testAddToWithZeroPeriod() {
        Period p = Period.of(0, 0, 0); // zero period
        LocalDate base = LocalDate.of(2000, 1, 1);
        Temporal result = p.addTo(base);
        if (!base.equals(result)) {
            throw new AssertionError("Failed testAddToWithZeroPeriod: expected " + base + " but got " + result);
        }
    }

    private static void testAddToWithUnsupportedTemporal() {
        Period p = Period.of(1, 0, 0); // 1 year
        Temporal unsupported = new Temporal() {
            @Override
            public boolean isSupported(ChronoUnit unit) {
                return false;
            }
            @Override
            public long getLong(ChronoUnit unit) {
                throw new UnsupportedTemporalTypeException("Unsupported");
            }
            @Override
            public Temporal plus(long amountToAdd, ChronoUnit unit) {
                throw new UnsupportedTemporalTypeException("Unsupported");
            }
            @Override
            public Temporal minus(long amountToSubtract, ChronoUnit unit) {
                throw new UnsupportedTemporalTypeException("Unsupported");
            }
        };
        try {
            p.addTo(unsupported);
            throw new AssertionError("Expected UnsupportedTemporalTypeException not thrown");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        }
    }
}
