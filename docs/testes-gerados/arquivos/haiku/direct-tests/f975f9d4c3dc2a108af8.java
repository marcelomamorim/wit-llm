/*
 * @test
 * @summary Verify ChronoField.checkValidIntValue delegates to ValueRange validation and returns int values or throws DateTimeException
 * @run main ChronoFieldWitupTest
 */

import java.time.DateTimeException;
import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testValidIntValuesAreReturned();
        testBoundaryValuesAreReturned();
        testBelowRangeThrows();
        testAboveRangeThrows();
        testDayOfWeekBoundaries();
        testNonIntRangeFieldThrows();
    }

    static void testValidIntValuesAreReturned() {
        check(ChronoField.DAY_OF_MONTH.checkValidIntValue(15) == 15, "DAY_OF_MONTH 15 should be returned");
        check(ChronoField.MONTH_OF_YEAR.checkValidIntValue(12) == 12, "MONTH_OF_YEAR 12 should be returned");
    }

    static void testBoundaryValuesAreReturned() {
        check(ChronoField.DAY_OF_MONTH.checkValidIntValue(1) == 1, "DAY_OF_MONTH minimum should be returned");
        check(ChronoField.MONTH_OF_YEAR.checkValidIntValue(1) == 1, "MONTH_OF_YEAR minimum should be returned");
        check(ChronoField.MONTH_OF_YEAR.checkValidIntValue(12) == 12, "MONTH_OF_YEAR maximum should be returned");
    }

    static void testBelowRangeThrows() {
        expectDateTimeException(0L, ChronoField.DAY_OF_MONTH);
        expectDateTimeException(0L, ChronoField.MONTH_OF_YEAR);
    }

    static void testAboveRangeThrows() {
        expectDateTimeException(32L, ChronoField.DAY_OF_MONTH);
        expectDateTimeException(13L, ChronoField.MONTH_OF_YEAR);
    }

    static void testDayOfWeekBoundaries() {
        check(ChronoField.DAY_OF_WEEK.checkValidIntValue(1) == 1, "DAY_OF_WEEK 1 should be returned");
        check(ChronoField.DAY_OF_WEEK.checkValidIntValue(7) == 7, "DAY_OF_WEEK 7 should be returned");
        expectDateTimeException(8L, ChronoField.DAY_OF_WEEK);
    }

    static void testNonIntRangeFieldThrows() {
        expectDateTimeException(0L, ChronoField.NANO_OF_DAY);
    }

    static void expectDateTimeException(long value, ChronoField field) {
        try {
            int result = field.checkValidIntValue(value);
            throw new RuntimeException("Expected DateTimeException for " + field + " value " + value + " but got " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
