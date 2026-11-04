/*
 * @test
 * @summary Tests for java.time.Year class, focusing on the 'of' method.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.ChronoField;

public class YearWitupTest {

    public static void main(String[] args) {
        testValidYear();
        testYearBelowMinimum();
        testYearAboveMaximum();
    }

    private static void testValidYear() {
        int validYear = 2023;
        Year y = Year.of(validYear);
        assert y.getValue() == validYear : "Expected year to be " + validYear;
    }

    private static void testYearBelowMinimum() {
        int minYear = Year.MIN_VALUE;
        int belowMin = minYear - 1;
        try {
            Year.of(belowMin);
            assert false : "Expected DateTimeException for year below MIN_VALUE";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }

    private static void testYearAboveMaximum() {
        int maxYear = Year.MAX_VALUE;
        int aboveMax = maxYear + 1;
        try {
            Year.of(aboveMax);
            assert false : "Expected DateTimeException for year above MAX_VALUE";
        } catch (java.time.DateTimeException e) {
            // Expected exception
        }
    }
}
