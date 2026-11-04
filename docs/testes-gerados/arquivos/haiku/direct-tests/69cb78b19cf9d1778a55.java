/*
 * @test
 * @summary Behavioral tests for Period.parse(CharSequence) derived from its source code
 * @run main PeriodWitupTest
 */

import java.time.Period;
import java.time.format.DateTimeParseException;

public class PeriodWitupTest {

    public static void main(String[] args) {
        testFullIsoFormat();
        testWeeksAreConvertedToDays();
        testNegativeSign();
        testSignedNumbers();
        testEqualsPeriodOf();
        testNullThrowsNpe();
        testBareDesignatorThrows();
        testMissingDesignatorThrows();
        testNumberOverflowThrowsParseException();
        testWeeksOverflowThrowsArithmeticException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFullIsoFormat() {
        Period p = Period.parse("P1Y2M3D");
        check(p.getYears() == 1, "years");
        check(p.getMonths() == 2, "months");
        check(p.getDays() == 3, "days");
    }

    static void testWeeksAreConvertedToDays() {
        Period p = Period.parse("P2W");
        check(p.getYears() == 0, "years for P2W");
        check(p.getMonths() == 0, "months for P2W");
        check(p.getDays() == 14, "days for P2W");

        Period q = Period.parse("P1Y2M3W4D");
        check(q.getYears() == 1, "years for P1Y2M3W4D");
        check(q.getMonths() == 2, "months for P1Y2M3W4D");
        check(q.getDays() == 25, "days for P1Y2M3W4D");
    }

    static void testNegativeSign() {
        Period p = Period.parse("-P1D");
        check(p.getDays() == -1, "negated days");

        Period q = Period.parse("-P1Y2M");
        check(q.getYears() == -1, "negated years");
        check(q.getMonths() == -2, "negated months");
    }

    static void testSignedNumbers() {
        Period p = Period.parse("P+5Y-3D");
        check(p.getYears() == 5, "plus-signed years");
        check(p.getDays() == -3, "minus-signed days");
    }

    static void testEqualsPeriodOf() {
        check(Period.parse("P1Y2M3D").equals(Period.of(1, 2, 3)), "equals Period.of");
        check(Period.parse("P2W").equals(Period.ofDays(14)), "P2W equals ofDays(14)");
    }

    static void testNullThrowsNpe() {
        try {
            Period.parse(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testBareDesignatorThrows() {
        try {
            Period.parse("P");
            throw new RuntimeException("expected DateTimeParseException for 'P'");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testMissingDesignatorThrows() {
        try {
            Period.parse("1Y");
            throw new RuntimeException("expected DateTimeParseException for '1Y'");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testNumberOverflowThrowsParseException() {
        try {
            Period.parse("P2147483648Y");
            throw new RuntimeException("expected DateTimeParseException for overflow");
        } catch (DateTimeParseException expected) {
            // expected
        }
    }

    static void testWeeksOverflowThrowsArithmeticException() {
        try {
            Period.parse("P306783379W");
            throw new RuntimeException("expected ArithmeticException for weeks overflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }
}
