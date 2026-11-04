/*
 * @test
 * @summary Witup-derived tests for Period.parse(CharSequence)
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testNullText();
        testValidTexts();
        testInvalidTexts();
        testArithmeticOverflowFromWeeks();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullText() {
        boolean thrown = false;
        try {
            Period.parse(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "parse(null) must throw NullPointerException");
    }

    static void testValidTexts() {
        check(Period.parse("P1Y2M3D").equals(Period.of(1, 2, 3)), "P1Y2M3D");
        check(Period.parse("P2W").equals(Period.ofDays(14)), "P2W must be 14 days");
        check(Period.parse("-P1Y").equals(Period.ofYears(-1)), "-P1Y must be negative years");
        check(Period.parse("P1Y2M3W4D").equals(Period.of(1, 2, 25)), "weeks must be folded into days");
        check(Period.parse("P0D").equals(Period.ZERO), "P0D must equal ZERO");
        check(Period.parse("+P5M").equals(Period.ofMonths(5)), "+P5M");
    }

    static void testInvalidTexts() {
        expectParseFailure("");
        expectParseFailure("abc");
        expectParseFailure("P");
        expectParseFailure("1Y");
        expectParseFailure("P1X");
    }

    static void expectParseFailure(String text) {
        boolean thrown = false;
        try {
            Period.parse(text);
        } catch (DateTimeParseException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeParseException for: " + text);
    }

    static void testArithmeticOverflowFromWeeks() {
        boolean thrown = false;
        try {
            Period.parse("P2147483647W");
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "weeks overflow must throw ArithmeticException");
    }
}
