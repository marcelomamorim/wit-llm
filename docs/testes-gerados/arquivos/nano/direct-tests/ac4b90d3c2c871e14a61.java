/*
 * @test
 * @summary Tests the addTo method of java.time.Period for various Temporal inputs.
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testAddToWithLocalDate();
        testAddToWithUnsupportedTemporal();
        testAddToWithNullTemporal();
    }

    static void testAddToWithLocalDate() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        Period period = Period.of(1, 2, 3);
        Temporal result = period.addTo(date);
        LocalDate expected = date.plus(1, ChronoUnit.YEARS)
                                    .plus(2, ChronoUnit.MONTHS)
                                    .plus(3, ChronoUnit.DAYS);
        if (!result.equals(expected)) {
            throw new AssertionError("addTo with LocalDate failed: expected " + expected + " but got " + result);
        }
    }

    static void testAddToWithUnsupportedTemporal() {
        Object obj = new Object();
        Period period = Period.of(0, 0, 1);
        try {
            period.addTo((Temporal) obj);
            throw new AssertionError("Expected ClassCastException for unsupported Temporal");
        } catch (ClassCastException e) {
            // expected
        }
    }

    static void testAddToWithNullTemporal() {
        Period period = Period.of(0, 0, 1);
        try {
            period.addTo((Temporal) null);
            throw new AssertionError("Expected NullPointerException for null Temporal");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
