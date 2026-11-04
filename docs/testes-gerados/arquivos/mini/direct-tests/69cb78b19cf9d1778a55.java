/*
 * @test
 * @summary Test java.time.Period.parse(CharSequence) method
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testValidPeriodParsing();
        testNegativePeriodParsing();
        testPeriodWithWeeksParsing();
        testPeriodWithDaysOnly();
        testPeriodWithMonthsOnly();
        testPeriodWithYearsOnly();
        testInvalidFormatThrows();
        testNullInputThrows();
    }

    private static void testValidPeriodParsing() {
        Period p = Period.parse("P2Y3M4D");
        if (p.getYears() != 2 || p.getMonths() != 3 || p.getDays() != 4) {
            throw new AssertionError("Failed to parse valid period P2Y3M4D");
        }
    }

    private static void testNegativePeriodParsing() {
        Period p = Period.parse("-P1Y2M3D");
        if (p.getYears() != -1 || p.getMonths() != -2 || p.getDays() != -3) {
            throw new AssertionError("Failed to parse negative period -P1Y2M3D");
        }
    }

    private static void testPeriodWithWeeksParsing() {
        Period p = Period.parse("P1W");
        if (p.getYears() != 0 || p.getMonths() != 0 || p.getDays() != 7) {
            throw new AssertionError("Failed to parse period with weeks P1W");
        }
    }

    private static void testPeriodWithDaysOnly() {
        Period p = Period.parse("P5D");
        if (p.getYears() != 0 || p.getMonths() != 0 || p.getDays() != 5) {
            throw new AssertionError("Failed to parse period with days only P5D");
        }
    }

    private static void testPeriodWithMonthsOnly() {
        Period p = Period.parse("P4M");
        if (p.getYears() != 0 || p.getMonths() != 4 || p.getDays() != 0) {
            throw new AssertionError("Failed to parse period with months only P4M");
        }
    }

    private static void testPeriodWithYearsOnly() {
        Period p = Period.parse("P3Y");
        if (p.getYears() != 3 || p.getMonths() != 0 || p.getDays() != 0) {
            throw new AssertionError("Failed to parse period with years only P3Y");
        }
    }

    private static void testInvalidFormatThrows() {
        try {
            Period.parse("Invalid");
            throw new AssertionError("Expected DateTimeParseException for invalid input");
        } catch (DateTimeParseException e) {
            // expected
        }
    }

    private static void testNullInputThrows() {
        try {
            Period.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
