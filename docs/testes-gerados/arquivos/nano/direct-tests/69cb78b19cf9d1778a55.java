/*
 * @test
 * @summary Tests for java.time.Period.parse(CharSequence)
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    static void testParseValid() {
        try {
            Period p1 = Period.parse("P2Y6M");
            assert p1.getYears() == 2 : "Expected 2 years";
            assert p1.getMonths() == 6 : "Expected 6 months";
            assert p1.getDays() == 0 : "Expected 0 days";

            Period p2 = Period.parse("P-1Y");
            assert p2.getYears() == -1 : "Expected -1 year";
            assert p2.getMonths() == 0 : "Expected 0 months";
            assert p2.getDays() == 0 : "Expected 0 days";

            Period p3 = Period.parse("P3W");
            assert p3.getDays() == 21 : "Expected 21 days";
        } catch (Exception e) {
            throw new AssertionError("Valid parse threw exception: " + e);
        }
    }

    static void testParseInvalid() {
        try {
            Period.parse("");
            throw new AssertionError("Expected DateTimeParseException for empty string");
        } catch (DateTimeParseException e) {
            // expected
        }
        try {
            Period.parse("InvalidText");
            throw new AssertionError("Expected DateTimeParseException for invalid text");
        } catch (DateTimeParseException e) {
            // expected
        }
        try {
            Period.parse("P2Y-6M");
            // Depending on implementation, this may throw or parse; test for exception
            // but as per current code, it should throw
            throw new AssertionError("Expected DateTimeParseException for malformed string");
        } catch (DateTimeParseException e) {
            // expected
        }
    }
}
