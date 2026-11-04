/*
 * @test
 * @summary Deterministic tests for java.time.Period.parse(CharSequence) method
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {
    public static void main(String[] args) {
        testParseNull();
        testParseValidPeriods();
        testParseInvalidPeriods();
    }

    private static void testParseNull() {
        try {
            Period.parse(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testParseValidPeriods() {
        // Basic valid period strings
        Period p1 = Period.parse("P1Y2M3D");
        if (p1.getYears() != 1 || p1.getMonths() != 2 || p1.getDays() != 3) {
            throw new AssertionError("Failed to parse P1Y2M3D correctly: " + p1);
        }

        Period p2 = Period.parse("P-1Y-2M-3D");
        if (p2.getYears() != -1 || p2.getMonths() != -2 || p2.getDays() != -3) {
            throw new AssertionError("Failed to parse P-1Y-2M-3D correctly: " + p2);
        }

        Period p3 = Period.parse("P2W");
        if (p3.getYears() != 0 || p3.getMonths() != 0 || p3.getDays() != 14) {
            throw new AssertionError("Failed to parse P2W correctly: " + p3);
        }

        Period p4 = Period.parse("P0D");
        if (p4.getYears() != 0 || p4.getMonths() != 0 || p4.getDays() != 0) {
            throw new AssertionError("Failed to parse P0D correctly: " + p4);
        }
    }

    private static void testParseInvalidPeriods() {
        // Invalid format strings should throw DateTimeParseException
        String[] invalidInputs = {
            "", "P", "1Y2M3D", "PX1Y", "P1X2M3D", "P1Y2M3", "P1Y2M3D4H", "P--1Y2M3D"
        };
        for (String input : invalidInputs) {
            try {
                Period.parse(input);
                throw new AssertionError("Expected DateTimeParseException for input: " + input);
            } catch (DateTimeParseException dtpe) {
                // Expected
            }
        }
    }
}
